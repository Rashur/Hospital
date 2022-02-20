package com.epam.hospital;

import com.epam.hospital.model.Nurse;

import java.util.List;
import java.util.Optional;

public interface NurseDao {
    List<Nurse> findAll();

    void create(Nurse nurse);

    void update(Nurse nurse);

    void delete(Nurse nurse);

    Optional<Nurse> findById(Integer nurseId);

}
