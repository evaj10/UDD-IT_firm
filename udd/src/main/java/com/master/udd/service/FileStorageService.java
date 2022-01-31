package com.master.udd.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileStorageService {

    @Value("${file.storage.directory}")
    private String fileStorageDir;

    public String saveFile(MultipartFile file) throws IOException {
        String retVal = null;
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "_" + fileName;
            Path path = Paths.get(fileStorageDir + File.separator + fileName);
            Files.write(path, bytes);
            retVal = path.toString();
        }
        return retVal;
    }

}
