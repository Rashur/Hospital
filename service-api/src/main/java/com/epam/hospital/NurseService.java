package com.epam.hospital;

import com.epam.hospital.dto.NurseDto;
import com.epam.hospital.model.Nurse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NurseService {

    List<Nurse> findAll();

    NurseDto create(NurseDto nurseDto);

    Nurse update(Nurse nurse, Integer id);

    void delete(Integer id);

    Optional<Nurse> findById(Integer id);

    List<NurseDto> findNursesByPatientsDateRange(Date dateBefore, Date dateAfter);

    Page<NurseDto> findAllWithPagination(Integer offset, Integer pageSize);

    void createFakeNurse();
}
