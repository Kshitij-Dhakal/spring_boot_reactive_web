CREATE TABLE person (
    id char(36) PRIMARY KEY ,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    profile_pic varchar(1024),
    password VARCHAR(255) NOT NULL,
    created BIGINT NOT NULL,
    updated BIGINT NOT NULL
);

CREATE TABLE journal (
    id CHAR(36) PRIMARY KEY ,
    user_id CHAR(36) NOT NULL,
    content TEXT,
    created BIGINT NOT NULL,
    updated BIGINT NOT NULL,
    CONSTRAINT fk_user
     FOREIGN KEY(user_id)
     REFERENCES person(id)
     ON DELETE CASCADE
);