package com.team5.WalkingWithWorld.controller;

import com.github.pagehelper.PageHelper;
import com.team5.WalkingWithWorld.dao.MapMapper;
import com.team5.WalkingWithWorld.dao.PhotosMapper;
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
    WalkingPathsMapper dao;
    @Autowired
    PhotosMapper photoDao;
    @Autowired
    MapMapper mapDao;
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
    public ModelAndView readAllWalkingPath(@Login UsersDto loginUser) {
        ModelAndView mav = new ModelAndView();
        List<WalkingPathsMapDTO> walkingPathList = dao.readAllWalkingPathsMap();

        for(WalkingPathsMapDTO dto : walkingPathList) {
           dto.setPhotosList(photoDao.readPhotos(dto.getId()));
           dto.setMapList(mapDao.ReadMap(dto.getId()));
        }

        mav.addObject("walkingPathList", walkingPathList);
        mav.setViewName("walking-path");
        return mav;
    }

    //산책로 등록
    @PostMapping("/walking-path")
    public ModelAndView createWalkingPath(WalkingPathsDTO dto,
                                          @Login UsersDto loginUser, FileVo files, MapDTO mapDTO, String course) throws IOException {
        ModelAndView mav = new ModelAndView();
        dto.setUsersId(loginUser.getId());
        dto.setCreatedBy(loginUser.getName());
        // 추후 결과 따른 msg 추가
        int walkingPathId = walkingPathService.createWalkingPath(dto, files, mapDTO, course);

        System.out.println("게시글 생성 완료 : " + walkingPathId);
        mav.setViewName("redirect:/walking-path/" + walkingPathId);
        return mav;
    }

    @PostMapping(value = "/walking-path/condition/{keyword}", produces = "application/json; charset=utf-8")
    public String conditionSearch(@PathVariable("keyword") String searchWord, @RequestBody SearchDTO searchDTO, Model model) {
        searchDTO.setKeyword(searchWord.equals("null")?null:searchWord);
        List<WalkingPathsMapDTO> walkingPathMapList = dao.searchWalkingPathWithSearchDTO(searchDTO); // 변경
        for(WalkingPathsMapDTO dto : walkingPathMapList) {
            dto.setPhotosList(photoDao.readPhotos(dto.getId()));
            dto.setMapList(mapDao.ReadMap(dto.getId()));
        }
        model.addAttribute("walkingPathList", walkingPathMapList);
        model.addAttribute("keyword", searchWord);
        return "walking-path_search :: #walking-path-list";
    }

    @PostMapping("/walking-path/search")
    public ModelAndView searchByKeyword(String keyword) {
        System.out.println(keyword);
        ModelAndView mav = new ModelAndView();
        List<WalkingPathsMapDTO> walkingPathsMapDTOList = dao.searchWalkingPathByKeyword(keyword);
        for(WalkingPathsMapDTO dto : walkingPathsMapDTOList) {
            dto.setPhotosList(photoDao.readPhotos(dto.getId()));
            dto.setMapList(mapDao.ReadMap(dto.getId()));
        }
        mav.addObject("walkingPathList", walkingPathsMapDTOList);
        mav.addObject("keyword", keyword);
        mav.setViewName("walking-path");
        return mav;
    }
    @GetMapping("/walking-path/write")
    public ModelAndView goToWrite(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("referer", request.getHeader("referer"));
        mav.setViewName("walking-path_write_form");
        return mav;
    }
    @GetMapping("/walking-path/modify/{walking-path-id}")
    public ModelAndView goToModify(@PathVariable("walking-path-id") int walkingPathId, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("referer", request.getHeader("referer"));
        WalkingPathsMapDTO walkingPathsMapDTO = dao.readWalkingPath(walkingPathId);
        walkingPathsMapDTO.setMapList(mapDao.ReadMap(walkingPathsMapDTO.getId()));
        mav.addObject("walkingPath", walkingPathsMapDTO);
        mav.setViewName("walking-path_modify_form");
        return mav;
    }
    @PostMapping("/walking-path/modify")
    public String modifyWalkingPath(WalkingPathsDTO walkingPathsDTO, @Login UsersDto loginUser) {
        walkingPathsDTO.setModifiedBy(loginUser.getName());
        walkingPathsDTO.setId(walkingPathsDTO.getId());
        int result = dao.updateWalkingPath(walkingPathsDTO);
        System.out.println("수정 결과 : " + result);
        return "redirect:/walking-path/"  + walkingPathsDTO.getId();
    }

    //산책로 단품
    @GetMapping("/walking-path/{walking-path-id}")
    public ModelAndView getWalkingPathById(@PathVariable("walking-path-id") int id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        WalkingPathsMapDTO walkingPaths = walkingPathService.readWalkingPathById(id);
        List<PhotosDTO> photosList = photoDao.readPhotos(walkingPaths.getId());
        if(!photosList.isEmpty())
            walkingPaths.setPhotosList(photosList);
        List<MapDTO> mapList = mapDao.ReadMap(walkingPaths.getId());
        walkingPaths.setMapList(mapList);
        if(walkingPaths.getMapList().isEmpty())
            walkingPaths.setMapList(null);

        mav.addObject("walkingPaths", walkingPaths);
        mav.setViewName("walking-path_detail");
        return mav;
    }
    @GetMapping("/walking-path/detail/{walking-path-id}")
    @ResponseBody
    public WalkingPathsMapDTO getWalkingPathMapById(@PathVariable("walking-path-id") int id) {
        return dao.readWalkingPath(id);
    }
    @GetMapping("/walking-path/photos/{walking-path-id}")
    @ResponseBody
    public List<PhotosDTO> getPhotosByWalkingPathId(@PathVariable("walking-path-id") int id) {
        return photoDao.readPhotos(id);
    }
    @GetMapping("/walking-path/delete/{walking-path-id}")
    public String deleteWalkingPathById(@PathVariable("walking-path-id") String id) {
        int result = dao.deleteWalkingPath(Integer.parseInt(id));
        System.out.println("게시글 삭제 : " + result);
        return "redirect:/walking-path";
    }
}