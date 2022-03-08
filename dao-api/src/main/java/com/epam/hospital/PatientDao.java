package com.epam.hospital;

import com.epam.hospital.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientDao extends MongoRepository<Patient, Integer> {

    Patient findById(String id);

    void deleteById(String id);
}
