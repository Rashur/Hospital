package com.epam.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NurseDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private List<Integer> patientIds;

}
