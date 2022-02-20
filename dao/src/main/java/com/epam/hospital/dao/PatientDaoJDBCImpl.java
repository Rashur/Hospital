package com.epam.hospital.dao;

import com.epam.hospital.PatientDao;
import com.epam.hospital.model.Nurse;
import com.epam.hospital.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PatientDaoJDBCImpl implements PatientDao {

    private static final Logger log = LogManager.getLogger(PatientDaoJDBCImpl.class);

    @PersistenceContext
    private final EntityManager entityManager;

    private static final String SQL_ALL_PATIENTS = "FROM Patient";
    private static final String SQL_FIND_PATIENT_BY_ID = "FROM Patient WHERE id=:id";

    @Autowired
    public PatientDaoJDBCImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Patient> findAll() {
        log.info("IN PatientDaoJDBCImpl findAll()");
        TypedQuery<Patient> typedQuery = entityManager.createQuery(SQL_ALL_PATIENTS, Patient.class);
        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public void create(Patient patient) {
        log.info("patient: {}", patient.getNurseList().stream().map(Nurse::getFirstName).collect(Collectors.toList()));
        entityManager.persist(patient);
    }

    @Override
    @Transactional
    public void update(Patient patient) {
        Patient patientBeforeUpdate = entityManager.find(Patient.class, patient.getId());
        patient.setIllnessDate(patientBeforeUpdate.getIllnessDate());
        entityManager.merge(patient);
    }

    @Override
    @Transactional
    public void delete(Patient patient) {
        entityManager.remove(entityManager.contains(patient) ? patient : entityManager.merge(patient));
    }

    @Override
    public Optional<Patient> findById(Integer patientId) {
        TypedQuery<Patient> typedQuery = entityManager.createQuery(SQL_FIND_PATIENT_BY_ID, Patient.class);
        typedQuery.setParameter("id", patientId);
        try {
            Patient patient = typedQuery.getSingleResult();
            return Optional.ofNullable(patient);
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }
}
