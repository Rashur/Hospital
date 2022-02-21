package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NurseDao extends JpaRepository<Nurse, Integer> {

}
