package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("temp-file", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        return this.cloudinary.uploader().upload(file, new HashMap<>()).get("url").toString();
    }
}
