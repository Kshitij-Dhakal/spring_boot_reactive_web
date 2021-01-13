CREATE TABLE person (
    id char(36) PRIMARY KEY ,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created BIGINT NOT NULL,
    updated BIGINT NOT NULL
);