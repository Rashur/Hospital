package com.epam.hospital;

import com.epam.hospital.model.Nurse;
import com.epam.hospital.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PatientsController {

    private static final Logger log = LogManager.getLogger(PatientsController.class);

    private final PatientService patientService;
    private final NurseService nurseService;

    public PatientsController(final PatientService patientService,
                              final NurseService nurseService) {
        this.patientService = patientService;
        this.nurseService = nurseService;
    }

    @GetMapping(value = "/patients")
    public String patientsPage(Model model) {
        model.addAttribute("patients", patientService.findAll());
        log.info("IN PatientsController patientsPage() go to patients: {}", model);
        return "patients";
    }

    @GetMapping(value = "/patient")
    public String addPatientPage(Model model) {
        model.addAttribute("isNew", true)
                .addAttribute("patient", new Patient())
                .addAttribute("nurses", nurseService.findAll());
        log.info("IN PatientsController addPatientPage() go to add patient page: {}", model);
        return "patient";
    }

    @PostMapping(value = "/patient")
    public String addPatient(Patient patient) {
        patientService.create(patient);
        log.info("IN PatientsController addPatient() add patient: {}", patient);
        return "redirect:/patients";
    }

    @GetMapping(value = "/patient/{id}")
    public String updatePatientPage(@PathVariable Integer id, Model model) {
        model.addAttribute("isNew", false)
                .addAttribute("patient", patientService.findById(id))
                .addAttribute("nurses", nurseService.findAll());
        log.info("IN PatientsController updatePatientPage() go to update patient page: {}", model);
        return "patient";
    }

    @PostMapping(value = "/patient/{id}")
    public String updatePatient(Patient patient) {
        patientService.update(patient);
        log.info("IN PatientsController updatePatient() update patient: {}", patient);
        return "redirect:/patients";
    }

    @GetMapping(value = "/patient/{id}/delete")
    public String deletePatient(@PathVariable Integer id) {
        patientService.delete(id);
        log.info("IN PatientsController deletePatient() delete patient with id: {}", id);
        return "redirect:/patients";
    }
}
