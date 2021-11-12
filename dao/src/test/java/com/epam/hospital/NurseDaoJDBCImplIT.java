package com.epam.hospital;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
@Transactional
public class NurseDaoJDBCImplIT {

    private NurseDaoJDBCImpl nurseDaoJDBC;


    public NurseDaoJDBCImplIT(@Autowired NurseDao nurseDao) {
        this.nurseDaoJDBC = (NurseDaoJDBCImpl) nurseDao;
    }

    @Test
    void findAll() {
        assertNotNull(nurseDaoJDBC);
        assertNotNull(nurseDaoJDBC.findAll());
    }
}
