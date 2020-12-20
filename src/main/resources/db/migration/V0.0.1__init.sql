CREATE TABLE IF NOT EXISTS jytuser (
    user_id serial PRIMARY KEY,
    email varchar(320) UNIQUE NOT NULL,
    password varchar(320) NOT NULL ,
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    CREATED_AT timestamptz,
    MODIFIED_AT timestamptz
);

CREATE INDEX IF NOT EXISTS index_user_email ON jytuser(email);


CREATE TABLE IF NOT EXISTS role (
    role_id SERIAL PRIMARY KEY,
    role_name varchar(10) NOT NULL,
    CREATED_AT timestamptz,
    MODIFIED_AT timestamptz
);

CREATE TABLE IF NOT EXISTS user_role (
    user_id serial NOT NULL ,
    role_id SERIAL NOT NULL ,
    PRIMARY KEY (role_id, user_id),
    CONSTRAINT FK_USER_ROLE_USER_ID FOREIGN KEY (user_id) REFERENCES jytuser(user_id),
    CONSTRAINT FK_USER_ROLE_ROLE_ID FOREIGN KEY (role_id) REFERENCES role(role_id),
    CREATED_AT timestamptz,
    MODIFIED_AT timestamptz
);

