package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NurseDao extends MongoRepository<Nurse, Integer> {

    Nurse findById(String id);

    void deleteById(String id);
}
