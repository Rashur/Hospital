package com.epam.hospital.model;

import java.util.Date;
import java.util.Objects;

public class Patient {
    private Integer id;

    private String firstName;

    private String lastName;

    private String diagnosis;

    private Date illnessDate;

    private Integer nursesId;

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

    public Date getIllnessDate() {
        return illnessDate;
    }

    public void setIllnessDate(Date illnessDate) {
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
}
