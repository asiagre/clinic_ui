package com.project.clinic_ui.domain;

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

    private Long id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime slot;
}
