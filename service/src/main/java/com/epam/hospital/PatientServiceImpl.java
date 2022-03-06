package com.epam.hospital;

import com.epam.hospital.dto.PatientDto;
import com.epam.hospital.exception.PatientNotFoundException;
import com.epam.hospital.mapper.PatientMapper;
import com.epam.hospital.model.Nurse;
import com.epam.hospital.model.Patient;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class PatientServiceImpl implements PatientService{

    private static final Logger log = LogManager.getLogger(PatientServiceImpl.class);
    private final PatientDao patientDao;
    private final PatientMapper patientMapper;
    private final NurseDao nurseDao;

    @Autowired
    public PatientServiceImpl(final PatientDao patientDao, PatientMapper patientMapper, NurseDao nurseDao) {
        this.patientDao = patientDao;
        this.patientMapper = patientMapper;
        this.nurseDao = nurseDao;
    }

    @Override
    public List<PatientDto> findAll() {
        log.info("IN PatientServiceImpl getAll()");
        List<PatientDto> patientDtoList = new ArrayList<>();
        for (Patient patient : patientDao.findAll()) {
            patientDtoList.add(patientMapper.toDto(patient));
        }
        return patientDtoList;
    }

    @Override
    public PatientDto create(PatientDto patientDto) {
        patientDto.setIllnessDate(LocalDate.now());
        patientDao.save(patientMapper.toEntity(patientDto));
        log.info("IN PatientServiceImpl create() create patient: {}", patientDto);
        return patientDto;
    }

    @Override
    public PatientDto update(PatientDto patientDto, Integer id) {
        Optional<Patient> searchedPatient = patientDao.findById(id);
        if (searchedPatient.isEmpty()) {
            throw new PatientNotFoundException("Patient with id: " + id + " doesn't exist");
        }
        patientDto.setId(id);
        patientDto.setIllnessDate(searchedPatient.get().getIllnessDate());
        patientDao.save(patientMapper.toEntity(patientDto));
        log.info("IN PatientServiceImpl update() update patient: {}", patientDto);
        return patientDto;
    }

    @Override
    public void delete(Integer id) {
        if (patientDao.findById(id).isEmpty()) {
            throw new PatientNotFoundException("Patient with id: " + id + " doesn't exist");
        }
        log.info("IN PatientServiceImpl delete() delete patient with id: {}", id);
        patientDao.deleteById(id);
    }

    @Override
    public Optional<PatientDto> findById(Integer id) {
        Optional<Patient> searchedPatient = patientDao.findById(id);
        if (searchedPatient.isPresent()) {
            PatientDto patientDto = patientMapper.toDto(searchedPatient.get());
            log.info("IN PatientServiceImpl findById() find patient with id: {}", id);
            return Optional.ofNullable(patientDto);
        } else {
            throw new PatientNotFoundException("Patient with id: " + id + " doesn't exist");
        }
    }

    @Override
    public List<PatientDto> allPatientWithNurseListGreaterThan(Long listSize) {
        if (listSize != null) {
            List<PatientDto> patientDtoList = new ArrayList<>();
            for (Patient patient: patientDao.findAllByNurseListCountGreaterThan(listSize)) {
                patientDtoList.add(patientMapper.toDto(patient));
            }
            return patientDtoList;
        } else {
            throw new IllegalArgumentException("List size cannot be empty");
        }
    }

    @Override
    public void createFakePatient() {
        Faker faker = new Faker();
        Patient patient = new Patient();
        boolean isEquals = true;
        patient.setFirstName(faker.name().firstName());
        patient.setLastName(faker.name().lastName());
        patient.setDiagnosis(faker.medical().diseaseName());
        patient.setIllnessDate(faker.date().past(250, TimeUnit.DAYS)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        List<Nurse> listWithAllNurses = nurseDao.findAll();
        while (isEquals) {
            Integer firstNurse = faker.random().nextInt(1, listWithAllNurses.size()-1);
            Integer secondNurse = faker.random().nextInt(1, listWithAllNurses.size()-1);
            if (!firstNurse.equals(secondNurse)) {
                List<Nurse> nurseListForSave = new ArrayList<>();
                nurseListForSave.add(listWithAllNurses.get(firstNurse));
                nurseListForSave.add(listWithAllNurses.get(secondNurse));
                patient.setNurseList(nurseListForSave);
                isEquals = false;
            }
        }
        patientDao.save(patient);
    }
}
