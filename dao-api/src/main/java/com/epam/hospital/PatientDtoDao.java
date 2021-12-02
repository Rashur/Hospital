package com.epam.hospital;

import com.epam.hospital.dto.DateRange;
import com.epam.hospital.dto.PatientDto;

import java.util.List;

public interface PatientDtoDao {

    List<PatientDto> findAllWithNurseName();

    List<PatientDto> findInRange(DateRange dateRange);
}
