package com.epam.hospital.dao;

import com.epam.hospital.NurseDao;
import com.epam.hospital.SpringJdbcConfig;
import com.epam.hospital.mapper.NurseRowMapper;
import com.epam.hospital.model.Nurse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJdbcTest
@Import({NurseDaoJDBCImpl.class, NurseRowMapper.class})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
@EnableAutoConfiguration
public class NurseDaoJDBCImplIT {

    private static final Logger log = LogManager.getLogger(NurseDaoJDBCImplIT.class);
    private final NurseDao nurseDaoJDBC;

    public NurseDaoJDBCImplIT(@Autowired final NurseDao nurseDao) {
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
