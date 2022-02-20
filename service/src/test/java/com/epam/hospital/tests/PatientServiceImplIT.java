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

    }

    @Test
    void shouldCreatePatient() {

    }

    @Test
    void shouldUpdatePatient() {

    }

    @Test
    void shouldDeletePatient() {

    }

    @Test
    void shouldFindPatientById() {

    }

    @Test
    public void shouldReturnEmptyWhenCallFindById() {

    }
}