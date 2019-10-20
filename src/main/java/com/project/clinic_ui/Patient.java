package com.project.clinic_ui;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaadin.flow.data.binder.PropertyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient {

    private Long id;

    @PropertyId("firstname")
    private String firstname;

    @PropertyId("lastname")
    private String lastname;

    @PropertyId("pin")
    private String pin;

    @PropertyId("phoneNumber")
    private String phoneNumber;

    @PropertyId("email")
    private String email;

//    private String password;
//    private boolean admin;


    public Patient(String firstname, String lastname, String pin, String phoneNumber, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.pin = pin;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
