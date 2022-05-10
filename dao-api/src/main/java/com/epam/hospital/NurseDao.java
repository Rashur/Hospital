package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface NurseDao extends JpaRepository<Nurse, Integer> {

    @Query(value = "select * from nurse as n " +
            "join nurse_patient np on n.id = np.nurse_id " +
            "join patient p on p.id = np.patient_id " +
            "where p.illness_date between :dateBefore and :dateAfter",
    nativeQuery = true)
    List<Nurse> findNursesByPatientsIllnessDateBetween(@Param("dateBefore") Date dateBefore,
                                                       @Param("dateAfter") Date dateAfter);

}
