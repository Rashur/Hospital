package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger log = LogManager.getLogger(NurseDaoJDBCImplIT.class);
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
        log.info("Execute nurseDao test create()");
        assertNotNull(nurseDaoJDBC);
        int nursesSizeBefore = nurseDaoJDBC.findAll().size();
        Nurse nurse = new Nurse(5,"Tatsiana", "Levchuk");
        Integer nurseId = nurseDaoJDBC.create(nurse);
        assertNotNull(nurseId);
        assertEquals(nursesSizeBefore, nurseDaoJDBC.findAll().size()-1);
    }

    @Test
    void findById() {
        log.info("Execute nurseDao test findById()");
        assertNotNull(nurseDaoJDBC);
        assertNotNull(nurseDaoJDBC.findById(1));
    }

    @Test
    void update() {
        assertNotNull(nurseDaoJDBC);
        Nurse foundNurse = nurseDaoJDBC.findById(1);
        Nurse nurse = new Nurse(1,"Ekaterina", "Ivanova");
        nurseDaoJDBC.update(nurse);
        Nurse updatedNurse = nurseDaoJDBC.findById(1);
        assertNotEquals(foundNurse, updatedNurse);
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
