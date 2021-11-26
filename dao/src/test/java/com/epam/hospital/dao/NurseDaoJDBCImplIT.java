package com.epam.hospital.dao;

import com.epam.hospital.NurseDao;
import com.epam.hospital.model.Nurse;
import com.epam.hospital.model.Patient;
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
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
@Transactional
public class NurseDaoJDBCImplIT {

    private static final Logger log = LogManager.getLogger(NurseDaoJDBCImplIT.class);
    private final NurseDao nurseDaoJDBC;

    @Autowired
    public NurseDaoJDBCImplIT(final NurseDao nurseDao) {
        this.nurseDaoJDBC = nurseDao;
    }

    @Test
    void shouldReturnAllNurses() {
        log.info("Execute nurseDao test findAll()");
        assertNotNull(nurseDaoJDBC);
        List<Nurse> allNurses = nurseDaoJDBC.findAll();
        assertNotNull(allNurses);
        assertEquals(3, allNurses.size());
    }

    @Test
    void shouldCreateNurseSuccessfully() {
        log.info("Execute nurseDao test create()");
        assertNotNull(nurseDaoJDBC);
        Nurse expectedNurse = new Nurse(null,"Tatsiana", "Levchuk");
        Integer nurseId = nurseDaoJDBC.create(expectedNurse);
        assertNotNull(nurseId);
        expectedNurse.setId(nurseId);
        Optional<Nurse> actualNurse = nurseDaoJDBC.findById(nurseId);
        assertTrue(actualNurse.isPresent());
        assertEquals(expectedNurse, actualNurse.get());
    }

    @Test
    void shouldFindNurseById() {
        log.info("Execute nurseDao test findById()");
        assertNotNull(nurseDaoJDBC);
        Nurse expectedNurse = new Nurse(1, "Eigenia", "Liashuk");
        Optional<Nurse> actualNurse = nurseDaoJDBC.findById(1);
        assertTrue(actualNurse.isPresent());
        assertEquals(expectedNurse, actualNurse.get());
    }

    @Test
    void shouldUpdateNurse() {
        log.info("Execute nurseDao test update()");
        assertNotNull(nurseDaoJDBC);
        Nurse expectedNurse = nurseDaoJDBC.findById(1).get();
        Nurse actualNurse = new Nurse(1,"Ekaterina", "Ivanova");
        expectedNurse.setFirstName("Ekaterina");
        expectedNurse.setLastName("Ivanova");
        nurseDaoJDBC.update(actualNurse);
        actualNurse = nurseDaoJDBC.findById(1).get();
        assertEquals(expectedNurse, actualNurse);
    }

    @Test
    void shouldDeleteNurse() {
        assertNotNull(nurseDaoJDBC);
        Nurse expectedNurse = nurseDaoJDBC.findById(1).get();
        Integer deletedId = nurseDaoJDBC.delete(expectedNurse.getId());
        Optional<Nurse> deletedNurse = nurseDaoJDBC.findById(deletedId);
        assertTrue(deletedNurse.isEmpty());
    }

}
