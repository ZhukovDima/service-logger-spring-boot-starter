package com.demo.devsrc.filemanager.web;

import com.demo.devsrc.filemanager.model.File;
import com.demo.devsrc.filemanager.service.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class FileControllerTest {

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private FileController fileController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    @Test
    public void saveFile_ShouldReturn201Created() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());
        File created = new File();
        created.setName("test.txt");
        when(fileStorageService.save(any(MultipartFile.class))).thenReturn(created);

        mockMvc.perform(multipart("/api/v1/files").file(file))
                .andExpect(status().isCreated());

        verify(fileStorageService, times(1)).save(any(MultipartFile.class));
    }

    @Test
    public void getByName_ShouldReturn200Ok() throws Exception {
        String name = "test.txt";
        File file = new File();
        file.setName(name);
        file.setData("test".getBytes());
        file.setContentType("text/plain");
        when(fileStorageService.get(anyString())).thenReturn(file);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/files/by-name").param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain"))
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"test.txt\""));

        verify(fileStorageService, times(1)).get(anyString());
    }
}