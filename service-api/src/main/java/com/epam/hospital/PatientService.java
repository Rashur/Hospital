package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import com.epam.hospital.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<Patient> findAll();

    Integer create(Patient patient);

    Integer update(Patient patient);

    Integer delete(Integer patientId);

    Optional<Patient> findById(Integer patientId);
}
