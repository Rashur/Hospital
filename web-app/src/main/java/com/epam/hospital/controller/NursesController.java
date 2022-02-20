package com.epam.hospital.controller;

import com.epam.hospital.NurseService;
import com.epam.hospital.dto.NurseDto;
import com.epam.hospital.model.Nurse;
import com.epam.hospital.validators.NurseValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
public class NursesController {

    private static final Logger log = LogManager.getLogger(NursesController.class);

    private final NurseService nurseService;
    private final NurseValidator nurseValidator;

    @Autowired
    public NursesController(final NurseService nurseService,
                            final NurseValidator nurseValidator) {
        this.nurseService = nurseService;
        this.nurseValidator = nurseValidator;
    }

    @GetMapping(value = "/nurses")
    public String nurses(Model model) {
        model.addAttribute("nurses", nurseService.findAll());
        log.info("IN NursesController nurses() find all nurses: {}", model);
        return "nurses";
    }

    @GetMapping(value = "/nurse")
    public String addNursePage(Model model) {
        model.addAttribute("isNew", true)
                .addAttribute("nurse", new Nurse());
        log.info("IN NursesController addNursePage() go to add page: {}", model);
        return "nurse";
    }

    @PostMapping(value = "/nurse")
    public String addNurse(NurseDto nurseDto, BindingResult bindingResult) {
        nurseValidator.validate(nurseDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "nurse";
        }

        nurseService.create(nurseDto);
        log.info("IN NursesController addNurse() add nurse: {}", nurseDto);
        return "redirect:/nurses";
    }

    @GetMapping(value = "/nurse/delete")
    public String deleteNurse(@RequestBody NurseDto nurseDto) {
        nurseService.delete(nurseDto);
        log.info("IN NursesController deleteNurse() delete nurse: {}", nurseDto);
        return "redirect:/nurses";
    }

    @GetMapping(value = "/nurse/{id}")
    public String updateNursePage(@PathVariable Integer id, Model model) {
        model.addAttribute("isNew", false)
                .addAttribute("nurse", nurseService.findById(id).get());
        log.info("IN NursesController updateNursePage() go to update page: {}, with id: {}", model, id);
        return "nurse";
    }

    @PostMapping(value = "/nurse/{id}")
    public String updateNurse(NurseDto nurseDto, BindingResult bindingResult) {
        nurseValidator.validate(nurseDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "nurse";
        }

        nurseService.update(nurseDto);
        log.info("IN NursesController updateNurse() update nurse: {}", nurseDto);
        return "redirect:/nurses";
    }
}
