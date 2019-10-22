package com.project.clinic_ui.view;

import com.project.clinic_ui.PatientView;
import com.project.clinic_ui.clinic.ClinicClient;
import com.project.clinic_ui.domain.Appointment;
import com.project.clinic_ui.domain.Doctor;
import com.project.clinic_ui.domain.Patient;
import com.project.clinic_ui.domain.Score;
import com.project.clinic_ui.form.AppointmentForm;
import com.project.clinic_ui.form.ScoreForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Route("")
public class MainView extends VerticalLayout implements RouterLayout {

    private ClinicClient clinicClient;
    private AppointmentForm appointmentForm;
    private AdminView adminView;
    private PatientView patientView;
    private ScoreForm scoreForm;
    private Patient patient;

    private H2 label = new H2("NEOCLINIC");
    private ComboBox<String> specialization = new ComboBox<>();
    private TextField filter = new TextField();
    private Button search = new Button("Search");
    private Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);
    private Button adminButton = new Button("Admin panel");
    private Button addScore = new Button("Add score");
    private Button myVisits = new Button("My visits");
    private LoginForm loginAdmin = new LoginForm();
    private LoginForm loginPatient = new LoginForm();

    public MainView(ClinicClient clinicClient) {
        this.clinicClient = clinicClient;
        appointmentForm = new AppointmentForm(this, clinicClient);
        adminView = new AdminView(clinicClient);
        patientView = new PatientView(clinicClient);
        scoreForm = new ScoreForm(this, clinicClient);
        specialization.setItems(specializations());
        specialization.addValueChangeListener(event ->
                doctorGrid.setItems(clinicClient.getDoctorBySpecialization(event.getValue())));
        filter.setPlaceholder("Search...");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update());
        HorizontalLayout horizontalLayout = new HorizontalLayout(specialization, filter, search, addScore, myVisits);
        doctorGrid.setColumns("firstname", "lastname", "rating", "specialization");
        HorizontalLayout mainContent = new HorizontalLayout(doctorGrid, appointmentForm, scoreForm, loginAdmin, loginPatient);
        mainContent.setSizeFull();
        doctorGrid.setSizeFull();
        setHorizontalComponentAlignment(Alignment.END, adminButton);
        setHorizontalComponentAlignment(Alignment.CENTER, label);
        add(label, horizontalLayout, mainContent, adminButton);
        appointmentForm.setAppointment(null, null);
        scoreForm.setScore(null);
        refreshForms();
        setSizeFull();
        refresh();
        search.addClickListener(event -> doctorGrid.setItems(adminView.searching()));
        myVisits.addClickListener(event -> {
            refreshForms();
            loginPatient.setVisible(true);
            LoginI18n i18n = createI18n();
            i18n.getForm().setUsername("Email");
            loginPatient.setI18n(i18n);
            loginPatient.addLoginListener(e -> {
                boolean isAuthenticated = authenticatePatient(e);
                if (isAuthenticated) {
                    myVisits.getUI().ifPresent(ui -> ui.navigate("patient"));
                    patientView.refresh();
                } else {
                    loginPatient.setError(true);
                }
            });
        });
        adminButton.addClickListener(event -> {
            refreshForms();
            loginAdmin.setVisible(true);
            loginAdmin.setI18n(createI18n());
            loginAdmin.addLoginListener(e -> {
                boolean isAuthenticated = authenticateAdmin(e);
                if (isAuthenticated) {
                    adminButton.getUI().ifPresent(ui -> ui.navigate("admin"));
                } else {
                    loginAdmin.setError(true);
                }
            });
        });
        doctorGrid.asSingleSelect().addValueChangeListener(event -> {
            refreshForms();
            Doctor doctor = doctorGrid.asSingleSelect().getValue();
            doctorGrid.asSingleSelect().clear();
            appointmentForm.setAppointment(new Appointment(), doctor);
        });
        addScore.addClickListener(event -> {
            refreshForms();
            scoreForm.setScore(new Score());
        });
    }

    public void refresh() {
        doctorGrid.setItems(clinicClient.getDoctors());
    }

    private void update() {
        doctorGrid.setItems(clinicClient.getDoctorsByLastname(filter.getValue()));
    }

    public LoginI18n createI18n() {
        final LoginI18n i18n = LoginI18n.createDefault();

        i18n.setHeader(new LoginI18n.Header());
        return i18n;
    }

    private Set<String> specializations() {
        return clinicClient.getDoctors().stream()
                .map(doctor -> doctor.getSpecialization())
                .collect(Collectors.toSet());
    }

    private boolean authenticateAdmin(AbstractLogin.LoginEvent event) {
        if(event.getUsername().equals("Admin") && event.getPassword().equals("admin")) {
            return true;
        } else {
            return false;
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
                patientView.setPatient(patient);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void refreshForms() {
        appointmentForm.setVisible(false);
        loginPatient.setVisible(false);
        loginAdmin.setVisible(false);
        scoreForm.setVisible(false);
    }

}
