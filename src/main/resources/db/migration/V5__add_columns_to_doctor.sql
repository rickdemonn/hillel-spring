alter table doctor add column university varchar(255);
alter table doctor add column university_gradation_date date;
alter table doctor add column doc_info_id integer;

update doctor set university = 'unknown';

