package com.project.clinic_ui;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;


public class PatientForm extends FormLayout {

    private ClinicClient clinicClient;

    private TextField firstname = new TextField("First Name");
    private TextField lastname = new TextField("Last Name");
    private TextField pin = new TextField("Personal Identifier Number");
    private TextField phoneNumber = new TextField("Phone Number");
    private TextField email = new TextField("Email");

    private Binder<Patient> binder = new Binder<>(Patient.class);

    public PatientForm(ClinicClient clinicClient) {
        this.clinicClient = clinicClient;
        add(firstname, lastname, pin, phoneNumber, email);
    }

    public Patient addPatient() {
        Patient patient = binder.getBean();
        return clinicClient.createPatient(patient);
    }

}
