package com.epam.hospital.dao;

import com.epam.hospital.NurseDao;
import com.epam.hospital.model.Nurse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class NurseDaoJDBCImpl implements NurseDao {

    private static final Logger log = LogManager.getLogger(NurseDaoJDBCImpl.class);

    @PersistenceContext
    private final EntityManager entityManager;

    private static final String SQL_ALL_NURSES = "FROM Nurse";
    private static final String SQL_FIND_NURSE_BY_ID = "FROM Nurse WHERE id=:id";

    @Autowired
    public NurseDaoJDBCImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Nurse> findAll() {
        log.info("IN NurseDaoJDBCImpl findAll()");
        TypedQuery<Nurse> typedQuery = entityManager.createQuery(SQL_ALL_NURSES, Nurse.class);
        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public void create(Nurse nurse) {
        entityManager.persist(nurse);
    }

    @Override
    @Transactional
    public void update(Nurse nurse) {
        entityManager.merge(nurse);
    }

    @Override
    @Transactional
    public void delete(Nurse nurse) {
        entityManager.remove(entityManager.contains(nurse) ? nurse : entityManager.merge(nurse));
    }

    @Override
    public Optional<Nurse> findById(Integer nurseId) {
        TypedQuery<Nurse> typedQuery = entityManager.createQuery(SQL_FIND_NURSE_BY_ID, Nurse.class);
        typedQuery.setParameter("id", nurseId);
        try {
            Nurse nurse = typedQuery.getSingleResult();
            return Optional.ofNullable(nurse);
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }
}
