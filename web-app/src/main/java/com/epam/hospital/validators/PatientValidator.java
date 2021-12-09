package com.epam.hospital.validators;

import com.epam.hospital.model.Patient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PatientValidator implements Validator {

    private static final Integer PATIENT_FIELDS_LENGTH = 30;

    @Override
    public boolean supports(Class<?> aClass) {
        return Patient.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "lastName.empty");

        Patient patient = (Patient) o;

        if (StringUtils.hasLength(patient.getFirstName()) && patient.getFirstName().length() > PATIENT_FIELDS_LENGTH
                || StringUtils.hasLength(patient.getLastName()) && patient.getLastName().length() > PATIENT_FIELDS_LENGTH) {
            errors.rejectValue("firstName", "firstName.maxSize");
            errors.rejectValue("lastName", "lastName.maxSize");
        }
    }
}
