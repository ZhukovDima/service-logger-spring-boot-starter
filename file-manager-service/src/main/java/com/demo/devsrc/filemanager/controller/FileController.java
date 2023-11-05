package com.demo.devsrc.filemanager.controller;

import com.demo.devsrc.filemanager.error.IllegalRequestDataException;
import com.demo.devsrc.filemanager.model.File;
import com.demo.devsrc.filemanager.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = FileController.REST_API)
public class FileController {

    static final String REST_API = "/api/v1/files";

    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<File> save(@RequestParam("file") MultipartFile file) {
        log.info("save {}", file);

        if (!StringUtils.hasLength(file.getOriginalFilename())) {
            throw new IllegalRequestDataException("The field file is empty.");
        }

        File created = fileStorageService.save(file);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_API + "/by-name?name={name}")
                .buildAndExpand(created.getName()).toUri();

        return ResponseEntity.created(uri).body(created);
    }

    @GetMapping(value = "/by-name", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getByName(@RequestParam String name) {
        log.info("getByName {}", name);
        File file = fileStorageService.get(name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(file.getName())
                .build());

        Resource resource = new ByteArrayResource(file.getData());
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
