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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Index;
import javax.persistence.ValidationMode;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

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
    public List<Nurse> allNurses() {
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
    public Nurse findNurseById(@PathVariable Integer id) {
        log.info("IN NurseController findNurseById() find nurse with id: {}", nurseService.findById(id).get());
        return nurseService.findById(id).get();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/nurses/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Updating nurse")
    public Nurse updateNurse(@RequestBody Nurse nurse,
                            @PathVariable Integer id) {
        log.info("IN NurseController updateNurse() update nurse: {}", nurse);
        return nurseService.update(nurse, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/nurses/{id}", produces = {"application/json"})
    @Operation(summary = "Deleting nurse by id")
    public void deleteNurse(@PathVariable Integer id) {
        log.info("IN NurseController deleteNurse() delete nurse with id: {}", id);
        nurseService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/nurses/date-range", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Getting nurse if their patients has an illness date between this date range")
    public List<NurseDto> nurseByPatientDateRange(@RequestBody DateRange dateRange) {
        log.info("IN NurseController nurseByPatientDateRange() with date range: {}, {}", dateRange.getDateFrom(), dateRange.getDateTo());
        return nurseService.findNursesByPatientsDateRange(dateRange.getDateFrom(), dateRange.getDateTo());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/nurses/{offset}/{pageSize}")
    @Operation(summary = "Get all nurses with pagination")
    public Page<NurseDto> nurseByFirstNameAndPageable(@PathVariable Integer offset,
                                                      @PathVariable Integer pageSize) {
        log.info("IN NurseController nurseByFirstNameAndPageable() find nurse per page");
        return nurseService.findAllWithPagination(offset, pageSize);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "nurses/fake")
    @Operation(summary = "Creating fake nurse")
    public void createFakeNurse() {
        log.info("IN NurseController createFakeNurse() create fake nurse");
        nurseService.createFakeNurse();
    }
}
