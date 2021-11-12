package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NurseDaoJDBCImpl implements NurseDao{

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_ALL_NURSES = "SELECT * FROM NURSE";

    public NurseDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Nurse> findAll() {
        return namedParameterJdbcTemplate.query(SQL_ALL_NURSES, new NurseRowMapper());
    }

    @Override
    public Integer create(Nurse nurse) {
        return null;
    }

    @Override
    public Integer update(Nurse nurse) {
        return null;
    }

    @Override
    public Integer delete(Integer nurseId) {
        return null;
    }

    private class NurseRowMapper implements RowMapper<Nurse> {

        @Override
        public Nurse mapRow(ResultSet resultSet, int i) throws SQLException {
            Nurse nurse = new Nurse();
            nurse.setId(resultSet.getInt("id"));
            nurse.setFirstName(resultSet.getString("first_name"));
            nurse.setLastName(resultSet.getString("last_name"));
            return nurse;
        }
    }
}
