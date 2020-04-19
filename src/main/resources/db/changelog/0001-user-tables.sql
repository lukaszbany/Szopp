CREATE TABLE app_user
(
    id       BIGSERIAL PRIMARY KEY,
    username TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE app_role
(
    id   BIGINT PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE app_user_role
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL references app_user(id),
    role_id BIGINT NOT NULL references app_role(id)
);

INSERT INTO app_role (id, name)
VALUES (1, 'ADMIN'),
       (2, 'STAFF'),
       (3, 'REGISTERED_USER');

INSERT INTO app_user (username, password) VALUES ('admin', '$2a$10$/yexgb6dZCVT0ru7RqK7VugsHQfxPW8rI8eyld3Njvc2DxKyIGls6'); -- admin:admin
INSERT INTO app_user_role(user_id, role_id) VALUES (1, 1);