package com.example.ctca.service;

import com.example.ctca.model.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {

    FileDTO uploadFile(MultipartFile multipartFile, String type);

    List<FileDTO> uploadMutilFile(MultipartFile[] multipartFiles, String type);

    FileDTO downloadFile(String filePath);

}
