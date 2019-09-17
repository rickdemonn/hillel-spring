alter table doctor add column version integer;

update doctor set version = 1;