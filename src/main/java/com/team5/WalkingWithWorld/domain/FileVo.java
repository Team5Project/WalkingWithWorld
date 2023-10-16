package com.team5.WalkingWithWorld.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class FileVo {
    private MultipartFile[] files;
}
