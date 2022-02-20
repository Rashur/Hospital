package com.epam.hospital;

import com.epam.hospital.config.ServiceRestTestConfig;
import com.epam.hospital.dto.NurseDto;
import com.epam.hospital.model.Nurse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.MockRestServiceServer.createServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@Import({ServiceRestTestConfig.class})
class NurseServiceRestTest {

    private static final Logger log = LoggerFactory.getLogger(NurseServiceRest.class);

    public static final String NURSES_URL = "http://localhost:8088/nurses";

    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();
    private NurseServiceRest nurseServiceRest;

    @BeforeEach
    public void before() {
        mockServer = createServer(restTemplate);
        nurseServiceRest = new NurseServiceRest(NURSES_URL, restTemplate);
    }

    @Test
    void shouldFindAllNurses() throws Exception{
        log.info("Execute test IN NurseServiceRestTest shouldFindAllNurses()");
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(4), create(5)))));
        List<NurseDto> nursesList = nurseServiceRest.findAll();

        mockServer.verify();
        assertNotNull(nursesList);
        assertTrue(nursesList.size() > 0);
    }

    @Test
    void shouldCreateNurse() throws Exception {
        log.info("Execute test IN NurseServiceRestTest shouldCreateNurse()");
        Nurse nurse = new Nurse();

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("5")));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL + "/" + nurse.getId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(nurse)));

        Integer id = 0 /*nurseServiceRest.create(nurse);*/;
        NurseDto foundNurse = nurseServiceRest.findById(id).get();

        mockServer.verify();

        assertNotNull(id);
        assertNotNull(foundNurse);
        assertEquals(nurse, foundNurse);
    }

    @Test
    void shouldUpdateNurse() throws Exception {
        log.info("Execute test IN NurseServiceRestTest shouldUpdateNurse()");

        Nurse nurse = new Nurse();

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString("5")));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL + "/" + nurse.getId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(nurse)));

        Integer id = 0 /* nurseServiceRest.update(nurse);*/;
        NurseDto updatedNurse = nurseServiceRest.findById(id).get();

        mockServer.verify();

        assertNotNull(id);
        assertNotNull(updatedNurse);
        assertEquals(nurse, updatedNurse);
        assertEquals(nurse.getId(), updatedNurse.getId());
    }

    @Test
    void shouldDeleteNurse() throws Exception {
        log.info("Execute test IN NurseServiceRestTest shouldDeleteNurse()");

        Nurse nurse = new Nurse();

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL + "/" + nurse.getId())))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString("2")));

       Integer deletedId = 0 /*nurseServiceRest.delete(nurse.getId());*/;

        mockServer.verify();

        assertNotNull(deletedId);
        assertEquals(nurse.getId() ,deletedId);
    }

    @Test
    void shouldFindNurseById() throws Exception {
        log.info("Execute test IN NurseServiceRestTest shouldFindNurseById()");

        Nurse nurse = new Nurse();

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL + "/" + nurse.getId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(nurse)));

        NurseDto foundNurse = nurseServiceRest.findById(nurse.getId()).get();

        mockServer.verify();

        assertNotNull(foundNurse);
        assertEquals(nurse.getId(), foundNurse.getId());
        assertEquals(nurse, foundNurse);

    }

    //TODO refactor Nurse generator
    private Nurse create(Integer index) {
        return new Nurse();
    }
}