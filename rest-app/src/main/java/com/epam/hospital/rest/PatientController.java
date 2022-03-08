package com.epam.hospital.rest;

import com.epam.hospital.PatientService;
import com.epam.hospital.dto.PatientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Tag(name = "Patients Controller",
        description = "Interaction with patients")
public class PatientController {

    private static final Logger log = LogManager.getLogger(PatientController.class);

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/patients")
    @Operation(summary = "Getting all patients")
    public List<PatientDto> allPatients() {
        log.info("IN PatientController allPatients() find all patients");
        return patientService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/patients", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Creating new patient")
    public PatientDto createPatient(@RequestBody PatientDto patientDto) {
        log.info("IN PatientController createPatient() create patient: {}", patientDto);
        return patientService.create(patientDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/patients/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Updating patient")
    public PatientDto updatePatient(@RequestBody PatientDto patientDto,
                              @PathVariable String id) {
        log.info("IN PatientController updatePatient() update patient: {}", patientDto);
        return patientService.update(patientDto, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/patients/{id}")
    @Operation(summary = "Getting patient by id")
    public PatientDto findPatientById(@PathVariable String id) {
        log.info("IN PatientController findPatientById() find patient with id: {}", id);
        return patientService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/patients/{id}", produces = {"application/json"})
    @Operation(summary = "Deleting patient by id")
    public void deletePatient(@PathVariable String id) {
        log.info("IN PatientController deletePatient() delete patient with id: {}", id);
        patientService.delete(id);
    }

}
