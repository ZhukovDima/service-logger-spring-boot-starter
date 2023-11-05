package com.demo.devsrc.filemanager;

import org.testcontainers.containers.PostgreSQLContainer;

public class FileManagerPostgresqlContainer extends PostgreSQLContainer<FileManagerPostgresqlContainer> {

    private static final String IMAGE_VERSION = "postgres:16.0";
    private static FileManagerPostgresqlContainer container;

    private FileManagerPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static FileManagerPostgresqlContainer getInstance() {
        if (container == null) {
            container = new FileManagerPostgresqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
