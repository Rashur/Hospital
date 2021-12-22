package com.epam.hospital.controller;

import com.epam.hospital.NurseService;
import com.epam.hospital.PatientDtoService;
import com.epam.hospital.PatientService;
import com.epam.hospital.dto.DateRange;
import com.epam.hospital.model.Patient;
import com.epam.hospital.validators.PatientValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private final PatientDtoService patientDtoService;
    private final PatientValidator patientValidator;

    public PatientsController(final PatientService patientService,
                              final NurseService nurseService,
                              final PatientDtoService patientDtoService,
                              final PatientValidator patientValidator) {
        this.patientService = patientService;
        this.nurseService = nurseService;
        this.patientDtoService = patientDtoService;
        this.patientValidator = patientValidator;
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
    public ModelAndView addPatient(Patient patient, BindingResult bindingResult) {
        patientValidator.validate(patient, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("patient").addObject("nurses", nurseService.findAll());
        }

        patientService.create(patient);
        log.info("IN PatientsController addPatient() add patient: {}", patient);
        return new ModelAndView("redirect:/patients");
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
    public ModelAndView updatePatient(Patient patient, BindingResult bindingResult) {
        patientValidator.validate(patient, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("patient").addObject("nurses", nurseService.findAll());
        }

        patientService.update(patient);
        log.info("IN PatientsController updatePatient() update patient: {}", patient);
        return new ModelAndView("redirect:/patients");
    }

    @GetMapping(value = "/patient/{id}/delete")
    public String deletePatient(@PathVariable Integer id) {
        patientService.delete(id);
        log.info("IN PatientsController deletePatient() delete patient with id: {}", id);
        return "redirect:/patients";
    }
}
