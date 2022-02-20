package com.epam.hospital;

import com.epam.hospital.dto.PatientDto;
import com.epam.hospital.model.Nurse;
import com.epam.hospital.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<PatientDto> findAll();

    void create(PatientDto patientDto);

    void update(PatientDto patientDto);

    void delete(PatientDto patientDto);

    Optional<PatientDto> findById(Integer patientId);
}
