package com.epam.hospital.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entity of PatientDto")
public class PatientDto {

    @Schema(description = "Identifier", example = "512")
    private String id;

    @Schema(description = "Firstname of patient", example = "Ivan")
    private String firstName;

    @Schema(description = "Firstname of patient", example = "Ivanov")
    private String lastName;

    @Schema(description = "Diagnosis of patient", example = "Skalioz")
    private String diagnosis;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Illness date of patient", example = "2022-02-10")
    private LocalDate illnessDate;

    @Schema(description = "List with attached nurse's ids", example = "[2,3]")
    private List<String> nurseIds;

}
