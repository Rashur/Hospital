package com.epam.hospital;

import com.epam.hospital.config.ServiceRestTestConfig;
import com.epam.hospital.dto.DateRange;
import com.epam.hospital.dto.PatientDto;
import com.epam.hospital.model.Nurse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.MockRestServiceServer.createServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@Import({ServiceRestTestConfig.class})
class PatientDtoServiceRestTest {

    private static final Logger log = LoggerFactory.getLogger(PatientDtoServiceRestTest.class);

    public static final String PATIENTS_URL = "http://localhost:8088/patients";

    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();
    private PatientDtoServiceRest patientDtoServiceRest;

    @BeforeEach
    public void before() {
        mockServer = createServer(restTemplate);
        patientDtoServiceRest = new PatientDtoServiceRest(PATIENTS_URL, restTemplate);
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldFindAllPatientsWithNurseName() throws Exception {
        log.info("Execute test IN PatientDtoServiceRestTest shouldFindAllNurses()");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(4), create(5)))));

        List<PatientDto> patientDtoList = patientDtoServiceRest.findAllPatientsWithNurseName();

        mockServer.verify();

        assertNotNull(patientDtoList);
        assertTrue(patientDtoList.size() > 0);
    }

    @Test
    void shouldFindAllPatientsInRange() throws Exception {
//        log.info("Execute test IN PatientDtoServiceRestTest shouldFindAllPatientsInRange()");
//        DateRange dateRange = new DateRange(Date.valueOf(LocalDate.of(2021, 9, 1)),
//                Date.valueOf(LocalDate.of(2022, 1, 9)));
//
//        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL + "/date-range")))
//                .andExpect(method(HttpMethod.POST))
//                .andRespond(withStatus(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(mapper.writeValueAsString(dateRange)));
//
//        List<PatientDto> patientDtoList = patientDtoServiceRest.findAllPatientsInRange(dateRange);
//
//        mockServer.verify();
//
//        assertNotNull(patientDtoList);
    }
    //TODO refactor PatientDto generator
    private PatientDto create(Integer index) {
        return new PatientDto();
    }
}