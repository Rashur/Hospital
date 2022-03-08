package com.epam.hospital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entity of NurseDto")
public class NurseDto {

    @Schema(description = "Identifier", example = "61")
    private String id;

    @Schema(description = "Firstname of nurse", example = "Tatsiana")
    private String firstName;

    @Schema(description = "Lastname of nurse", example = "Ivanova")
    private String lastName;

    @Schema(description = "List with attached patient's ids", example = "[1, 3, 4]")
    private List<String> patientIds;

}
