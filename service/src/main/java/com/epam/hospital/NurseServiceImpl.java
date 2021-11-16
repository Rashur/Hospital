package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NurseServiceImpl implements NurseService {

    private final NurseDao nurseDao;

    public NurseServiceImpl(NurseDao nurseDao) {
        this.nurseDao = nurseDao;
    }

    @Override
    public List<Nurse> findAll() {
        return nurseDao.findAll();
    }
}
