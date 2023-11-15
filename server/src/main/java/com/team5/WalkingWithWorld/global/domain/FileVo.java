package com.team5.WalkingWithWorld.global.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class FileVo {
    @Size
    @Min(0)
    @Max(5)
    private List<MultipartFile> files;
}
