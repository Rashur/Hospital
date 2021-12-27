package com.epam.hospital.dao;

import com.epam.hospital.SpringJdbcConfig;
import com.epam.hospital.mapper.PatientRowMapper;
import com.epam.hospital.model.Patient;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import({PatientDaoJDBCImpl.class, PatientRowMapper.class})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
@EnableAutoConfiguration
class PatientDaoJDBCImplIT {

    private static final Logger log = LogManager.getLogger(PatientDaoJDBCImplIT.class);
    private final PatientDaoJDBCImpl patientDaoJDBC;

    PatientDaoJDBCImplIT(@Autowired final PatientDaoJDBCImpl patientDaoJDBC) {
        this.patientDaoJDBC = patientDaoJDBC;
    }

    @Test
    void shouldReturnAllPatients() {
        log.info("Execute test IN PatientDaoJDBCImplIT shouldReturnAllPatients()");
        assertNotNull(patientDaoJDBC);
        List<Patient> allPatients = patientDaoJDBC.findAll();
        assertNotNull(allPatients);
        assertEquals(4, allPatients.size());
    }

    @Test
    void shouldCreatePatient() {
        log.info("Execute test PatientDaoJDBCImplIT shouldCreatePatient()");
        assertNotNull(patientDaoJDBC);
        Patient expectedPatient = new Patient(null, "Denis", "Beresten", "Skalioz", LocalDate.now(), 1);
        Integer patientId = patientDaoJDBC.create(expectedPatient);
        assertNotNull(patientId);
        expectedPatient.setId(patientId);
        Optional<Patient> actualPatient = patientDaoJDBC.findById(patientId);
        assertTrue(actualPatient.isPresent());
        assertEquals(expectedPatient, actualPatient.get());
    }

    @Test
    void shouldUpdatePatient() {
        log.info("Execute test PatientDaoJDBCImplIT shouldUpdatePatient()");
        assertNotNull(patientDaoJDBC);
        Patient expectedPatient = patientDaoJDBC.findById(1).get();
        Patient actualPatient = new Patient(1 , "Egor", "Krechko", "Skalioz", LocalDate.now(), 1);
        expectedPatient.setFirstName("Egor");
        expectedPatient.setLastName("Krechko");
        expectedPatient.setDiagnosis("Skalioz");
        expectedPatient.setIllnessDate(LocalDate.of(2021, 10, 1));
        expectedPatient.setNursesId(1);
        patientDaoJDBC.update(actualPatient);
        actualPatient = patientDaoJDBC.findById(1).get();
        assertEquals(expectedPatient, actualPatient);
    }

    @Test
    void shouldDeletePatient() {
        log.info("Execute test PatientDaoJDBCImplIT shouldDeletePatient()");
        assertNotNull(patientDaoJDBC);
        Patient expectedPatient = patientDaoJDBC.findById(1).get();
        Integer deletedId = patientDaoJDBC.delete(expectedPatient.getId());
        Optional<Patient> deletedPatient = patientDaoJDBC.findById(deletedId);
        assertTrue(deletedPatient.isEmpty());
    }

    @Test
    void shouldFindPatientById() {
        log.info("Execute test PatientDaoJDBCImplIT shouldFindPatientById()");
        assertNotNull(patientDaoJDBC);
        Patient expectedPatient = new Patient(1, "Dzianis", "Berastsen", "Skalioz", LocalDate.of(2021, 10, 1), 2 );
        Optional<Patient> actualPatient = patientDaoJDBC.findById(1);
        assertTrue(actualPatient.isPresent());
        assertEquals(expectedPatient, actualPatient.get());
    }

    @Test
    public void shouldReturnEmptyWhenCallFindById() {
        log.info("Execute test PatientDaoJDBCImplIT shouldReturnEmptyWhenCallFindById()");
        assertNotNull(patientDaoJDBC);
        Optional<Patient> actualPatient = patientDaoJDBC.findById(123123);
        assertTrue(actualPatient.isEmpty());
    }
}