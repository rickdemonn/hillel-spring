alter table appointment add column version integer;
update appointment set version = 1;