

CREATE TABLE IF NOT EXISTS SERVICE_INFO (
    id SERIAL PRIMARY KEY ,
    service_name varchar(100)
);

CREATE TABLE IF NOT EXISTS SYS_CONFIG (
    id SERIAL PRIMARY KEY,
    key varchar(100) unique not null ,
    value varchar(380) not null ,
    service_id SERIAL not null ,
    CREATED_AT timestamptz,
    MODIFIED_AT timestamptz,
    CONSTRAINT FK_SYS_CONFIG_SERVICE_INFO FOREIGN KEY (service_id) REFERENCES SERVICE_INFO(id)
);

insert into service_info (service_name) values ('COMMON');
insert into service_info (service_name) values ('USER_SERVICE');
INSERT INTO SYS_CONFIG (KEY, VALUE, service_id) VALUES
    ('JWT_SECRET',
    'W35N6bWa85DkQLt8cHVQrtWkZU5D9uJFBY9GFN3ce5URdE8YQ3D4MgL4QTJvrjZ3FzwbX9WCu3hXNSQtFGFVnQhaHuSywUPZkedxMejQpxun3TJXrNDgcwMcd23fkPKNYwfBHFLNEwC9XARUghyLWJNg4zsWrSEnwsC7vsxfS8gURhSamRntyQGqnryBfMKzkvHL29J7YXLFBPGZ3HNmypeDx9QeRyDtJLWuBjEvzwhASL3gbbLSVhkagJAXxQxa',
     (select id from service_info where service_name='COMMON'));