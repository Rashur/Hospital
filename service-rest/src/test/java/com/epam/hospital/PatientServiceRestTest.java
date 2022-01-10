package com.epam.hospital;

import com.epam.hospital.config.ServiceRestTestConfig;
import com.epam.hospital.model.Nurse;
import com.epam.hospital.model.Patient;
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
class PatientServiceRestTest {

    private static final Logger log = LoggerFactory.getLogger(PatientDtoServiceRestTest.class);

    public static final String PATIENTS_URL = "http://localhost:8088/patients";

    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();
    private PatientServiceRest patientServiceRest;

    @BeforeEach
    public void before() {
        mockServer = createServer(restTemplate);
        patientServiceRest = new PatientServiceRest(PATIENTS_URL, restTemplate);
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldFindAllPatients() throws Exception {
        log.info("Execute test IN PatientServiceRestTest shouldFindAllPatients()");
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(4), create(5)))));

        List<Patient> patientsList = patientServiceRest.findAll();

        mockServer.verify();

        assertNotNull(patientsList);
        assertTrue(patientsList.size() > 0);
    }

    @Test
    void shouldCreatePatient() throws Exception {
        log.info("Execute test IN PatientServiceRestTest shouldCreatePatient()");

        Patient patient = create(5);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("5")));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL + "/" + patient.getId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(patient)));

        Integer id = patientServiceRest.create(patient);
        Patient foundPatient = patientServiceRest.findById(id).get();

        mockServer.verify();

        assertNotNull(id);
        assertNotNull(foundPatient);
        assertEquals(patient, foundPatient);
    }

    @Test
    void shouldUpdatePatient() throws Exception {
        log.info("Execute test IN PatientServiceRestTest shouldUpdatePatient()");

        Patient patient = create(5);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("5")));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL + "/" + patient.getId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(patient)));

        Integer id = patientServiceRest.update(patient);
        Patient updatedPatient = patientServiceRest.findById(id).get();

        mockServer.verify();

        assertNotNull(id);
        assertNotNull(updatedPatient);
        assertEquals(patient, updatedPatient);
        assertEquals(patient.getId(), updatedPatient.getId());
    }

    @Test
    void shouldDeletePatient() throws Exception {
        log.info("Execute test IN PatientServiceRestTest shouldDeletePatient()");

        Patient patient = create(2);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL + "/" + patient.getId())))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("2")));

        Integer deletedId = patientServiceRest.delete(patient.getId());

        mockServer.verify();

        assertNotNull(deletedId);
        assertEquals(patient.getId() ,deletedId);
    }

    @Test
    void shouldFindPatientById() throws Exception {
        log.info("Execute test IN PatientServiceRestTest shouldFindPatientById()");

        Patient patient = create(5);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL + "/" + patient.getId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(patient)));

        Patient foundPatient = patientServiceRest.findById(patient.getId()).get();

        mockServer.verify();

        assertNotNull(foundPatient);
        assertEquals(patient.getId(), foundPatient.getId());
        assertEquals(patient, foundPatient);
    }

    private Patient create(Integer index) {
        return new Patient(index, "firstName" + index, "lastName"+ index,
                "diagnosis" + index, LocalDate.of(2021, 10, 15), 2);
    }
}