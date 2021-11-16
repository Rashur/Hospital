package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NurseDaoJDBCImpl implements NurseDao{

    private static final Logger log = LogManager.getLogger(NurseDaoJDBCImpl.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SQL_ALL_NURSES = "SELECT * FROM NURSE";
    private static final String SQL_CREATE_NURSE = "INSERT INTO NURSE(first_name,last_name) values(:firstName,:lastName)";
    private static final String SQL_UPDATE_NURSE = "UPDATE NURSE SET first_name = :firstName, last_name = :lastName where id = :id";
    private static final String SQL_DELETE_NURSE = "DELETE FROM NURSE WHERE id=:id";
    private static final String SQL_FIND_NURSE_BY_ID = "SELECT * FROM NURSE WHERE id=:id";

    public NurseDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Nurse> findAll() {
        log.info("IN findAll()");
        return namedParameterJdbcTemplate.query(SQL_ALL_NURSES, new NurseRowMapper());
    }

    @Override
    public Integer create(Nurse nurse) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("firstName", nurse.getFirstName())
                .addValue("lastName", nurse.getLastName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_NURSE, sqlParameterSource, keyHolder);
        log.info("IN create() create nurse:{}", nurse);
        return (Integer) keyHolder.getKey();
    }

    @Override
    public Integer update(Nurse nurse) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("firstName", nurse.getFirstName())
                .addValue("lastName", nurse.getLastName())
                .addValue("id", nurse.getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_UPDATE_NURSE, sqlParameterSource, keyHolder);
        log.info("IN update() update nurse: {} with id: {}", nurse, nurse.getId());
        return (Integer) keyHolder.getKey();
    }

    @Override
    public Integer delete(Integer nurseId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", nurseId);
        namedParameterJdbcTemplate.update(SQL_DELETE_NURSE, sqlParameterSource);
        //TODO realize patientDao and delete with join
        log.info("IN delete() delete nurse with id: {}", nurseId);
        return nurseId;
    }

    @Override
    public Nurse findById(Integer nurseId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", nurseId);
        Nurse nurse = namedParameterJdbcTemplate.queryForObject(SQL_FIND_NURSE_BY_ID, sqlParameterSource, new NurseRowMapper());
        log.info("IN findById() find nurse {} with id: {}",nurse, nurseId);
        return nurse;
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
