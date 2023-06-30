CREATE TABLE users (
                       id          BIGINT AUTO_INCREMENT,
                       username    VARCHAR(30) NOT NULL UNIQUE,
                       password    VARCHAR(80) NOT NULL,
                       email       VARCHAR(50) UNIQUE,
                       PRIMARY KEY (id)
);

CREATE TABLE roles (
                       id          INT AUTO_INCREMENT,
                       name        VARCHAR(50) NOT NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE users_roles (
                             user_id     BIGINT NOT NULL,
                             role_id     INT NOT NULL,
                             PRIMARY KEY (user_id, role_id),
                             FOREIGN KEY (user_id) REFERENCES users (id),
                             FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO roles (name)
VALUES
    ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO users (username, password, email)
VALUES
    ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com'),
    ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com');

INSERT INTO users_roles (user_id, role_id)
VALUES
    (1, 1),
    (2, 2);
