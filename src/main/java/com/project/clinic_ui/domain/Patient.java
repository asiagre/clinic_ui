package com.project.clinic_ui.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient {

    private Long id;

    private String firstname;

    private String lastname;

    private String pin;

    private String phoneNumber;

    private String email;

    private String password;

    public Patient(String firstname, String lastname, String pin, String phoneNumber, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.pin = pin;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }


}
