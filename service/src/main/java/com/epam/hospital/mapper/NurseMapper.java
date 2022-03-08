package com.epam.hospital.mapper;

import com.epam.hospital.NurseDao;
import com.epam.hospital.PatientDao;
import com.epam.hospital.dto.NurseDto;
import com.epam.hospital.model.Nurse;
import com.epam.hospital.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NurseMapper {

    private final PatientDao patientDao;

    @Autowired
    public NurseMapper(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public NurseDto toDto(Nurse nurse) {
        NurseDto nurseDto = new NurseDto();
        nurseDto.setId(nurse.getId());
        nurseDto.setFirstName(nurse.getFirstName());
        nurseDto.setLastName(nurse.getLastName());
        nurseDto.setPatientIds(nurse.getPatientList().stream()
                .map(Patient::getId)
                .collect(Collectors.toList()));
        return nurseDto;
    }

    public Nurse toEntity(NurseDto nurseDto) {
        Nurse nurse = new Nurse();
        nurse.setId(nurseDto.getId());
        nurse.setFirstName(nurseDto.getFirstName());
        nurse.setLastName(nurseDto.getLastName());
        nurse.setPatientList(convertAllPatients(nurseDto.getPatientIds()));
        return nurse;
    }

    private List<Patient> convertAllPatients(List<String> listId) {
        List<Patient> patientList = new ArrayList<>();
        for (String id: listId) {
            patientList.add(patientDao.findById(id));
        }
        return patientList;
    }
}
