package com.epam.hospital.tests;

import com.epam.hospital.NurseService;
import com.epam.hospital.NurseServiceImpl;
import com.epam.hospital.ServiceTestConfig;
import com.epam.hospital.model.Nurse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfig.class})
@Transactional
class NurseServiceImplIT {

    private static final Logger log = LogManager.getLogger(NurseServiceImplIT.class);
    private final NurseService nurseService;

    public NurseServiceImplIT(@Autowired final NurseService nurseService) {
        this.nurseService = nurseService;
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