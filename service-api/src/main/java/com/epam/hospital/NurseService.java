package com.epam.hospital;

import com.epam.hospital.model.Nurse;

import java.util.List;

public interface NurseService {

    List<Nurse> findAll();

    Integer create(Nurse nurse);

    Integer update(Nurse nurse);

    Integer delete(Integer nurseId);

    Nurse findById(Integer nurseId);
}
