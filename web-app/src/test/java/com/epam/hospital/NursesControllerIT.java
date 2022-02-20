package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
class NursesControllerIT {

    private static final String NURSES_URL = "http://localhost:8088/nurses";

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private NurseService nurseService;
    @Autowired
    private RestTemplate restTemplate;

    private MockMvc mockMvc;
    private MockRestServiceServer mockServer;
    private final ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldReturnNursesPage() throws Exception {
        Nurse nurse1 = createNurse(1, "Eigenia", "Liashuk" );
        Nurse nurse2 = createNurse(2, "Ekaterina", "Kozura" );
        Nurse nurse3 = createNurse(3, "Tatsiana", "Rezko" );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(Arrays.asList(nurse1, nurse2, nurse3))));


        mockMvc.perform(
                MockMvcRequestBuilders.get("/nurses")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("nurses"));

        mockServer.verify();
    }

    @Test
    void addNursePage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/nurse")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("nurse"))
                .andExpect(MockMvcResultMatchers.model().attribute("isNew", is(true)))
                .andExpect(MockMvcResultMatchers.model().attribute("nurse", hasProperty("id", is(nullValue()))))
                .andExpect(MockMvcResultMatchers.model().attribute("nurse", hasProperty("firstName", is(nullValue()))))
                .andExpect(MockMvcResultMatchers.model().attribute("nurse", hasProperty("lastName", is(nullValue()))));
    }

    @Test
    void shouldAddNurse() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("1"));

        Nurse savingNurse = new Nurse();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/nurse")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", savingNurse.getFirstName())
                .param("lastName", savingNurse.getLastName())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/nurses"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/nurses"));

        mockServer.verify();
    }

    @Test
    void shouldDeleteNurse() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL + "/" + 1)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.DELETE))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("1"));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/nurse/1/delete")
        ).andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/nurses"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/nurses"));

        mockServer.verify();
    }

    @Test
    void updateNursePage() throws Exception {
        Nurse nurse = new Nurse();

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL + "/" + nurse.getId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(nurse)));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/nurse/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("nurse"))
                .andExpect(MockMvcResultMatchers.model().attribute("isNew", is(false)))
                .andExpect(MockMvcResultMatchers.model().attribute("nurse", hasProperty("id", is(1))))
                .andExpect(MockMvcResultMatchers.model().attribute("nurse", hasProperty("firstName", is("Eigenia"))))
                .andExpect(MockMvcResultMatchers.model().attribute("nurse", hasProperty("lastName", is("Liashuk"))));
    }

    @Test
    void updateNurse() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(NURSES_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/nurse/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("firstName", "qwe")
                .param("lastName", "qwe")
        ).andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/nurses"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/nurses"));

    }

    private Nurse createNurse(int id, String firstName, String lastName) {
        Nurse nurse = new Nurse();
        nurse.setId(id);
        nurse.setFirstName(firstName);
        nurse.setLastName(lastName);
        return nurse;
    }
}