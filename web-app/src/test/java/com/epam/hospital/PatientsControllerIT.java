package com.epam.hospital;

import com.epam.hospital.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Disabled
class PatientsControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private PatientService patientService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shouldReturnPatientsPage() {
    }

    @Test
    void shouldReturnAddPatientPage() throws Exception {
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
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("nursesId", is(nullValue()))))
                .andExpect(MockMvcResultMatchers.model().attribute("nurses", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("firstName", is("Eigenia")),
                                hasProperty("lastName", is("Liashuk"))
                        )
                )))
                .andExpect(MockMvcResultMatchers.model().attribute("nurses", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("firstName", is("Ekaterina")),
                                hasProperty("lastName", is("Kozura"))
                        )
                )))
                .andExpect(MockMvcResultMatchers.model().attribute("nurses", hasItem(
                        allOf(
                                hasProperty("id", is(3)),
                                hasProperty("firstName", is("Tatsiana")),
                                hasProperty("lastName", is("Rezko"))
                        )
                )));
    }

    @Test
    void shouldAddPatient() throws Exception {
        assertNotNull(patientService);
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

        assertEquals(expectedPatient, patientService.findById(expectedPatient.getId()).get());
    }

    @Test
    void shouldReturnUpdatePatientPage() throws Exception {
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
                .andExpect(MockMvcResultMatchers.model().attribute("patient", hasProperty("nursesId", is(2))))
                .andExpect(MockMvcResultMatchers.model().attribute("nurses", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("firstName", is("Eigenia")),
                                hasProperty("lastName", is("Liashuk"))
                        )
                )))
                .andExpect(MockMvcResultMatchers.model().attribute("nurses", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("firstName", is("Ekaterina")),
                                hasProperty("lastName", is("Kozura"))
                        )
                )))
                .andExpect(MockMvcResultMatchers.model().attribute("nurses", hasItem(
                        allOf(
                                hasProperty("id", is(3)),
                                hasProperty("firstName", is("Tatsiana")),
                                hasProperty("lastName", is("Rezko"))
                        )
                )));
    }

    @Test
    void shouldUpdatePatient() throws Exception {
        assertNotNull(patientService);
        Patient actualPatient = patientService.findById(1).get();

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

        assertNotEquals(actualPatient, patientService.findById(1).get());


    }

    @Test
    void deletePatient() throws Exception {
        assertNotNull(patientService);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/patient/1/delete")
        ).andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/patients"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patients"));

        Integer deletedId = patientService.delete(1);
        Optional<Patient> deletedPatient = patientService.findById(deletedId);
        assertTrue(deletedPatient.isEmpty());
    }
}