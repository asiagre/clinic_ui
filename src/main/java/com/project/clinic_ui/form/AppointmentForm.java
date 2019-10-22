package com.project.clinic_ui.form;

import com.project.clinic_ui.domain.Appointment;
import com.project.clinic_ui.clinic.ClinicClient;
import com.project.clinic_ui.domain.Doctor;
import com.project.clinic_ui.domain.Patient;
import com.project.clinic_ui.dto.AppointmentDto;
import com.project.clinic_ui.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentForm extends FormLayout {

//    private ClinicClient clinicClient;
//    private MainView mainView;
//
//    private ComboBox<Doctor> doctor = new ComboBox<>();
//    private ComboBox<LocalDateTime> slot = new ComboBox<>();
//    private TextField firstname = new TextField("First Name");
//    private TextField lastname = new TextField("Last Name");
//    private TextField pin = new TextField("Personal Identifier Number");
//    private TextField phoneNumber = new TextField("Phone Number");
//    private TextField email = new TextField("Email");
//    private PasswordField password = new PasswordField("Password");
//    private Button makeAppointmentButton = new Button("Make an Appointment");
//    private Button cancelButton = new Button("Cancel");
//    private Binder<Appointment> binder = new Binder<>(Appointment.class);
//
//    public AppointmentForm(MainView mainView, ClinicClient clinicClient) {
//        this.mainView = mainView;
//        this.clinicClient = clinicClient;
//        binder.bindInstanceFields(this);
//        HorizontalLayout buttons = new HorizontalLayout(makeAppointmentButton, cancelButton);
//        makeAppointmentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        makeAppointmentButton.addClickListener(event -> makeAppointment());
//        cancelButton.addClickListener(event -> cancel());
//        add(doctor, slot, firstname, lastname, pin, phoneNumber, email, password, buttons);
//    }
//
//    private void cancel() {
//        setAppointment(null, null);
//    }
//
//    private void makeAppointment() {
//        Appointment appointment = binder.getBean();
//        Patient patient = new Patient(firstname.getValue(),
//                lastname.getValue(),
//                pin.getValue(),
//                phoneNumber.getValue(),
//                email.getValue(),
//                password.getValue());
//        Patient patientInDb = clinicClient.createPatient(patient);
//        AppointmentDto appointmentDto = new AppointmentDto(
//                appointment.getSlot(),
//                patientInDb.getId(),
//                appointment.getDoctor().getId()
//        );
//        clinicClient.makeAppointment(appointmentDto);
//        setAppointment(null, null);
//        mainView.refresh();
//    }
//
//    public void setAppointment(Appointment appointment, Doctor doc) {
//        binder.setBean(appointment);
//        if(appointment == null) {
//            setVisible(false);
//        } else {
//            if(doc != null) {
//                doctor.setValue(doc);
//                slot.setItems(doc.getFreeSlots());
//            }
//            setVisible(true);
//        }
//    }

    private ClinicClient clinicClient;
    private MainView mainView;
    private Patient patient;

    private Button hasAccount = new Button("Log In");
    private Button register = new Button("Register");
    private ComboBox<Doctor> doctor = new ComboBox<>();
    private ComboBox<LocalDateTime> slot = new ComboBox<>();
    private TextField firstname = new TextField("First Name");
    private TextField lastname = new TextField("Last Name");
    private TextField pin = new TextField("Personal Identifier Number");
    private TextField phoneNumber = new TextField("Phone Number");
    private TextField email = new TextField("Email");
    private PasswordField password = new PasswordField("Password");
    private Button makeAppointmentButton = new Button("Make an Appointment");
    private Button cancelButton = new Button("Cancel");
    private Binder<Appointment> binder = new Binder<>(Appointment.class);
    private LoginForm loginForm = new LoginForm();
    private HorizontalLayout buttons = new HorizontalLayout();
    private HorizontalLayout hl = new HorizontalLayout();

    public AppointmentForm(MainView mainView, ClinicClient clinicClient) {
        this.mainView = mainView;
        this.clinicClient = clinicClient;
        binder.bindInstanceFields(this);
        hasAccount.setSizeFull();
        register.setSizeFull();
        buttons.add(makeAppointmentButton, cancelButton);
        hl.add(hasAccount, register);
        refreshForm(false);
        hasAccount.addClickListener(event -> {
            refreshForm(false);
            loginForm.setVisible(true);
            LoginI18n i18n = mainView.createI18n();
            i18n.getForm().setUsername("Email");
            loginForm.setI18n(i18n);
            loginForm.addLoginListener(e -> {
                boolean isAuthenticated = authenticatePatient(e);
                if (isAuthenticated) {
                    loginForm.setVisible(false);
                    doctor.setVisible(true);
                    slot.setVisible(true);
                    buttons.setVisible(true);
                    hl.setVisible(false);
                } else {
                    loginForm.setError(true);
                }
            });
        });
        register.addClickListener(event -> {
            refreshForm(true);
            loginForm.setVisible(false);
        });
        makeAppointmentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        makeAppointmentButton.addClickListener(event -> makeAppointment());
        cancelButton.addClickListener(event -> cancel());
        add(hl, loginForm, doctor, slot, firstname, lastname, pin, phoneNumber, email, password, buttons);
    }

    private void cancel() {
        setAppointment(null, null);
        refreshForm(false);
        patient = null;
    }

    private void makeAppointment() {
        Appointment appointment = binder.getBean();
        if(patient == null) {
            patient = new Patient(firstname.getValue(),
                    lastname.getValue(),
                    pin.getValue(),
                    phoneNumber.getValue(),
                    email.getValue(),
                    password.getValue());
        }
        Patient patientInDb = clinicClient.createPatient(patient);
        AppointmentDto appointmentDto = new AppointmentDto(
                appointment.getSlot(),
                patientInDb.getId(),
                appointment.getDoctor().getId()
        );
        clinicClient.makeAppointment(appointmentDto);
        patient = null;
        refreshForm(false);
        setAppointment(null, null);
        clearForm();
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

    private boolean authenticatePatient(AbstractLogin.LoginEvent event) {
        List<Patient> patientList = clinicClient.getPatients();
        List<Patient> filterPatients = patientList.stream()
                .filter(patient -> patient.getEmail().equals(event.getUsername()))
                .collect(Collectors.toList());
        if(filterPatients.size() == 1) {
            if(filterPatients.get(0).getPassword().equals(event.getPassword())) {
                patient = filterPatients.get(0);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void refreshForm(boolean bool) {
        doctor.setVisible(bool);
        slot.setVisible(bool);
        firstname.setVisible(bool);
        lastname.setVisible(bool);
        pin.setVisible(bool);
        phoneNumber.setVisible(bool);
        email.setVisible(bool);
        password.setVisible(bool);
        buttons.setVisible(bool);
        loginForm.setVisible(bool);
        hl.setVisible(!bool);
    }

    private void clearForm() {
        firstname.clear();
        lastname.clear();
        email.clear();
        pin.clear();
        phoneNumber.clear();
        password.clear();
    }
}
