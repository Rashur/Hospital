package com.epam.hospital;

import com.epam.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientDao extends JpaRepository<Patient, Integer> {

    @Query(value = "select p from Patient as p " +
            "join p.nurseList as nl " +
            "group by p.id having count(nl) > :size ")
    List<Patient> findAllByNurseListCountGreaterThan(@Param("size") Long size);
}
