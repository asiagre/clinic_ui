package com.project.clinic_ui.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Doctor {
    private Long id;
    private String firstname;
    private String lastname;
    private String specialization;
    private double rating;
    private Integer[] scores = new Integer[100];
    private LocalDateTime[] freeSlots = new LocalDateTime[100];

    @Override
    public String toString() {
        return firstname + " " + lastname + "(" + specialization + ")";
    }
}
