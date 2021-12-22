package com.epam.hospital;

import com.epam.hospital.dao.NurseDaoJDBCImpl;
import com.epam.hospital.dao.PatientDaoJDBCImpl;
import com.epam.hospital.dao.PatientDtoDaoImpl;
import com.epam.hospital.mapper.NurseRowMapper;
import com.epam.hospital.mapper.PatientRowMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DaoTestConfig extends SpringJdbcConfig {

    @Bean
    PatientDtoDao patientDtoDao() {
        return new PatientDtoDaoImpl(namedParameterJdbcTemplate());
    }

    @Bean
    PatientRowMapper patientRowMapper() {
        return new PatientRowMapper();
    }

    @Bean
    NurseRowMapper nurseRowMapper() {
        return new NurseRowMapper();
    }

    @Bean
    PatientDao patientDao()  {
        return new PatientDaoJDBCImpl(namedParameterJdbcTemplate(), patientRowMapper());
    }

    @Bean
    NurseDao nurseDao() {
        return new NurseDaoJDBCImpl(namedParameterJdbcTemplate(), nurseRowMapper());
    }
}
