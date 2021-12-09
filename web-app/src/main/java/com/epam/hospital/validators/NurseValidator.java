package com.epam.hospital.validators;

import com.epam.hospital.model.Nurse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class NurseValidator implements Validator {

    private static final Integer NURSE_FIELDS_LENGTH = 30;

    @Override
    public boolean supports(Class<?> aClass) {
        return Nurse.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "lastName.empty");

        Nurse nurse = (Nurse) o;

        if (StringUtils.hasLength(nurse.getFirstName()) && nurse.getFirstName().length() > NURSE_FIELDS_LENGTH
            || StringUtils.hasLength(nurse.getLastName()) && nurse.getLastName().length() > NURSE_FIELDS_LENGTH) {
            errors.rejectValue("firstName", "firstName.maxSize");
            errors.rejectValue("lastName", "lastName.maxSize");
        }
    }
}
