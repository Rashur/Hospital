package com.epam.hospital;

import com.epam.hospital.dto.DateRange;
import com.epam.hospital.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class PatientsController {

    private static final Logger log = LogManager.getLogger(PatientsController.class);

    private final PatientService patientService;
    private final NurseService nurseService;
    private final PatientDtoServiceImpl patientDtoService;

    public PatientsController(final PatientService patientService,
                              final NurseService nurseService,
                              final PatientDtoServiceImpl patientDtoService) {
        this.patientService = patientService;
        this.nurseService = nurseService;
        this.patientDtoService = patientDtoService;
    }

    @GetMapping(value = "/patients")
    public String patientsPage(Model model) {
        DateRange dateRange = new DateRange();
        dateRange.setDateFrom(new Date());
        dateRange.setDateTo(new Date());
        model.addAttribute("patients", patientDtoService.findAllPatientsWithNurseName());
        model.addAttribute("dateRange", dateRange);
        log.info("IN PatientsController patientsPage() go to patients: {}", model);
        return "patients";
    }

    @PostMapping(value = "/patients/date-range")
    public ModelAndView findPatientsInDateRange(DateRange dateRange) {
        ModelAndView modelAndView = new ModelAndView("patients");
        modelAndView.addObject("patients", patientDtoService.findAllPatientsInRange(dateRange));
        log.info("IN PatientsController findPatientsInDateRange() date range from {} to {} with patients: {}",
                dateRange.getDateFrom(),
                dateRange.getDateTo(),
                patientDtoService.findAllPatientsInRange(dateRange));
        return modelAndView;
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
                .addAttribute("patient", patientService.findById(id).get())
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
