package com.epam.hospital;

import com.epam.hospital.dto.NurseDto;
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
    public List<NurseDto> findAll() {
        log.info("IN NurseServiceRest findAll() find all nurses");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<NurseDto>) responseEntity.getBody();
    }

    @Override
    public void create(NurseDto nurseDto) {
        log.info("IN NurseServiceRest create() create nurse: {}", nurseDto);
        restTemplate.postForEntity(url, nurseDto, Integer.class);
    }

    @Override
    public void update(NurseDto nurseDto) {
        log.info("IN NurseServiceRest update() update nurse: {}", nurseDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<NurseDto> entity = new HttpEntity<>(nurseDto, headers);
        restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class );
    }

    @Override
    public void delete(NurseDto nurseDto) {
        log.info("IN NurseServiceRest delete() delete nurse: {}", nurseDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<NurseDto> entity = new HttpEntity<>(nurseDto ,headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Integer.class);
    }

    @Override
    public Optional<NurseDto> findById(Integer nurseId) {
        log.info("IN NurseServiceRest findById() find nurse by id: {}", nurseId);
        ResponseEntity<NurseDto> responseEntity = restTemplate.getForEntity(url + "/" + nurseId, NurseDto.class);
        NurseDto nurseDto = responseEntity.getBody();
        return Optional.ofNullable(nurseDto);
    }
}
