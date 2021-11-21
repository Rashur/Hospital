package com.epam.hospital.dao;

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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
@Transactional
class PatientDaoJDBCImplIT {

    private static final Logger log = LogManager.getLogger(PatientDaoJDBCImplIT.class);
    private final PatientDaoJDBCImpl patientDaoJDBC;

    @Autowired
    PatientDaoJDBCImplIT(final PatientDaoJDBCImpl patientDaoJDBC) {
        this.patientDaoJDBC = patientDaoJDBC;
    }

    @Test
    void shouldReturnAllPatients() {
        log.info("Execute test IN PatientDaoJDBCImplIT findAll()");
        assertNotNull(patientDaoJDBC);
        List<Patient> allPatients = patientDaoJDBC.findAll();
        assertNotNull(allPatients);
        assertEquals(4, allPatients.size());
    }

    @Test
    void create() {
        log.info("Execute test PatientDaoJDBCImplIT create()");
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
    void update() {
        log.info("Execute test PatientDaoJDBCImplIT update()");
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
    void delete() {
        assertNotNull(patientDaoJDBC);
        Patient expectedPatient = patientDaoJDBC.findById(1).get();
        Integer deletedId = patientDaoJDBC.delete(expectedPatient.getId());
        Optional<Patient> deletedPatient = patientDaoJDBC.findById(deletedId);
        assertTrue(deletedPatient.isEmpty());
    }

    @Test
    void findById() {
        log.info("Execute test PatientDaoJDBCImplIT findById()");
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