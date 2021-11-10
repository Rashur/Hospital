package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class NurseDaoJDBCImpl implements NurseDao{

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NurseDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Nurse> findAll() {
        return null;
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
}
