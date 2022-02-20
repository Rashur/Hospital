package com.epam.hospital;

import com.epam.hospital.dto.NurseDto;
import com.epam.hospital.model.Nurse;

import java.util.List;
import java.util.Optional;

public interface NurseService {

    List<NurseDto> findAll();

    void create(NurseDto nurseDto);

    void update(NurseDto nurse);

    void delete(NurseDto nurseDto);

    Optional<NurseDto> findById(Integer nurseId);
}
