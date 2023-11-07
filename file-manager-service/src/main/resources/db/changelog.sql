--liquibase formatted sql

--changeset dmitriizhukov:init_schema
create table files
(
    id           SERIAL       NOT NULL
        constraint files_pk primary key,
    name         VARCHAR(100) NOT NULL
        constraint file_uk unique,
    content_type VARCHAR(100) NOT NULL,
    data         oid        NOT NULL
)
