package com.epam.hospital;

import com.epam.hospital.model.Patient;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

public interface PatientDao {
    List<Patient> findAll();

    void create(Patient patient);

    void update(Patient patient);

    void delete(Patient patient);

    Optional<Patient> findById(Integer patientId);
}
