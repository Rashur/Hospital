package com.epam.hospital.rest;

import com.epam.hospital.PatientService;
import com.epam.hospital.dto.PatientDto;
import com.epam.hospital.model.Patient;
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
    public List<Patient> allPatients() {
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
                              @PathVariable Integer id) {
        log.info("IN PatientController updatePatient() update patient: {}", patientDto);
        return patientService.update(patientDto, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/patients/{id}")
    @Operation(summary = "Getting patient by id")
    public Patient findPatientById(@PathVariable Integer id) {
        log.info("IN PatientController findPatientById() find patient with id: {}", id);
        return patientService.findById(id).get();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/patients/{id}", produces = {"application/json"})
    @Operation(summary = "Deleting patient by id")
    public void deletePatient(@PathVariable Integer id) {
        log.info("IN PatientController deletePatient() delete patient with id: {}", id);
        patientService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/patients/size={size}")
    @Operation(summary = "Getting patients if they have more nurse than given")
    public List<PatientDto> patientsByNurseListSize(@PathVariable Long size) {
        return patientService.allPatientWithNurseListGreaterThan(size);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/patients/fake")
    @Operation(summary = "Creating fake patient")
    public void createFakePatient() {
        log.info("IN PatientController createFakePatient() create fake patient");
        patientService.createFakePatient();
    }
}
