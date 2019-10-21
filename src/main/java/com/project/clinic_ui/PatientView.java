package com.project.clinic_ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("patient")
public class PatientView extends VerticalLayout {

    private ClinicClient clinicClient;
    private AppointmentOfPatientForm appointmentOfPatientForm;

    private Grid<Appointment> appointmentGrid = new Grid<>(Appointment.class);

    public PatientView(ClinicClient clinicClient, Patient patient) {
        this.clinicClient = clinicClient;
//        appointmentOfPatientForm = new DoctorForm(this, clinicClient);
//        appointmentGrid.setColumns("slot", "doctor");
//        HorizontalLayout mainContent = new HorizontalLayout(appointmentGrid, appointmentOfPatientForm);
//        mainContent.setSizeFull();
//        appointmentGrid.setSizeFull();
//        add(mainContent);
//        setSizeFull();
//        appointmentOfPatientForm.setAppointment(null);
//        refresh();
//        appointmentGrid.asSingleSelect().addValueChangeListener(event -> appointmentOfPatientForm.setAppointment(appointmentGrid.asSingleSelect().getValue()));
    }
//
//    public void refresh() {
//        appointmentGrid.setItems(clinicClient.getDoctors());
//    }

}
