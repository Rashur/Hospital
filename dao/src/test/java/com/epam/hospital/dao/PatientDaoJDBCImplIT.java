package com.epam.hospital.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@DataJdbcTest
@Import({PatientDaoJDBCImpl.class})
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