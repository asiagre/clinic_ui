package com.project.clinic_ui.form;

import com.project.clinic_ui.clinic.ClinicClient;
import com.project.clinic_ui.domain.Doctor;
import com.project.clinic_ui.view.AdminView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class DoctorForm extends FormLayout {

    private AdminView adminView;
    private ClinicClient clinicClient;

    private TextField firstname = new TextField("Firstname");
    private TextField lastname = new TextField("Lastname");
    private TextField specialization = new TextField("Specialization");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button edit = new Button("Edit");

    private Binder<Doctor> binder = new Binder<>(Doctor.class);

    public DoctorForm(AdminView adminView, ClinicClient clinicClient) {
        this.adminView = adminView;
        this.clinicClient = clinicClient;
        binder.bindInstanceFields(this);
        HorizontalLayout buttons = new HorizontalLayout(save, edit, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> save());
        edit.addClickListener(event -> edit());
        delete.addClickListener(event -> delete());
        add(firstname, lastname, specialization, buttons);
    }

    private void save() {
        Doctor doctor = binder.getBean();
        doctor.setId(0L);
        clinicClient.createDoctor(doctor);
        adminView.refresh();
        setDoctor(null);
    }

    private void edit() {
        Doctor doctor = binder.getBean();
        clinicClient.editDoctor(doctor);
        adminView.refresh();
        setDoctor(null);
    }

    private void delete() {
        Doctor doctor = binder.getBean();
        clinicClient.deleteDoctor(doctor);
        adminView.refresh();
        setDoctor(null);
    }

    public void setDoctor(Doctor doctor) {
        binder.setBean(doctor);
        if(doctor == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstname.focus();
        }
    }

}
