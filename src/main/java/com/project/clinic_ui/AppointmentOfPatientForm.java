package com.project.clinic_ui;

import com.project.clinic_ui.clinic.ClinicClient;
import com.project.clinic_ui.domain.Appointment;
import com.project.clinic_ui.domain.Doctor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;

import java.time.LocalDateTime;

public class AppointmentOfPatientForm extends FormLayout {

    private PatientView patientView;
    private ClinicClient clinicClient;
    private Doctor doctor;

    private ComboBox<LocalDateTime> slot = new ComboBox<>();
    private Button delete = new Button("Delete");
    private Button edit = new Button("Edit");

    private Binder<Appointment> binder = new Binder<>(Appointment.class);

    public AppointmentOfPatientForm(PatientView patientView, ClinicClient clinicClient) {
        this.patientView = patientView;
        this.clinicClient = clinicClient;
        binder.bindInstanceFields(this);
        HorizontalLayout buttons = new HorizontalLayout(edit, delete);
        edit.addClickListener(event -> edit());
        delete.addClickListener(event -> delete());
        add(slot, buttons);
    }

    private void edit() {
        Appointment appointment = binder.getBean();
        clinicClient.editAppointment(appointment, appointment.getSlot());
        patientView.refresh();
        setAppointment(null);
    }

    private void delete() {
        Appointment appointment = binder.getBean();
        clinicClient.deleteAppointment(appointment);
        patientView.refresh();
        setAppointment(null);
    }

    public void setAppointment(Appointment appointment) {
        binder.setBean(appointment);
        if(appointment == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }
}
