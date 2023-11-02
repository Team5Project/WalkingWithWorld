package com.team5.WalkingWithWorld.walkingPaths.controller;

import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class WalkingPathsController2 {
    private WalkingPathsRepository walkingPathsRepository;

    WalkingPathsController2(WalkingPathsRepository walkingPathsRepository) {
        this.walkingPathsRepository = walkingPathsRepository;
    }

    @GetMapping
    public Page<WalkingPaths> getAll(@PageableDefault(size = 2) Pageable pageable) {
        return walkingPathsRepository.findAll(pageable);
    }
}
