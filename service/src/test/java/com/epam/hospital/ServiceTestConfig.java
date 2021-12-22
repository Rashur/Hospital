package com.epam.hospital;

import com.epam.hospital.dao.NurseDaoJDBCImpl;
import com.epam.hospital.dao.PatientDaoJDBCImpl;
import com.epam.hospital.dao.PatientDtoDaoImpl;
import com.epam.hospital.mapper.NurseRowMapper;
import com.epam.hospital.mapper.PatientRowMapper;
import org.springframework.context.annotation.Bean;

public class ServiceTestConfig extends SpringJdbcConfig {

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

    @Bean
    PatientService patientService() {
        return new PatientServiceImpl(patientDao());
    }

    @Bean
    NurseService nurseService() {
        return new NurseServiceImpl(nurseDao());
    }

    @Bean
    PatientDtoService patientDtoService() {
        return new PatientDtoServiceImpl(patientDtoDao());
    }
}
