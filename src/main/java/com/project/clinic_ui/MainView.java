package com.project.clinic_ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import java.util.Set;
import java.util.stream.Collectors;

@Route("")
public class MainView extends VerticalLayout implements RouterLayout {

    private ClinicClient clinicClient;
    private AppointmentForm appointmentForm;
    private AdminView adminView;
    private ScoreForm scoreForm;

    private ComboBox<String> comboBox = new ComboBox<>();
    private TextField textField = new TextField();
    private Button button = new Button("Search");
    private Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);
    private Button adminButton = new Button("Admin panel");
    private Button addScore = new Button("Add score");
    LoginForm component = new LoginForm();
//    private MenuBar menuBar = new MenuBar();
//    private Icon logo = new Icon(VaadinIcon.DOCTOR);
//    private Icon search = new Icon(VaadinIcon.SEARCH);

    public MainView(ClinicClient clinicClient) {
        this.clinicClient = clinicClient;
        appointmentForm = new AppointmentForm(this, clinicClient);
        adminView = new AdminView(clinicClient);
        scoreForm = new ScoreForm(this, clinicClient);
        comboBox.setItems(specializations());
        comboBox.addValueChangeListener(event ->
                doctorGrid.setItems(clinicClient.getDoctorBySpecialization(event.getValue())));
        textField.setPlaceholder("Search...");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.addValueChangeListener(e -> update());
        HorizontalLayout horizontalLayout = new HorizontalLayout(comboBox, textField, button, addScore);
        doctorGrid.setColumns("firstname", "lastname", "rating", "specialization");
        HorizontalLayout mainContent = new HorizontalLayout(doctorGrid, appointmentForm, scoreForm, component);
        mainContent.setSizeFull();
        doctorGrid.setSizeFull();
        add(horizontalLayout, mainContent, adminButton);
        appointmentForm.setAppointment(null, null);
        scoreForm.setScore(null);
        component.setVisible(false);
        adminButton.addClickListener(event -> {
            component.setVisible(true);
            component.setI18n(createI18n());
            component.addLoginListener(e -> {
                boolean isAuthenticated = authenticate(e);
                if (isAuthenticated) {
                    button.getUI().ifPresent(ui -> ui.navigate("admin"));
                } else {
                    component.setError(true);
                }
            });
        });
        setSizeFull();
        refresh();
        doctorGrid.asSingleSelect().addValueChangeListener(event -> {
            Doctor doctor = doctorGrid.asSingleSelect().getValue();
            doctorGrid.asSingleSelect().clear();
            appointmentForm.setAppointment(new Appointment(), doctor);
        });
        addScore.addClickListener(event -> {
            scoreForm.setScore(new Score());
        });

//        menuBar.addItem(logo);
//        menuBar.addItem("HOME");
//        menuBar.addItem("APPOINTMENTS");
//        menuBar.addItem("REGISTER/LOG IN");
//        menuBar.addItem(search);
//        menuBar.setSizeFull();
//        add(menuBar);
    }

    public void refresh() {
        doctorGrid.setItems(clinicClient.getDoctors());
    }

    private void update() {
        doctorGrid.setItems(clinicClient.getDoctorsByLastname(textField.getValue()));
    }

    private Set<String> specializations() {
        return clinicClient.getDoctors().stream()
                .map(doctor -> doctor.getSpecialization())
                .collect(Collectors.toSet());
    }

    private LoginI18n createI18n() {
        final LoginI18n i18n = LoginI18n.createDefault();

        i18n.setHeader(new LoginI18n.Header());
        i18n.getForm().setUsername("Admin");
        i18n.getForm().setPassword("admin");
        return i18n;
    }

    private boolean authenticate(AbstractLogin.LoginEvent event) {
        if(event.getUsername().equals("Admin") && event.getPassword().equals("admin")) {
            return true;
        } else {
            return false;
        }
    }

}
