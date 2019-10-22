package com.project.clinic_ui.domain;

import com.project.clinic_ui.domain.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private Doctor doctor;
    private Integer score;
}
