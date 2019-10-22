package com.project.clinic_ui.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Slot {

    private Doctor doctor;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minute;
}
