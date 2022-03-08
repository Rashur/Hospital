package com.epam.hospital.rest;

import com.epam.hospital.NurseService;
import com.epam.hospital.dto.DateRange;
import com.epam.hospital.dto.NurseDto;
import com.epam.hospital.model.Nurse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@Tag(name = "Nurse Controller",
        description = "Interaction with nurse")
public class NurseController {

    private static final Logger log = LogManager.getLogger(NurseController.class);

    private final NurseService nurseService;

    @Autowired
    public NurseController(final NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/nurses")
    @Operation(summary = "Get all nurses")
    public List<NurseDto> allNurses() {
        log.info("IN NurseController allNurses() find all nurses");
        return nurseService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/nurses", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Creating new nurse")
    public NurseDto createNurse(@RequestBody NurseDto nurseDto) {
        log.info("IN NurseController createNurse() create nurse: {}", nurseDto);
        return nurseService.create(nurseDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/nurses/{id}")
    @Operation(summary = "Getting nurse by id")
    public NurseDto findNurseById(@PathVariable String id) {
        log.info("IN NurseController findNurseById() find nurse with id: {}", id);
        return nurseService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/nurses/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Updating nurse")
    public NurseDto updateNurse(@RequestBody NurseDto nurseDto,
                            @PathVariable String id) {
        log.info("IN NurseController updateNurse() update nurse: {}", nurseDto);
        return nurseService.update(nurseDto, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/nurses/{id}", produces = {"application/json"})
    @Operation(summary = "Deleting nurse by id")
    public void deleteNurse(@PathVariable String id) {
        log.info("IN NurseController deleteNurse() delete nurse with id: {}", id);
        nurseService.delete(id);
    }

}
