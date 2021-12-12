package com.epam.hospital.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

public class PatientDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String diagnosis;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate illnessDate;
    private String nurseFirstName;
    private String nurseLastName;

    public PatientDto(Integer id, String firstName, String lastName, String diagnosis, LocalDate illnessDate, String nurseFirstName, String nurseLastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.diagnosis = diagnosis;
        this.illnessDate = illnessDate;
        this.nurseFirstName = nurseFirstName;
        this.nurseLastName = nurseLastName;
    }

    public PatientDto() {
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

    public String getNurseFirstName() {
        return nurseFirstName;
    }

    public void setNurseFirstName(String nurseFirstName) {
        this.nurseFirstName = nurseFirstName;
    }

    public String getNurseLastName() {
        return nurseLastName;
    }

    public void setNurseLastName(String nurseLastName) {
        this.nurseLastName = nurseLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientDto that = (PatientDto) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(diagnosis, that.diagnosis) && Objects.equals(illnessDate, that.illnessDate) && Objects.equals(nurseFirstName, that.nurseFirstName) && Objects.equals(nurseLastName, that.nurseLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, diagnosis, illnessDate, nurseFirstName, nurseLastName);
    }

    @Override
    public String toString() {
        return "PatientDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", illnessDate=" + illnessDate +
                ", nurseFirstName='" + nurseFirstName + '\'' +
                ", nurseLastName='" + nurseLastName + '\'' +
                '}';
    }
}
