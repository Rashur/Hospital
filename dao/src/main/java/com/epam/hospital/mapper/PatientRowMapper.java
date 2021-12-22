package com.epam.hospital.mapper;

import com.epam.hospital.model.Patient;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PatientRowMapper implements RowMapper<Patient> {

    @Override
    public Patient mapRow(ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getInt("id"));
        patient.setFirstName(resultSet.getString("first_name"));
        patient.setLastName(resultSet.getString("last_name"));
        patient.setDiagnosis(resultSet.getString("diagnosis"));
        patient.setIllnessDate(resultSet.getDate("illness_date").toLocalDate());
        patient.setNursesId(resultSet.getInt("nurse_id"));
        return patient;
    }
}
