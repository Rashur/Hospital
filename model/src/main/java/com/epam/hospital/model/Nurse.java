package com.epam.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document("nurse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nurse {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private List<Patient> patientList;

}
