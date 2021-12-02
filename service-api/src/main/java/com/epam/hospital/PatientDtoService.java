package com.epam.hospital;

import com.epam.hospital.dto.DateRange;
import com.epam.hospital.dto.PatientDto;

import java.util.List;

public interface PatientDtoService {

    List<PatientDto> findAllPatientsWithNurseName();

    List<PatientDto> findAllPatientsInRange(DateRange dateRange);
}
