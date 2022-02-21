package com.epam.hospital.mapper;

import com.epam.hospital.NurseDao;
import com.epam.hospital.dto.PatientDto;
import com.epam.hospital.model.Nurse;
import com.epam.hospital.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientMapper {

    private final NurseDao nurseDao;

    @Autowired
    public PatientMapper(NurseDao nurseDao) {
        this.nurseDao = nurseDao;
    }

    public PatientDto toDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patient.getId());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setDiagnosis(patient.getDiagnosis());
        patientDto.setIllnessDate(patient.getIllnessDate());
        patientDto.setNurseIds(patient.getNurseList().stream()
                .map(Nurse::getId)
                .collect(Collectors.toList()));
        return patientDto;
    }

    public Patient toEntity(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setDiagnosis(patientDto.getDiagnosis());
        patient.setIllnessDate(patientDto.getIllnessDate());
        patient.setNurseList(convertAllNurses(patientDto.getNurseIds()));
        return patient;
    }

    private List<Nurse> convertAllNurses(List<Integer> listIds) {
        List<Nurse> nurseList = new ArrayList<>();
        for (Integer id : listIds) {
            nurseList.add(nurseDao.findById(id).get());
        }
        return nurseList;
    }
}
