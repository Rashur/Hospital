package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
class NurseServiceImplIT {

    private static final Logger log = LogManager.getLogger(NurseServiceImplIT.class);
    private final NurseServiceImpl nurseService;

    public NurseServiceImplIT(@Autowired final NurseServiceImpl nurseService) {
        this.nurseService = nurseService;
    }

    @Test
    void shouldReturnAllNurses() {
        log.info("Execute nurseService test shouldReturnAllNurses()");
        assertNotNull(nurseService);
        List<Nurse> allNurses = nurseService.findAll();
        assertNotNull(allNurses);
        assertEquals(3, allNurses.size());
    }

    @Test
    void shouldCreateNurseSuccessfully() {
        log.info("Execute nurseService test shouldCreateNurseSuccessfully()");
        assertNotNull(nurseService);
        Nurse expectedNurse = new Nurse(null,"Tatsiana", "Levchuk");
        Integer nurseId = nurseService.create(expectedNurse);
        assertNotNull(nurseId);
        expectedNurse.setId(nurseId);
        Optional<Nurse> actualNurse = nurseService.findById(nurseId);
        assertTrue(actualNurse.isPresent());
        assertEquals(expectedNurse, actualNurse.get());
    }

    @Test
    void shouldFindNurseById() {
        log.info("Execute nurseService test shouldFindNurseById()");
        assertNotNull(nurseService);
        Nurse expectedNurse = new Nurse(1, "Eigenia", "Liashuk");
        Optional<Nurse> actualNurse = nurseService.findById(1);
        assertTrue(actualNurse.isPresent());
        assertEquals(expectedNurse, actualNurse.get());
    }

    @Test
    void shouldUpdateNurse() {
        log.info("Execute nurseService test shouldUpdateNurse()");
        assertNotNull(nurseService);
        Nurse expectedNurse = nurseService.findById(1).get();
        Nurse actualNurse = new Nurse(1,"Ekaterina", "Ivanova");
        expectedNurse.setFirstName("Ekaterina");
        expectedNurse.setLastName("Ivanova");
        nurseService.update(actualNurse);
        actualNurse = nurseService.findById(1).get();
        assertEquals(expectedNurse, actualNurse);
    }

    @Test
    void shouldDeleteNurse() {
        log.info("Execute nurseService test shouldDeleteNurse()");
        assertNotNull(nurseService);
        Nurse expectedNurse = nurseService.findById(1).get();
        Integer deletedId = nurseService.delete(expectedNurse.getId());
        Optional<Nurse> deletedNurse = nurseService.findById(deletedId);
        assertTrue(deletedNurse.isEmpty());
    }

}