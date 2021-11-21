package com.epam.hospital;

import com.epam.hospital.model.Patient;

import java.util.List;
import java.util.Optional;

//TODO can be extended from generic crudDao
public interface PatientDao {
    List<Patient> findAll();

    Integer create(Patient patient);

    Integer update(Patient patient);

    Integer delete(Integer patientId);

    Optional<Patient> findById(Integer patientId);
}
