package com.project.clinic_ui;

import com.project.clinic_ui.clinic.ClinicClient;
import com.project.clinic_ui.domain.Appointment;
import com.project.clinic_ui.domain.Patient;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.Route;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Route("patient")
public class PatientView extends VerticalLayout {

    private ClinicClient clinicClient;
    private AppointmentOfPatientForm appointmentOfPatientForm;
    private Patient patient;

    private H2 myVisits = new H2("MY APPOINTMENTS");
    private Grid<Appointment> appointmentGrid = new Grid<>(Appointment.class);

    public PatientView(ClinicClient clinicClient) {
        this.clinicClient = clinicClient;
        appointmentOfPatientForm = new AppointmentOfPatientForm(this, clinicClient);
        HorizontalLayout mainContent = new HorizontalLayout(appointmentGrid, appointmentOfPatientForm);
        mainContent.setSizeFull();
        appointmentGrid.setColumns("slot", "doctor");
        appointmentGrid.setSizeFull();
        add(myVisits, mainContent);
        setSizeFull();
        appointmentOfPatientForm.setAppointment(null);
        setHorizontalComponentAlignment(Alignment.CENTER, myVisits);
        //refresh();
        //appointmentGrid.asSingleSelect().addValueChangeListener(event -> appointmentOfPatientForm.setAppointment(appointmentGrid.asSingleSelect().getValue()));
    }

    public void refresh() {
        if(patient != null) {
            List<Appointment> appointmentList = clinicClient.getPatientAppointments(patient);
            appointmentGrid.addColumn(appointment -> appointment.getDoctor().toString());
            appointmentGrid.addColumn(appointment -> appointment.getSlot().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            appointmentGrid.setItems(appointmentList);
        }
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
