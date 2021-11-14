package com.epam.hospital;

import com.epam.hospital.model.Nurse;
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

    @Test
    void create() {
        assertNotNull(nurseDaoJDBC);
        int nursesSizeBefore = nurseDaoJDBC.findAll().size();
        Nurse nurse = new Nurse("Tatsiana", "Levchuk");
        Integer nurseId = nurseDaoJDBC.create(nurse);
        assertNotNull(nurseId);
        assertEquals(nursesSizeBefore, nurseDaoJDBC.findAll().size()-1);
    }

    /*@Test
    void delete() {
        assertNotNull(nurseDaoJDBC);
        int nurseSizeBefore = nurseDaoJDBC.findAll().size();
        Integer nurseId = nurseDaoJDBC.delete(1);
        assertNotNull(nurseId);
        assertEquals(nurseSizeBefore, nurseDaoJDBC.findAll().size()+1);
    }

     */


}
