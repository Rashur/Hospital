package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class NurseServiceRest implements NurseService {

    private static final Logger log = LogManager.getLogger(NurseServiceRest.class);

    private String url;
    private RestTemplate restTemplate;

    public NurseServiceRest() {
    }

    public NurseServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Nurse> findAll() {
        log.info("IN NurseServiceRest findAll() find all nurses");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Nurse>) responseEntity.getBody();
    }

    @Override
    public Integer create(Nurse nurse) {
        log.info("IN NurseServiceRest create() create nurse: {}", nurse);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, nurse, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer update(Nurse nurse) {
        log.info("IN NurseServiceRest update() update nurse: {}", nurse);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Nurse> entity = new HttpEntity<>(nurse, headers);
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class );
        return responseEntity.getBody();
    }

    @Override
    public Integer delete(Integer nurseId) {
        log.info("IN NurseServiceRest delete() delete nurse with id: {}", nurseId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Nurse> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(url + "/" + nurseId,
                HttpMethod.DELETE,
                entity,
                Integer.class);
        return responseEntity.getBody();
    }

    @Override
    public Optional<Nurse> findById(Integer nurseId) {
        log.info("IN NurseServiceRest findById() find nurse by id: {}", nurseId);
        ResponseEntity<Nurse> responseEntity = restTemplate.getForEntity(url + "/" + nurseId, Nurse.class);
        Nurse nurse = responseEntity.getBody();
        return Optional.ofNullable(nurse);
    }
}
