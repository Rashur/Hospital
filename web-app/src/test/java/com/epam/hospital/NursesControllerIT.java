package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:app-context-test.xml"})
@Transactional
class NursesControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private NurseService nurseService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shouldReturnNursesPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/nurses")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("nurses"))
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
        assertNotNull(nurseService);
        Nurse savingNurse = new Nurse(4,"Anastasia", "Ivanova");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/nurse")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", savingNurse.getFirstName())
                .param("lastName", savingNurse.getLastName())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/nurses"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/nurses"));

        assertEquals(savingNurse, nurseService.findById(savingNurse.getId()).get());

    }

    @Test
    void deleteNurse() throws Exception {
        assertNotNull(nurseService);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/nurse/1/delete")
        ).andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/nurses"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/nurses"));

        Integer deletedId = nurseService.delete(1);
        Optional<Nurse> findDeletedNurse = nurseService.findById(deletedId);
        assertTrue(findDeletedNurse.isEmpty());
    }

    @Test
    void updateNursePage() throws Exception {
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
        assertNotNull(nurseService);
        Nurse actualNurse = nurseService.findById(1).get();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/nurse/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("firstName", "qwe")
                .param("lastName", "qwe")
        ).andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/nurses"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/nurses"));

        Nurse updatedNurse = nurseService.findById(1).get();
        assertNotEquals(actualNurse, updatedNurse);
    }
}