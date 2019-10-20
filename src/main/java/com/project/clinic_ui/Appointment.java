package com.project.clinic_ui;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    private Doctor doctor;
    private LocalDateTime slot;
    private String firstname;
    private String lastname;
    private String pin;
    private String phoneNumber;
    private String email;

}
