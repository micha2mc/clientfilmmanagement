package com.zakado.zkd.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface UploadFileService {
    Resource load(String filename) throws MalformedURLException;

    String copy(MultipartFile file) throws IOException;
    void deleteImage(String nombreArchivo);
}
