package com.epam.hospital.tests;

import com.epam.hospital.PatientService;
import com.epam.hospital.PatientServiceImpl;
import com.epam.hospital.ServiceTestConfig;
import com.epam.hospital.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfig.class})
@Transactional
class PatientServiceImplIT {

    private static final Logger log = LogManager.getLogger(PatientServiceImplIT.class);

    private final PatientService patientService;

    @BeforeEach
    private void setUp() {

    }

    @Autowired
    PatientServiceImplIT(final PatientService patientService) {
        this.patientService = patientService;
    }

    @Test
    void shouldReturnAllPatients() {
        log.info("Execute test IN PatientDaoJDBCImplIT shouldReturnAllPatients()");
        assertNotNull(patientService);
        List<Patient> allPatients = patientService.findAll();
        assertNotNull(allPatients);
        assertEquals(4, allPatients.size());
    }

    @Test
    void shouldCreatePatient() {
        log.info("Execute test PatientDaoJDBCImplIT shouldCreatePatient()");
        assertNotNull(patientService);
        Patient expectedPatient = new Patient(null, "Denis", "Beresten", "Skalioz", LocalDate.now(), 1);
        Integer patientId = patientService.create(expectedPatient);
        assertNotNull(patientId);
        expectedPatient.setId(patientId);
        Optional<Patient> actualPatient = patientService.findById(patientId);
        assertTrue(actualPatient.isPresent());
        assertEquals(expectedPatient, actualPatient.get());
    }

    @Test
    void shouldUpdatePatient() {
        log.info("Execute test PatientDaoJDBCImplIT shouldUpdatePatient()");
        assertNotNull(patientService);
        Patient expectedPatient = patientService.findById(1).get();
        Patient actualPatient = new Patient(1 , "Egor", "Krechko", "Skalioz", LocalDate.now(), 1);
        expectedPatient.setFirstName("Egor");
        expectedPatient.setLastName("Krechko");
        expectedPatient.setDiagnosis("Skalioz");
        expectedPatient.setIllnessDate(LocalDate.of(2021, 10, 1));
        expectedPatient.setNursesId(1);
        patientService.update(actualPatient);
        actualPatient = patientService.findById(1).get();
        assertEquals(expectedPatient, actualPatient);
    }

    @Test
    void shouldDeletePatient() {
        log.info("Execute test PatientDaoJDBCImplIT shouldDeletePatient()");
        assertNotNull(patientService);
        Patient expectedPatient = patientService.findById(1).get();
        Integer deletedId = patientService.delete(expectedPatient.getId());
        Optional<Patient> deletedPatient = patientService.findById(deletedId);
        assertTrue(deletedPatient.isEmpty());
    }

    @Test
    void shouldFindPatientById() {
        log.info("Execute test PatientDaoJDBCImplIT shouldFindPatientById()");
        assertNotNull(patientService);
        Patient expectedPatient = new Patient(1, "Dzianis", "Berastsen", "Skalioz", LocalDate.of(2021, 10, 1), 2);
        Optional<Patient> actualPatient = patientService.findById(1);
        assertTrue(actualPatient.isPresent());
        assertEquals(expectedPatient, actualPatient.get());
    }

    @Test
    public void shouldReturnEmptyWhenCallFindById() {
        log.info("Execute test PatientDaoJDBCImplIT shouldReturnEmptyWhenCallFindById()");
        assertNotNull(patientService);
        Optional<Patient> actualPatient = patientService.findById(123123);
        assertTrue(actualPatient.isEmpty());
    }
}