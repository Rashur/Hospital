package com.epam.hospital;

import com.epam.hospital.dto.NurseDto;
import com.epam.hospital.exception.NurseNotFoundException;
import com.epam.hospital.mapper.NurseMapper;
import com.epam.hospital.model.Nurse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NurseServiceImpl implements NurseService {

    private static final Logger log = LogManager.getLogger(NurseServiceImpl.class);
    private final NurseDao nurseDao;
    private final NurseMapper nurseMapper;

    @Autowired
    public NurseServiceImpl(final NurseDao nurseDao, NurseMapper nurseMapper) {
        this.nurseDao = nurseDao;
        this.nurseMapper = nurseMapper;
    }

    @Override
    public List<NurseDto> findAll() {
        log.info("IN NurseServiceImpl findAll()");
        List<NurseDto> nurseDtoList = new ArrayList<>();
        for (Nurse nurse : nurseDao.findAll()) {
            nurseDtoList.add(nurseMapper.toDto(nurse));
        }
        return nurseDtoList;
    }

    @Override
    public NurseDto create(NurseDto nurseDto) {
        Nurse nurse = nurseMapper.toEntity(nurseDto);
        nurseDao.save(nurse);
        log.info("IN NurseServiceImpl create() nurse: {}", nurseDto);
        return nurseDto;
    }

    @Override
    public NurseDto update(NurseDto nurseDto, Integer id) {
        if (nurseDao.findById(id).isEmpty()) {
            throw new NurseNotFoundException("Nurse with id:" + id + " doesn't exist");
        }
        nurseDto.setId(id);
        Nurse nurse = nurseMapper.toEntity(nurseDto);
        nurseDao.save(nurse);
        log.info("IN NurseServiceImpl update() update nurse: {} with id: {}", nurseDto, id);
        return nurseDto;
    }

    @Override
    public void delete(Integer id) {
        if (nurseDao.findById(id).isEmpty()) {
            throw new NurseNotFoundException("Nurse with id:" + id + " doesn't exist");
        }
        log.info("IN NurseServiceImpl delete() delete nurse with id: {}", id);
        nurseDao.deleteById(id);
    }

    @Override
    public Optional<NurseDto> findById(Integer id) {
        Optional<Nurse> searchedNurse = nurseDao.findById(id);
        if (searchedNurse.isPresent()) {
            NurseDto nurseDto = nurseMapper.toDto(searchedNurse.get());
            log.info("IN NurseServiceImpl findById() find nurse with id: {}", id);
            return Optional.ofNullable(nurseDto);
        } else {
            throw new NurseNotFoundException("Nurse with id:" + id + " doesn't exist");
        }
    }
}
