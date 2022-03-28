package com.epam.hospital;

import com.epam.hospital.dto.PatientDto;
import com.epam.hospital.model.Nurse;
import com.epam.hospital.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<Patient> findAll();

    PatientDto create(PatientDto patientDto);

    PatientDto update(PatientDto patientDto, Integer id);

    void delete(Integer id);

    Optional<Patient> findById(Integer id);

    List<PatientDto> allPatientWithNurseListGreaterThan(Long listSize);

    void createFakePatient();
}
