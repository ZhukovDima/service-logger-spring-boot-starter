package com.demo.devsrc.filemanager.service;

import com.demo.devsrc.filemanager.error.IllegalRequestDataException;
import com.demo.devsrc.filemanager.model.File;
import com.demo.devsrc.filemanager.repository.FileRepository;
import com.demo.devsrc.starter.annotation.IgnoreLogging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FileStorageService {

    private final FileRepository fileRepository;

    public File save(@IgnoreLogging MultipartFile file) {
        File fileEntity = readFile(file);
        return fileRepository.save(fileEntity);
    }

    public File get(String name) {
        Optional<File> file = fileRepository.getByName(name);
        if (file.isEmpty()) {
            throw new IllegalRequestDataException("File not found with name " + name);
        }
        return file.get();
    }

    private File readFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            byte[] bytes = file.getBytes();
            return new File(fileName, contentType, bytes);
        } catch (IOException e) {
            throw new IllegalRequestDataException("File read error", e);
        }
    }
}
