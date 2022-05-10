package com.epam.hospital.exception;

public class PatientNotFoundException extends RuntimeException{

    public PatientNotFoundException(String s) {
        super(s);
    }
}
