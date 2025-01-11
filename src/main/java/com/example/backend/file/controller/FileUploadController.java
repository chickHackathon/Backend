package com.example.backend.file.controller;

import com.example.backend.common.BaseResponse;
import com.example.backend.file.service.CloudFileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final CloudFileUploadService cloudFileUploadService;

    @PostMapping("/img/upload")
    public BaseResponse<String> uploadImage(MultipartFile imgFile) {
        String imgUrl = cloudFileUploadService.uploadImg(imgFile);
        return new BaseResponse<>(imgUrl);
    }
}