package com.project.clinic_ui;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
