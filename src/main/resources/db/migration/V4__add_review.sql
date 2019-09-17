create table review (
    id serial primary key ,
    version integer,
    pet_id integer,
    service integer,
    equipment integer,
    specialist_qualification integer,
    effectiveness_of_the_treatment integer,
    overall_rating integer,
    comment text,
    date timestamp
);