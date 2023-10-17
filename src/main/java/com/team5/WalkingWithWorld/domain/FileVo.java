package com.team5.WalkingWithWorld.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class FileVo {
    private List<MultipartFile> files;
}
