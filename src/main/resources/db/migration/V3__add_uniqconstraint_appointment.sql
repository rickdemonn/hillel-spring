ALTER TABLE appointment
ADD CONSTRAINT appointment_unique UNIQUE (doc_id, busy_hour, local_date);