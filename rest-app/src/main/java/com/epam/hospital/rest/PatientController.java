package com.epam.hospital.rest;

import com.epam.hospital.PatientDao;
import com.epam.hospital.PatientDtoService;
import com.epam.hospital.PatientService;
import com.epam.hospital.dto.DateRange;
import com.epam.hospital.dto.PatientDto;
import com.epam.hospital.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PatientController {

    private static final Logger log = LogManager.getLogger(PatientController.class);

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/patients")
    public List<PatientDto> allPatients() {
        log.info("IN PatientController allPatients() find all patients");
        return patientService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/patients", consumes = "application/json", produces = "application/json")
    public void createPatient(@RequestBody PatientDto patientDto) {
        log.info("IN PatientController createPatient() create patient: {}", patientDto);
        patientService.create(patientDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/patients/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public void updatePatient(@RequestBody PatientDto patientDto,
                              @PathVariable Integer id) {
        log.info("IN PatientController updatePatient() update patient: {}", patientDto);
        patientService.update(patientDto, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/patients/{id}")
    public PatientDto findPatientById(@PathVariable Integer id) {
        log.info("IN PatientController findPatientById() find patient with id: {}", id);
        return patientService.findById(id).get();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/patients/{id}", produces = {"application/json"})
    public void deletePatient(@PathVariable Integer id) {
        log.info("IN PatientController deletePatient() delete patient with id: {}", id);
        patientService.delete(id);
    }

//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping(value = "/patients/date-range")
//    public List<PatientDto> findPatientsInDateRange(@RequestBody DateRange dateRange) {
//        log.info("IN PatientController deletePatient() find patients in date range from: {} to: {}", dateRange.getDateFrom(), dateRange.getDateTo());
//        patientService.findAllPatientsInRange(dateRange);
//    }
}
