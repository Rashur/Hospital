package com.epam.hospital.exception;

public class NurseNotFoundException extends RuntimeException{

    public NurseNotFoundException(String s) {
        super(s);
    }
}
