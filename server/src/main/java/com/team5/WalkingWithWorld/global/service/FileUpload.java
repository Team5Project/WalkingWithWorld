package com.team5.WalkingWithWorld.global.service;

import com.team5.WalkingWithWorld.global.domain.FileVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//이미지 업로드 구현
@Service
public class FileUpload {

    @Value("${file.path}")
    private String path;

    public Map<String, String> upload(FileVo files) throws IOException {

        Map<String, String> filesName = new HashMap<>();

        for (MultipartFile mfile : files.getFiles()) {
            if (mfile.isEmpty())
                return filesName;
            String fileName = LocalDateTime.now().getNano() + mfile.getOriginalFilename();
            try {
                File f = new File(path + fileName);
                System.out.println(f.getPath()); ///
                mfile.transferTo(f);
                filesName.put(fileName, "/images/" + fileName);
                System.out.println("파일이 저장되었어요!!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("오류가 발생했어요!!");
            }
        }

        return filesName;
    }
}
