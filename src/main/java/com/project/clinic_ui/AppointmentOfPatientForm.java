package com.project.clinic_ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppointmentOfPatientForm extends FormLayout {

//    private PatientView patientView;
//    private ClinicClient clinicClient;
//    private Doctor doctor;
//
//    private ComboBox<LocalDateTime> slot = new ComboBox<>();
//    private Button delete = new Button("Delete");
//    private Button edit = new Button("Edit");
//
//    private Binder<Appointment> binder = new Binder<>(Appointment.class);
//
//    public AppointmentOfPatientForm(PatientView patientView, ClinicClient clinicClient, Doctor doctor) {
//        this.patientView = patientView;
//        this.clinicClient = clinicClient;
//        binder.bindInstanceFields(this);
//        HorizontalLayout buttons = new HorizontalLayout(edit, delete);
//        edit.addClickListener(event -> edit());
//        delete.addClickListener(event -> delete());
//        add(slot, buttons);
//    }
//
//    private void edit() {
//        Appointment appointment = binder.getBean();
//        clinicClient.(doctor);
//        adminView.refresh();
//        setDoctor(null);
//    }
//
//    private void delete() {
//        Doctor doctor = binder.getBean();
//        clinicClient.deleteDoctor(doctor);
//        adminView.refresh();
//        setDoctor(null);
//    }
//
//    public void setDoctor(Doctor doctor) {
//        binder.setBean(doctor);
//        if(doctor == null) {
//            setVisible(false);
//        } else {
//            setVisible(true);
//            firstname.focus();
//        }
//    }
}
