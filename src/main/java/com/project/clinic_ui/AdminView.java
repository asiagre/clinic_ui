package com.project.clinic_ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Route("admin")
public class AdminView extends VerticalLayout {

    private ClinicClient clinicClient;
    private DoctorForm doctorForm;
    private SlotForm slotForm;

    private Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);
    private ComboBox<String> specialization = new ComboBox<>();
    private TextField filter = new TextField();
    private Button search = new Button("Search");
    private Button addDoctor = new Button("Add doctor");
    private Button addSlot = new Button("Add slot");

    public AdminView(ClinicClient clinicClient) {
        this.clinicClient = clinicClient;
        doctorForm = new DoctorForm(this, clinicClient);
        slotForm = new SlotForm(this, clinicClient);
        filter.setPlaceholder("Filter by lastname");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update(e.getValue()));
        specialization.setItems(specializations());
        specialization.addValueChangeListener(event -> {
            doctorGrid.setItems(clinicClient.getDoctorBySpecialization(specialization.getValue()));
        });
        search.addClickListener(event -> doctorGrid.setItems(searching()));
        addDoctor.addClickListener(event -> {
            doctorGrid.asSingleSelect().clear();
            doctorForm.setDoctor(new Doctor());
        });
        addSlot.addClickListener(event -> {
            slotForm.setSlot(new Slot());
        });
        doctorGrid.setColumns("firstname", "lastname", "specialization", "rating");
        HorizontalLayout horizontalLayout = new HorizontalLayout(specialization, filter, search, addDoctor, addSlot);
        HorizontalLayout mainContent = new HorizontalLayout(doctorGrid, doctorForm, slotForm);
        mainContent.setSizeFull();
        doctorGrid.setSizeFull();
        add(horizontalLayout, mainContent);
        setSizeFull();
        doctorForm.setDoctor(null);
        slotForm.setSlot(null);
        refresh();
        doctorGrid.asSingleSelect().addValueChangeListener(event -> doctorForm.setDoctor(doctorGrid.asSingleSelect().getValue()));
    }

    public void refresh() {
        doctorGrid.setItems(clinicClient.getDoctors());
    }

    public void update(String lastname) {
        doctorGrid.setItems(clinicClient.getDoctorsByLastname(lastname));
    }

    private Set<String> specializations() {
        return clinicClient.getDoctors().stream()
                .map(doctor -> doctor.getSpecialization())
                .collect(Collectors.toSet());
    }

    private List<Doctor> searching() {
        return clinicClient.getDoctors().stream()
                .filter(doctor -> doctor.getSpecialization().equalsIgnoreCase(specialization.getValue()))
                .filter(doctor -> doctor.getLastname().contains(filter.getValue()))
                .collect(Collectors.toList());
    }
}
