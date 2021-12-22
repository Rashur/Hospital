package com.epam.hospital.dao;

import com.epam.hospital.SpringJdbcConfig;
import com.epam.hospital.dto.DateRange;
import com.epam.hospital.dto.PatientDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import({PatientDtoDaoImpl.class})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
class PatientDtoDaoImplIT {

    private static final Logger log = LogManager.getLogger(PatientDaoJDBCImplIT.class);
    private final PatientDtoDaoImpl patientDtoDao;

    PatientDtoDaoImplIT(@Autowired final PatientDtoDaoImpl patientDtoDao) {
        this.patientDtoDao = patientDtoDao;
    }

    @Test
    void shouldFindAllWithNurseName() {
        log.info("Execute test IN PatientDtoDaoImplIT shouldFindAllWithNurseName()");
        assertNotNull(patientDtoDao);
        List<PatientDto> patientDtoList = patientDtoDao.findAllWithNurseName();
        assertNotNull(patientDtoList);
        assertEquals(4, patientDtoList.size());
    }

    @Test
    void shouldFindAllPatientsInRange() throws ParseException {
        log.info("Execute test IN PatientDtoDaoImplIT shouldFindAllPatientsInRange()");
        assertNotNull(patientDtoDao);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stringDateFrom = "2021-09-01";
        String stringDateTo = "2022-01-01";
        DateRange dateRange = new DateRange();
        Date dateFrom = simpleDateFormat.parse(stringDateFrom);
        Date dateTo = simpleDateFormat.parse(stringDateTo);
        dateRange.setDateFrom(dateFrom);
        dateRange.setDateTo(dateTo);
        List<PatientDto> rangeList = patientDtoDao.findInRange(dateRange);
        assertNotNull(rangeList);
    }
}