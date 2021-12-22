package com.epam.hospital;

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
    public List<Patient> findAll() {
        log.info("IN PatientServiceRest findAll() find all patients");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Patient>) responseEntity.getBody();
    }

    @Override
    public Integer create(Patient patient) {
        log.info("IN PatientServiceRest create() create patient: {}", patient);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, patient, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer update(Patient patient) {
        log.info("IN PatientServiceRest update() update patient: {}", patient);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Patient> entity = new HttpEntity<>(patient, headers);
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(url,
                HttpMethod.PUT,
                entity,
                Integer.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer delete(Integer patientId) {
        log.info("IN PatientServiceRest delete() delete patient with id: {}", patientId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Patient> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(url + "/" + patientId,
                HttpMethod.DELETE,
                entity,
                Integer.class);
        return responseEntity.getBody();
    }

    @Override
    public Optional<Patient> findById(Integer patientId) {
        log.info("IN PatientServiceRest findById() find patient with id: {}", patientId);
        ResponseEntity<Patient> responseEntity = restTemplate.getForEntity(url + "/" + patientId, Patient.class);
        Patient patient = responseEntity.getBody();
        return Optional.ofNullable(patient);
    }
}
