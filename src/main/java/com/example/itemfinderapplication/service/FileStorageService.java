package com.example.itemfinderapplication.service;

import lombok.AccessLevel;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileStorageService {

    @Value("${file.upload-dir}")
    String uploadDir;

    public String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + file.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );
            return "/uploads/" + fileName;
        } catch (IOException ex) {
            throw new RuntimeException("File yuklenme xetasi ");
        }
    }
}
