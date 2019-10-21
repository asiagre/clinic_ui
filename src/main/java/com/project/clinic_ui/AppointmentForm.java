package com.project.clinic_ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentForm extends FormLayout {

    private ClinicClient clinicClient;
    private MainView mainView;

    private ComboBox<Doctor> doctor = new ComboBox<>();
    private ComboBox<LocalDateTime> slot = new ComboBox<>();
    private PatientForm patient;
//    private TextField firstname = new TextField("First Name");
//    private TextField lastname = new TextField("Last Name");
//    private TextField pin = new TextField("Personal Identifier Number");
//    private TextField phoneNumber = new TextField("Phone Number");
//    private TextField email = new TextField("Email");
    private Button makeAppointmentButton = new Button("Make an Appointment");
    private Button cancelButton = new Button("Cancel");
    private Binder<Appointment> binder = new Binder<>(Appointment.class);

    public AppointmentForm(MainView mainView, ClinicClient clinicClient) {
        this.mainView = mainView;
        this.clinicClient = clinicClient;
        this.patient = new PatientForm(clinicClient);
        binder.bindInstanceFields(this);
//        doctor.setItems(clinicClient.getDoctors());
        HorizontalLayout buttons = new HorizontalLayout(makeAppointmentButton, cancelButton);
        makeAppointmentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        makeAppointmentButton.addClickListener(event -> makeAppointment());
        cancelButton.addClickListener(event -> cancel());
        add(doctor, slot, patient, buttons);
    }

    private void cancel() {
        setAppointment(null, null);
    }

    private void makeAppointment() {
        Appointment appointment = binder.getBean();
        Patient patientInDb = patient.addPatient();
//        Patient patient = new Patient(firstname.getValue(),
//                lastname.getValue(),
//                pin.getValue(),
//                phoneNumber.getValue(),
//                email.getValue());
//        Patient patientInDb = clinicClient.createPatient(patient);
        AppointmentDto appointmentDto = new AppointmentDto(
                appointment.getSlot(),
                patientInDb.getId(),
                appointment.getDoctor().getId()
        );
        clinicClient.makeAppointment(appointmentDto);
        setAppointment(null, null);
        mainView.refresh();
    }

    public void setAppointment(Appointment appointment, Doctor doc) {
        binder.setBean(appointment);
        if(appointment == null) {
            setVisible(false);
        } else {
            if(doc != null) {
                doctor.setValue(doc);
                slot.setItems(doc.getFreeSlots());
            }
            setVisible(true);
        }
    }
}
