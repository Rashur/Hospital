package com.epam.hospital;

import com.epam.hospital.dto.DateRange;
import com.epam.hospital.dto.PatientDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PatientDtoServiceImpl implements PatientDtoService{

    private static final Logger log = LogManager.getLogger(PatientServiceImpl.class);
    private final PatientDtoDao patientDtoDao;

    public PatientDtoServiceImpl(final PatientDtoDao patientDtoDao) {
        this.patientDtoDao = patientDtoDao;
    }

    @Override
    public List<PatientDto> findAllPatientsWithNurseName() {
        log.info("IN PatientDtoServiceImpl findAllPatientsWithNurseName() find all patients with nurse name");
        return patientDtoDao.findAllWithNurseName();
    }

    @Override
    public List<PatientDto> findAllPatientsInRange(DateRange dateRange) {
        log.info("IN PatientDtoServiceImpl findAllPatientsInRange() find all patients between: {}, {}",
                dateRange.getDateFrom(),
                dateRange.getDateTo());
        return patientDtoDao.findInRange(dateRange);
    }
}
