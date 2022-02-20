DROP TABLE IF EXISTS patient;

DROP TABLE IF EXISTS nurse;

DROP TABLE IF EXISTS nurses_patients;

CREATE TABLE nurse(
    id int NOT NULL auto_increment,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    CONSTRAINT nurse_pk PRIMARY KEY (id)
);

CREATE TABLE patient(
    id int NOT NULL auto_increment,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    diagnosis varchar(100),
    illness_date date NOT NULL,
    CONSTRAINT patient_pk PRIMARY KEY (id)
);

CREATE TABLE nurse_patient(
    nurse_id int,
    patient_id int,
    CONSTRAINT nurses_patients_pk PRIMARY KEY (nurse_id, patient_id),
    CONSTRAINT nurse_id_fk FOREIGN KEY (nurse_id) REFERENCES nurse(id),
    CONSTRAINT patient_id_fk FOREIGN KEY (patient_id) REFERENCES patient(id)
);