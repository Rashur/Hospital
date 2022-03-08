package com.epam.hospital;

import com.epam.hospital.dto.NurseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NurseService {

    List<NurseDto> findAll();

    NurseDto create(NurseDto nurseDto);

    NurseDto update(NurseDto nurse, String id);

    void delete(String id);

    NurseDto findById(String id);

}
