package com.demo.rrss.rrssbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    private static String UPLOADED_FOLDER = "C://temp//";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile) {

        if (uploadfile.isEmpty()) {
            return new ResponseEntity<>("Please select a file!", HttpStatus.BAD_REQUEST);
        }

        String mimeType = uploadfile.getContentType();
        if(!isImage(mimeType)) {
            return new ResponseEntity<>("Invalid file type. Only image files are allowed.", HttpStatus.BAD_REQUEST);
        }

        try {
            saveUploadedFiles(uploadfile);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), HttpStatus.OK);

    }

    private void saveUploadedFiles(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;
        Path path = Paths.get(UPLOADED_FOLDER + newFilename);
        Files.write(path, bytes);
    }

    private boolean isImage(String mimeType) {
        return mimeType.equals("image/jpeg") || mimeType.equals("image/png") || mimeType.equals("image/jpg");
    }

}