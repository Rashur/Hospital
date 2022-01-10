package com.epam.hospital;

import com.epam.hospital.dto.PatientDto;
import com.epam.hospital.model.Nurse;
import com.epam.hospital.model.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


@SpringBootTest
class PatientsControllerIT {

    private static final String PATIENTS_URL = "http://localhost:8088/patients";
    private static final String NURSES_URL = "http://localhost:8088/nurses";

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientDtoService patientDtoService;
    @Autowired
    private RestTemplate restTemplate;

    private MockMvc mockMvc;
    private MockRestServiceServer mockServer;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldReturnPatientsPage() throws Exception {
        PatientDto patientDto1 = createPatientDto(1, "Dzianis","Berastsen",
                "Skalioz", LocalDate.of(2021, 10, 1),
                "Ekaterina", "Kozura");
        PatientDto patientDto2 = createPatientDto(2, "Kirill", "Yrkovski",
                "qwe", LocalDate.of(2021, 5, 15),
                "Eigenia", "Liashuk");
        PatientDto patientDto3 = createPatientDto(3, "Vladislav", "Ivanov",
                "xzc", LocalDate.of(2021, 1, 29),
                "Tatsiana", "Rezko");
        PatientDto patientDto4 = createPatientDto(4, "Valeriy", "Sergeev",
                "sdfg", LocalDate.of(2021, 5 ,15),
                "Ekaterina", "Kozura");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(patientDto1, patientDto2, patientDto3, patientDto4))));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/patients")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("patients"));

        mockServer.verify();
    }

    @Test
    void shouldReturnAddPatientPage() throws Exception {
        Nurse nurse1 = createNurse(1, "Eigenia", "Liashuk" );
        Nurse nurse2 = createNurse(2, "Ekaterina", "Kozura" );
        Nurse nurse3 = createNurse(3, "Tatsiana", "Rezko" );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(nurse1, nurse2, nurse3))));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/patient")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient"))
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("patient"))
                .andExpect(MockMvcResultMatchers.model().attribute("isNew", is(true)))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("id", is(nullValue()))))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("firstName", is(nullValue()))))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("lastName", is(nullValue()))))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("diagnosis", is(nullValue()))))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("illnessDate", is(nullValue()))))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("nursesId", is(nullValue()))));
    }

    @Test
    void shouldAddPatient() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1"));

        LocalDate addedDate = LocalDate.now();
        Patient expectedPatient = new Patient(5, "Yahor", "Krechka", "Slepota", addedDate, 0);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/patient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", expectedPatient.getFirstName())
                .param("lastName", expectedPatient.getLastName())
                .param("diagnosis", expectedPatient.getDiagnosis())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/patients"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patients"));

    }

    @Test
    void shouldReturnUpdatePatientPage() throws Exception {
        Patient patient = new Patient(1, "Dzianis","Berastsen",
                "Skalioz", LocalDate.of(2021, 10, 1), 2);
        Nurse nurse1 = createNurse(1, "Eigenia", "Liashuk" );
        Nurse nurse2 = createNurse(2, "Ekaterina", "Kozura" );
        Nurse nurse3 = createNurse(3, "Tatsiana", "Rezko" );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL + "/" + patient.getId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(patient)));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(nurse1, nurse2, nurse3))));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/patient/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("patient"))
                .andExpect(MockMvcResultMatchers.model().attribute("isNew", false))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("id", is(1))))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("firstName", is("Dzianis"))))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("lastName", is("Berastsen"))))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("diagnosis", is("Skalioz"))))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("illnessDate", is(LocalDate.of(2021,10,1)))))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("nursesId", is(2))));
    }

    @Test
    void shouldUpdatePatient() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/patient/1")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", "1")
                    .param("firstName", "qwe")
                    .param("lastName", "qwe")
                    .param("diagnosis", "qwe")
                    .param("nurseId", "3")
        ).andExpect(MockMvcResultMatchers.status().isFound())
        .andExpect(MockMvcResultMatchers.view().name("redirect:/patients"))
        .andExpect(MockMvcResultMatchers.redirectedUrl("/patients"));
    }

    @Test
    void deletePatient() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PATIENTS_URL + "/" + 1)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.DELETE))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1"));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/patient/1/delete")
        ).andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/patients"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patients"));
    }

    private PatientDto createPatientDto(int id, String firstName,String lastName,
                                        String diagnosis, LocalDate illnessDate,
                                        String nurseFirstName, String nurseLastName) {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(id);
        patientDto.setFirstName(firstName);
        patientDto.setLastName(lastName);
        patientDto.setDiagnosis(diagnosis);
        patientDto.setIllnessDate(illnessDate);
        patientDto.setNurseFirstName(nurseFirstName);
        patientDto.setNurseLastName(nurseLastName);
        return patientDto;
    }

    private Nurse createNurse(int id, String firstName, String lastName) {
        Nurse nurse = new Nurse();
        nurse.setId(id);
        nurse.setFirstName(firstName);
        nurse.setLastName(lastName);
        return nurse;
    }
}