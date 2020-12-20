alter table sys_config add active BOOLEAN;
update sys_config set active=true where id > 0;