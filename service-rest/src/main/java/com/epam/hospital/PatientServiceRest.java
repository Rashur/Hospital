package com.epam.hospital;

import com.epam.hospital.dto.PatientDto;
import com.epam.hospital.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceRest implements PatientService{

    private static final Logger log = LogManager.getLogger(NurseServiceRest.class);

    private String url;
    private RestTemplate restTemplate;

    public PatientServiceRest() {
    }

    public PatientServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }


    @Override
    public List<PatientDto> findAll() {
        log.info("IN PatientServiceRest findAll() find all patients");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<PatientDto>) responseEntity.getBody();
    }

    @Override
    public void create(PatientDto patientDto) {
        log.info("IN PatientServiceRest create() create patient: {}", patientDto);
        restTemplate.postForEntity(url, patientDto, Integer.class);
    }

    @Override
    public void update(PatientDto patientDto) {
        log.info("IN PatientServiceRest update() update patient: {}", patientDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientDto> entity = new HttpEntity<>(patientDto, headers);
        restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
    }

    @Override
    public void delete(PatientDto patientDto) {
        log.info("IN PatientServiceRest delete() delete patient: {}", patientDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientDto> entity = new HttpEntity<>(patientDto, headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Integer.class);
    }

    @Override
    public Optional<PatientDto> findById(Integer patientId) {
        log.info("IN PatientServiceRest findById() find patient with id: {}", patientId);
        ResponseEntity<PatientDto> responseEntity = restTemplate.getForEntity(url + "/" + patientId, PatientDto.class);
        PatientDto patientDto = responseEntity.getBody();
        return Optional.ofNullable(patientDto);
    }
}
