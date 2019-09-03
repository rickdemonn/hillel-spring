create table review (
    id serial primary key ,
    version integer,
    pet_id integer,
    service varchar(255),
    equipment varchar(255),
    specialist_qualification varchar(255),
    effectiveness_of_the_treatment varchar(255),
    overall_rating varchar(255),
    comment text,
    date timestamp
);