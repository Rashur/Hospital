package com.epam.hospital.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

public class Patient {

    private Integer id;
    private String firstName;
    private String lastName;
    private String diagnosis;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate illnessDate;
    private Integer nursesId;

    public Patient() {
    }

    public Patient(Integer id, String firstName, String lastName, String diagnosis, LocalDate illnessDate, Integer nursesId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.diagnosis = diagnosis;
        this.illnessDate = illnessDate;
        this.nursesId = nursesId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public LocalDate getIllnessDate() {
        return illnessDate;
    }

    public void setIllnessDate(LocalDate illnessDate) {
        this.illnessDate = illnessDate;
    }

    public Integer getNursesId() {
        return nursesId;
    }

    public void setNursesId(Integer nursesId) {
        this.nursesId = nursesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) && Objects.equals(firstName, patient.firstName) && Objects.equals(lastName, patient.lastName) && Objects.equals(diagnosis, patient.diagnosis) && Objects.equals(illnessDate, patient.illnessDate) && Objects.equals(nursesId, patient.nursesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, diagnosis, illnessDate, nursesId);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", illnessDate=" + illnessDate +
                ", nursesId=" + nursesId +
                '}';
    }
}
