package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NurseDaoJDBCImpl implements NurseDao{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SQL_ALL_NURSES = "SELECT * FROM NURSE";
    private static final String SQL_CREATE_NURSE = "INSERT INTO NURSE(first_name,last_name) values(:first_name,:last_name)";
    private static final String SQL_DELETE_NURSE = "DELETE FROM NURSE WHERE ID=:ID";

    public NurseDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Nurse> findAll() {
        return namedParameterJdbcTemplate.query(SQL_ALL_NURSES, new NurseRowMapper());
    }

    @Override
    public Integer create(Nurse nurse) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("first_name", nurse.getFirstName())
                .addValue("last_name", nurse.getLastName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_NURSE, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    @Override
    public Integer update(Nurse nurse) {
        return null;
    }

    @Override
    public Integer delete(Integer nurseId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("ID", nurseId);
        namedParameterJdbcTemplate.update(SQL_DELETE_NURSE, sqlParameterSource);
        return nurseId;
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
