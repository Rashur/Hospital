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
    nurse_id int,
    CONSTRAINT patient_pk PRIMARY KEY (id),
    CONSTRAINT patient_nurse_fk FOREIGN KEY (nurse_id) REFERENCES nurse(id)
);

CREATE TABLE nurses_patient(
    id int NOT NULL auto_increment,
    nurse_id int not null,
    patient_id int not null,
    CONSTRAINT nurses_patients_pk PRIMARY KEY (id),
    CONSTRAINT nurse_id_fk FOREIGN KEY (nurse_id) REFERENCES nurse(id),
    CONSTRAINT patient_id_fk FOREIGN KEY (patient_id) REFERENCES patient(id)
);