package com.epam.hospital;

import com.epam.hospital.dto.NurseDto;
import com.epam.hospital.dto.PatientDto;
import com.epam.hospital.mapper.PatientMapper;
import com.epam.hospital.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService{

    private static final Logger log = LogManager.getLogger(PatientServiceImpl.class);
    private final PatientDao patientDao;
    private final PatientMapper patientMapper;

    @Autowired
    public PatientServiceImpl(final PatientDao patientDao, PatientMapper patientMapper) {
        this.patientDao = patientDao;
        this.patientMapper = patientMapper;
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
    public void create(PatientDto patientDto) {
        patientDto.setIllnessDate(LocalDate.now());
        log.info("IN PatientServiceImpl create() create patient: {}", patientDto);
        patientDao.create(patientMapper.toEntity(patientDto));
    }

    @Override
    public void update(PatientDto patientDto) {
        log.info("IN PatientServiceImpl update() update patient: {}", patientDto);
        patientDao.update(patientMapper.toEntity(patientDto));
    }

    @Override
    public void delete(PatientDto patientDto) {
        log.info("IN PatientServiceImpl delete() delete patient: {}", patientDto);
        Patient patient = patientMapper.toEntity(patientDto);
        patientDao.delete(patient);
    }

    @Override
    public Optional<PatientDto> findById(Integer patientId) {
        log.info("IN PatientServiceImpl findById() find patient with id: {}", patientId);
        PatientDto patientDto = patientMapper.toDto(patientDao.findById(patientId).get());
        return Optional.ofNullable(patientDto);
    }
}
