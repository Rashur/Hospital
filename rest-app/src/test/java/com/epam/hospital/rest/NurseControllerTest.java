package com.epam.hospital.rest;

import com.epam.hospital.NurseService;
import com.epam.hospital.dto.NurseDto;
import com.epam.hospital.model.Nurse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NurseControllerTest {

    private static final Logger log = LoggerFactory.getLogger(NurseControllerTest.class);

    @InjectMocks
    private NurseController nurseController;
    @Mock
    private NurseService nurseService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(nurseController).build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(nurseService);
    }

    @Test
    void allNurses() throws Exception {
        log.info("Execute test IN NurseControllerTest allNurses()");
        //Mockito.when(nurseService.findAll()).thenReturn(Arrays.asList(create(1), create(2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/nurses"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("firstName1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("lastName1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("firstName2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", Matchers.is("lastName2")));

    }

    @Test
    void createNurse() throws Exception {
        log.info("Execute test IN NurseControllerTest createNurse()");
        NurseDto nurse = create(0);
        //Mockito.when(nurseService.create(nurse)).thenReturn(0);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/nurses")
                .content(objectMapper.writeValueAsString(nurse))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse();

        Integer returnedId = objectMapper.readValue(response.getContentAsString(), Integer.class);
        assertNotNull(returnedId);
        assertEquals(returnedId, nurse.getId());

    }

    @Test
    void findNurseById() throws Exception {
        log.info("Execute test IN NurseControllerTest findNurseById()");
        NurseDto nurse = create(0);
      //  Mockito.when(nurseService.create(nurse)).thenReturn(0);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/nurses")
                .content(objectMapper.writeValueAsString(nurse))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse();
        Integer returnedId = objectMapper.readValue(response.getContentAsString(), Integer.class);
        assertNotNull(returnedId);
        assertEquals(nurse.getId(), returnedId);

        MockHttpServletResponse findResponse = mockMvc.perform(MockMvcRequestBuilders.get("/nurses/" + returnedId)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        Nurse foundNurse = objectMapper.readValue(findResponse.getContentAsString(), Nurse.class);

        assertEquals(nurse, foundNurse);
    }

    @Test
    void updateNurse() throws Exception {
        log.info("Execute test IN NurseControllerTest updateNurse()");
        NurseDto nurse = create(0);
       // Mockito.when(nurseService.create(nurse)).thenReturn(0);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/nurses")
                .content(objectMapper.writeValueAsString(nurse))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse();
        Integer returnedId = objectMapper.readValue(response.getContentAsString(), Integer.class);
        assertNotNull(returnedId);
        assertEquals(nurse.getId(), returnedId);

        nurse.setFirstName("qwe");

     //   Mockito.when(nurseService.update(nurse)).thenReturn(0);

        MockHttpServletResponse updateResponse = mockMvc.perform(MockMvcRequestBuilders.put("/nurses")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(nurse))
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

        Integer updatedId = objectMapper.readValue(updateResponse.getContentAsString(), Integer.class);
        assertEquals(nurse.getId(), updatedId);
    }

    @Test
    void deleteNurse() throws Exception {
        log.info("Execute test IN NurseControllerTest deleteNurse()");
        NurseDto nurse = create(0);
      //  Mockito.when(nurseService.create(nurse)).thenReturn(0);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/nurses")
                .content(objectMapper.writeValueAsString(nurse))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse();
        Integer returnedId = objectMapper.readValue(response.getContentAsString(), Integer.class);
        assertNotNull(returnedId);
        assertEquals(nurse.getId(), returnedId);

       // Mockito.when(nurseService.delete(returnedId)).thenReturn(0);
        MockHttpServletResponse deleteResponse = mockMvc.perform(MockMvcRequestBuilders.delete("/nurses/" + returnedId)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

        Integer deletedId = objectMapper.readValue(deleteResponse.getContentAsString(), Integer.class);
        assertEquals(returnedId, deletedId);
    }

    private NurseDto create(Integer index) {
        return new NurseDto();
    }
}