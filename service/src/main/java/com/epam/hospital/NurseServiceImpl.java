package com.epam.hospital;

import com.epam.hospital.dto.NurseDto;
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
    public void create(NurseDto nurseDto) {
        log.info("IN NurseServiceImpl create() nurse: {}", nurseDto);
        Nurse nurse = nurseMapper.toEntity(nurseDto);
        nurseDao.create(nurse);
    }

    @Override
    public void update(NurseDto nurseDto) {
        Nurse nurse = nurseMapper.toEntity(nurseDto);
        log.info("IN NurseServiceImpl update() update nurse: {} with id: {}", nurseDto, nurseDto.getId());
        nurseDao.update(nurse);
    }

    @Override
    public void delete(NurseDto nurseDto) {
        log.info("IN NurseServiceImpl delete() delete nurse: {}", nurseDto);
        Nurse nurse = nurseMapper.toEntity(nurseDto);
        nurseDao.delete(nurse);
    }

    @Override
    public Optional<NurseDto> findById(Integer nurseId) {
        log.info("IN NurseServiceImpl findById() find nurse with id: {}", nurseId);
        NurseDto nurseDto = nurseMapper.toDto(nurseDao.findById(nurseId).get());
        return Optional.ofNullable(nurseDto);
    }
}
