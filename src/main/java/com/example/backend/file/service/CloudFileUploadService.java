package com.example.backend.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudFileUploadService {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String region;

    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("png", "jpg", "jpeg", "gif", "webp", "bmp", "svg");
    private static final Long MAX_IMG_FILE_SIZE = 10 * 1024 * 1024L;

    private String makeFolder() {
        return "IMAGE/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    public String uploadImg(MultipartFile file) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        String uploadPath = makeFolder();
        String fileName = uploadPath + "/" + UUID.randomUUID();

        try {
            amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), objectMetadata);
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 업로드에 실패했습니다.");
        }
        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }
}