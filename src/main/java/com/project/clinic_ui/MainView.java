package com.project.clinic_ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Route("")
public class MainView extends VerticalLayout implements RouterLayout {

    private ClinicClient clinicClient;
    private PatientForm patientForm;
    private AdminView adminView;

    private ComboBox<String> comboBox = new ComboBox<>();
    private TextField textField = new TextField();
    private Button button = new Button("Search");
    private Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);
    private Button adminButton = new Button("Admin panel");
//    private MenuBar menuBar = new MenuBar();
//    private Icon logo = new Icon(VaadinIcon.DOCTOR);
//    private Icon search = new Icon(VaadinIcon.SEARCH);

    public MainView(ClinicClient clinicClient) {
        this.clinicClient = clinicClient;
        patientForm = new PatientForm(this, clinicClient);
        adminView = new AdminView(clinicClient);
        comboBox.setItems(specializations());
        comboBox.addValueChangeListener(event ->
                doctorGrid.setItems(clinicClient.getDoctorBySpecialization(event.getValue())));
        textField.setPlaceholder("Search...");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.addValueChangeListener(e -> update());
        HorizontalLayout horizontalLayout = new HorizontalLayout(comboBox, textField, button);
        doctorGrid.setColumns("firstname", "lastname", "rating", "specialization");
        HorizontalLayout mainContent = new HorizontalLayout(doctorGrid, patientForm);
        mainContent.setSizeFull();
        doctorGrid.setSizeFull();
        add(horizontalLayout, mainContent, adminButton);
        patientForm.setAppointment(null, null);
        adminButton.addClickListener(e -> {
            button.getUI().ifPresent(ui -> ui.navigate("admin"));
        });
        setSizeFull();
        refresh();
//        makeAppointment.addClickListener(event -> {
//            doctorGrid.asSingleSelect().clear();
//            patientForm.setAppointment(new Appointment());
//        });
        doctorGrid.asSingleSelect().addValueChangeListener(event -> {
            Doctor doctor = doctorGrid.asSingleSelect().getValue();
            doctorGrid.asSingleSelect().clear();
            patientForm.setAppointment(new Appointment(), doctor);
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

}
