package com.epam.hospital.dao;

import com.epam.hospital.PatientDtoDao;
import com.epam.hospital.dto.DateRange;
import com.epam.hospital.dto.PatientDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

public class PatientDtoDaoImpl implements PatientDtoDao {

    private static final Logger log = LogManager.getLogger(PatientDaoJDBCImpl.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PatientDtoDaoImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String SQL_FIND_ALL_WITH_NURSE_NAME = "SELECT patient.id, patient.first_name, patient.last_name, " +
                "patient.diagnosis, patient.illness_date, nurse.first_name AS nurseFirstName, nurse.last_name AS nurseLastName FROM patient " +
            "LEFT JOIN nurse ON patient.nurse_id = nurse.id";
    private static final String SQL_FIND_ALL_IN_DATE_RANGE = "SELECT patient.id, patient.first_name, patient.last_name, " +
            "patient.diagnosis, patient.illness_date, nurse.first_name AS nurseFirstName, nurse.last_name AS nurseLastName FROM patient " +
            "LEFT JOIN nurse ON patient.nurse_id = nurse.id WHERE patient.illness_date BETWEEN :dateFrom AND :dateTo";

    @Override
    public List<PatientDto> findAllWithNurseName() {
        log.info("IN PatientDtoDaoImpl findAllWithNurseName() find all nurse with nurse firstName and lastName");
        return namedParameterJdbcTemplate.query(SQL_FIND_ALL_WITH_NURSE_NAME, BeanPropertyRowMapper.newInstance(PatientDto.class));
    }

    @Override
    public List<PatientDto> findInRange(DateRange dateRange) {
        log.info("IN PatientDtoDaoImpl findInRange() find all patients between: {}, {}", dateRange.getDateFrom(), dateRange.getDateTo());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("dateFrom", dateRange.getDateFrom())
                .addValue("dateTo", dateRange.getDateTo());
        return namedParameterJdbcTemplate.query(SQL_FIND_ALL_IN_DATE_RANGE, sqlParameterSource, BeanPropertyRowMapper.newInstance(PatientDto.class));
    }
}
