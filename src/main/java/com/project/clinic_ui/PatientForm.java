package com.project.clinic_ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import sun.applet.Main;


public class PatientForm extends FormLayout {

//    private ClinicClient clinicClient;
//    private MainView mainView;
//
//    private TextField firstname = new TextField("First Name");
//    private TextField lastname = new TextField("Last Name");
//    private TextField pin = new TextField("Personal Identifier Number");
//    private TextField phoneNumber = new TextField("Phone Number");
//    private TextField email = new TextField("Email");
//    private Button save = new Button("Add patient");
//    private Button cancel = new Button("Cancel");
//
//    private Binder<Patient> binder = new Binder<>(Patient.class);
//
//    public PatientForm(MainView mainView, ClinicClient clinicClient) {
//        this.mainView = mainView;
//        this.clinicClient = clinicClient;
//        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
//        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        save.addClickListener(event -> save());
//        add(firstname, lastname, pin, phoneNumber, email, buttons);
//    }
//
//    public void save() {
//        Patient patient = binder.getBean();
//        Patient patientInDb = clinicClient.createPatient(patient);
//        mainView.setPatient(patientInDb);
//        mainView.refresh();
//        setPatient(null);
//    }
//
//    public void cancel() {
//        setPatient(null);
//    }
//
//    public void setPatient(Patient patient) {
//        binder.setBean(patient);
//        if(patient == null) {
//            setVisible(false);
//        } else {
//            setVisible(true);
//        }
//    }

}
