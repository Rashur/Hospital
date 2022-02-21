package com.epam.hospital.rest;

import com.epam.hospital.NurseService;
import com.epam.hospital.dto.NurseDto;
import com.epam.hospital.model.Nurse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class NurseController {

    private static final Logger log = LogManager.getLogger(NurseController.class);

    private final NurseService nurseService;

    @Autowired
    public NurseController(final NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @GetMapping(value = "/nurses")
    public List<NurseDto> allNurses() {
        log.info("IN NurseController allNurses() find all nurses");
        return nurseService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/nurses", consumes = "application/json", produces = "application/json")
    public void createNurse(@RequestBody NurseDto nurseDto) {
        log.info("IN NurseController createNurse() create nurse: {}", nurseDto);
        nurseService.create(nurseDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/nurses/{id}")
    public NurseDto findNurseById(@PathVariable Integer id) {
        log.info("IN NurseController findNurseById() find nurse with id: {}", id);
        return nurseService.findById(id).get();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/nurses/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public void updateNurse(@RequestBody NurseDto nurseDto,
                            @PathVariable Integer id) {
        log.info("IN NurseController updateNurse() update nurse: {}", nurseDto);
        nurseService.update(nurseDto, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/nurses/{id}", produces = {"application/json"})
    public void deleteNurse(@PathVariable Integer id) {
        log.info("IN NurseController deleteNurse() delete nurse with id: {}", id);
        nurseService.delete(id);
    }
}
