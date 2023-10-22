package com.team5.WalkingWithWorld.controller;

import com.github.pagehelper.PageHelper;
import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.domain.*;
import com.team5.WalkingWithWorld.global.Login;
import com.team5.WalkingWithWorld.service.WalkingPathService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class WalkingPathsController {
    @Autowired
    WalkingPathsMapper walkingPathsMapper;
    @Autowired
    WalkingPathService walkingPathService;

    //페이징 확인
    @GetMapping("/walking-paths-test")
    @ResponseBody
    public List<WalkingPathsMapDTO> getPagingWalkingPathList(
            SearchDTO dto,
            @RequestParam(required = false) int pageNum,
            @RequestParam(required = false) int size){
        PageInfo page = new PageInfo(pageNum, size);

        PageHelper.startPage(dto);
        com.github.pagehelper.PageInfo.of(walkingPathService.getList(dto));

        return walkingPathService.getList(dto);
    }

    // 전체 리스트
    @GetMapping("/walking-path")
    public ModelAndView readAllWalkingPath(@Login UsersDTO loginUser) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("walkingPathList", walkingPathService.readWalkingPathList());
        mav.setViewName("walking-path");
        return mav;
    }

    // 산책로 검색 필터 이용
    @PostMapping(value = "/walking-path/condition/{keyword}", produces = "application/json; charset=utf-8")
    public String conditionSearch(@PathVariable("keyword") String searchWord, @RequestBody SearchDTO searchDTO, Model model) {
        model.addAttribute("walkingPathList", walkingPathService.readWalkingPathListWithSearchDTO(searchWord, searchDTO));
        model.addAttribute("keyword", searchWord);
        return "walking-path_search :: #walking-path-list";
    }

    // 산책로 검색
    @PostMapping("/walking-path/search")
    public ModelAndView searchByKeyword(String keyword) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("walkingPathList", walkingPathService.readWalkingPathListWithKeyword(keyword));
        mav.addObject("keyword", keyword);
        mav.setViewName("walking-path");
        return mav;
    }

    // 산책로 작성 폼으로 이동
    @GetMapping("/walking-path/write")
    public ModelAndView goToWrite(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("referer", request.getHeader("referer"));
        mav.setViewName("walking-path_write_form");
        return mav;
    }

    //산책로 작성
    @PostMapping("/walking-path")
    public ModelAndView createWalkingPath(WalkingPathsDTO dto,
                                          @Login UsersDTO loginUser, FileVo files, MapDTO mapDTO, String course) throws IOException {
        ModelAndView mav = new ModelAndView();
        int result = walkingPathService.createWalkingPath(dto, loginUser, files, mapDTO, course);
        mav.setViewName("redirect:/walking-path/" + result);

        if(result == -1) {
            System.out.println("게시글 생성 실패");
            mav.setViewName("redirect:/walking-path");
        }

        return mav;
    }

    // 산책로 수정 폼으로 이동(walking-path-id 참조)
    @GetMapping("/walking-path/modify/{walking-path-id}")
    public ModelAndView goToModify(@PathVariable("walking-path-id") int walkingPathId, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        WalkingPathsMapDTO walkingPathsMapDTO = walkingPathService.readWalkingPathById(walkingPathId);
        if(walkingPathsMapDTO == null) {
            System.out.println("해당 게시글을 찾을 수 없습니다.");
            mav.setViewName("redirect:/walking-path");
            return mav;
        }

        mav.addObject("walkingPath", walkingPathsMapDTO);
        mav.addObject("referer", request.getHeader("referer"));
        mav.setViewName("walking-path_modify_form");
        return mav;
    }

    // 산책로 수정
    @PostMapping("/walking-path/modify")
    public String modifyWalkingPath(WalkingPathsDTO walkingPathsDTO, @Login UsersDTO loginUser) {
        if(loginUser.getId() != walkingPathsMapper.readWalkingPath(walkingPathsDTO.getId()).getUsersId()) {
            System.out.println("미인증 사용자 입니다.");
            return "redirect:/walking-path/" + walkingPathsDTO.getId();
        }

        walkingPathService.modifyWalkingPathWithUserName(walkingPathsDTO, loginUser.getName());
        return "redirect:/walking-path/"  + walkingPathsDTO.getId();
    }

    //산책로 하나 조회
    @GetMapping("/walking-path/{walking-path-id}")
    public ModelAndView getWalkingPathById(@PathVariable("walking-path-id") int id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        WalkingPathsMapDTO walkingPaths = walkingPathService.readWalkingPathById(id);
        if(walkingPaths == null) {
            System.out.println("검색 결과 없음");
            mav.setViewName("redirect:/walking-path");
            return mav;
        }

        mav.addObject("walkingPaths", walkingPaths);
        mav.setViewName("walking-path_detail");
        return mav;
    }

    // 산책로 삭제
    @GetMapping("/walking-path/delete/{walking-path-id}")
    public String deleteWalkingPathById(@PathVariable("walking-path-id") int id,
                                        @Login UsersDTO login) {

        if(login.getId() != walkingPathsMapper.readWalkingPath(id).getUsersId()){
            System.out.println("미인증 사용자 입니다");
            return "redirect:/walking-path/"+id;
        }

        System.out.println("게시글 삭제 결과 : " + walkingPathsMapper.deleteWalkingPath(id));
        return "redirect:/walking-path";
    }
}