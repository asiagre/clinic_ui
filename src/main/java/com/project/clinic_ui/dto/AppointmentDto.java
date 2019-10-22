package com.project.clinic_ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private Long id;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime visitDate;

    public AppointmentDto(LocalDateTime appointmentTime, Long patientId, Long doctorId) {
        this.visitDate = appointmentTime;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }
}
