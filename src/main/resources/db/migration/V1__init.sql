create table appointment (
    id serial primary key ,
    busy_hour integer ,
    doc_id integer ,
    local_date date ,
    pet_id integer
);

create table doctor (
    id serial primary key ,
    name varchar(255) ,
    is_sick varchar(255)
);

create table doctor_specializations (
    doctor_id integer ,
    specializations varchar(255)
);

create table pet (
    id serial primary key ,
    name varchar(255)
);