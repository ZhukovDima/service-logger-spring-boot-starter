package com.demo.devsrc.filemanager.controller;

import com.demo.devsrc.filemanager.model.File;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ContextConfiguration(initializers = {FileControllerIT.Initializer.class})
public class FileControllerIT {

    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16.0")
            .withDatabaseName("test")
            .withUsername("user")
            .withPassword("password");

    @BeforeAll
    static void startDb() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void stopDb() {
        postgreSQLContainer.stop();
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @PostConstruct
    public void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void saveFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());
        File created = new File();
        created.setName("test.txt");

        mockMvc.perform(MockMvcRequestBuilders.multipart(FileController.REST_API).file(file))
                .andExpect(status().isCreated());
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.driver-class-name=" + postgreSQLContainer.getDriverClassName(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(applicationContext);
        }
    }
}
