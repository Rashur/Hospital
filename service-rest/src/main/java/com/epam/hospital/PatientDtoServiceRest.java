package com.epam.hospital;

import com.epam.hospital.dto.DateRange;
import com.epam.hospital.dto.PatientDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PatientDtoServiceRest implements PatientDtoService{

    private static final Logger log = LogManager.getLogger(NurseServiceRest.class);

    private String url;
    private RestTemplate restTemplate;

    public PatientDtoServiceRest() {

    }

    public PatientDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<PatientDto> findAllPatientsWithNurseName() {
        log.info("IN PatientDtoServiceRest findAllPatientsWithNurseName() find all patients with nurses name");
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<PatientDto>) responseEntity.getBody();
    }

    @Override
    public List<PatientDto> findAllPatientsInRange(DateRange dateRange) {
        log.info("IN PatientDtoServiceRest findAllPatientsInRange() find all patients in date range from: {} to: {}", dateRange.getDateFrom(), dateRange.getDateTo());
        ResponseEntity<List> responseEntity = restTemplate.postForEntity(url + "/date-range", dateRange, List.class);
        return (List<PatientDto>) responseEntity.getBody();
    }
}
