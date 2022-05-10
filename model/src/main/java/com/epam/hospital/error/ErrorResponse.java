package com.epam.hospital.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Error response")
public class ErrorResponse {

    @Schema(description = "Error name", example = "404 NOT_FOUND")
    private String error;

    @Schema(description = "Error message", example = "Patient with id: 7 doesn't exist")
    private String message;

    @Schema(description = "Error status", example = "404")
    private int status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @Schema(description = "Error time", example = "2022-03-02 04:46:06")
    private LocalDateTime timestamp;
}
