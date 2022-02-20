package com.epam.hospital.dao;

import com.epam.hospital.NurseDao;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJdbcTest
@Import({NurseDaoJDBCImpl.class})
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

    }

    @Test
    void shouldCreateNurseSuccessfully() {

    }

    @Test
    void shouldFindNurseById() {

    }

    @Test
    void shouldUpdateNurse() {

    }

    @Test
    void shouldDeleteNurse() {

    }

}
