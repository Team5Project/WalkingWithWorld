package com.team5.WalkingWithWorld.walkingPaths.controller;

import com.github.pagehelper.PageHelper;
import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.global.Login;
import com.team5.WalkingWithWorld.global.domain.FileVo;
import com.team5.WalkingWithWorld.global.domain.MapDTO;
import com.team5.WalkingWithWorld.global.domain.PageInfo;
import com.team5.WalkingWithWorld.global.domain.SearchDTO;
import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import com.team5.WalkingWithWorld.global.pagination.PaginationService;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.WalkingPathsDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.WalkingPathsMapDTO;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.service.WalkingPathService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WalkingPathsController {
    @Autowired
    WalkingPathsMapper walkingPathsMapper;
    @Autowired
    WalkingPathService walkingPathService;
    @Autowired
    PaginationService paginationService;

    //페이징 확인
    @GetMapping("/page")
    @ResponseBody
    public PageResponseDto getPagingWalkingPathList(
            SearchDTO dto,
            @PageableDefault Pageable pageable) {

        Page<WalkingPathsMapDTO> walkingPaths = walkingPathService.getWalkingPathPagination(pageable);
        List<WalkingPathsMapDTO> walkingPathsMapDTOList = walkingPaths.getContent();
        List<Integer> barNumber = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), pageable.getPageSize());
        return new PageResponseDto(walkingPathsMapDTOList, walkingPaths,barNumber);
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
    public String createWalkingPath(WalkingPathsDTO dto,
                                    @Login UsersDTO loginUser, FileVo files, MapDTO mapDTO, String course, Model model) throws IOException {
        int result = walkingPathService.createWalkingPath(dto, loginUser, files, mapDTO, course);

        if(result == -1) {
            model.addAttribute("url", "/walking-path");
            model.addAttribute("message", "게시글 작성을 실패하였습니다.");
        }
        else {
            model.addAttribute("url", "/walking-path/" + result);
            model.addAttribute("message", "게시글이 작성되었습니다.");
        }
        return "message";
    }

    // 산책로 수정 폼으로 이동(walking-path-id 참조)
    @GetMapping("/walking-path/modify/{walking-path-id}")
    public ModelAndView goToModify(@PathVariable("walking-path-id") Long walkingPathId, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        WalkingPathsMapDTO walkingPathsMapDTO = walkingPathService.readWalkingPathById(walkingPathId);
        if(walkingPathsMapDTO == null) {
            System.out.println("존재하지 않는 데이터 접근");
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
    public String modifyWalkingPath(WalkingPathsDTO walkingPathsDTO, @Login UsersDTO loginUser, Model model) {
        if(loginUser.getId() != walkingPathsMapper.readWalkingPath(walkingPathsDTO.getId()).getUsersId()) {
            model.addAttribute("url", "/walking-path/" + walkingPathsDTO.getId());
            model.addAttribute("message", "수정권한이 없습니다.");
            return "message";
        }

        walkingPathService.modifyWalkingPathWithUserName(walkingPathsDTO, loginUser.getName());
        model.addAttribute("url", "/walking-path/"  + walkingPathsDTO.getId());
        model.addAttribute("message", "게시글이 수정되었습니다.");
        return "message";
    }

    //산책로 하나 조회
    @GetMapping("/walking-path/{walking-path-id}")
    public ModelAndView getWalkingPathById(@PathVariable("walking-path-id") Long id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        WalkingPathsMapDTO walkingPaths = walkingPathService.readWalkingPathById(id);
        if(walkingPaths == null) {
            System.out.println("존재하지 않는 데이터 접근");
            mav.setViewName("redirect:/walking-path");
            return mav;
        }

        mav.addObject("walkingPaths", walkingPaths);
        mav.setViewName("walking-path_detail");
        return mav;
    }

    // 산책로 삭제
    @GetMapping("/walking-path/delete/{walking-path-id}")
    public String deleteWalkingPathById(@PathVariable("walking-path-id") Long id,
                                        @Login UsersDTO login, Model model) {

        if(walkingPathsMapper.readWalkingPathMap(id) == null || login.getId() != walkingPathsMapper.readWalkingPath(id).getUsersId()){
            model.addAttribute("message", "삭제 권한이 없거나 존재하지 않는 게시글입니다.");
            model.addAttribute("url", "/walking-path/"+id);
            return "message";
        }

        walkingPathsMapper.deleteWalkingPath(id);
        model.addAttribute("message", "삭제되었습니다.");
        model.addAttribute("url", "/walking-path");
        return "message";
    }
}