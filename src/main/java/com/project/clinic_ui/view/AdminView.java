package com.project.clinic_ui.view;

import com.project.clinic_ui.clinic.ClinicClient;
import com.project.clinic_ui.domain.Doctor;
import com.project.clinic_ui.domain.Slot;
import com.project.clinic_ui.form.DoctorForm;
import com.project.clinic_ui.form.SlotForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Route("admin")
public class AdminView extends VerticalLayout implements RouterLayout {

    private ClinicClient clinicClient;
    private DoctorForm doctorForm;
    private SlotForm slotForm;

    private H2 label = new H2("ADMIN PANEL");
    private Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);
    private ComboBox<String> specialization = new ComboBox<>();
    private TextField filter = new TextField();
    private Button search = new Button("Search");
    private Button addDoctor = new Button("Add doctor");
    private Button addSlot = new Button("Add slot");
    private Button back = new Button("Return");

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
        doctorGrid.setColumns("firstname", "lastname", "specialization", "rating");
        HorizontalLayout horizontalLayout = new HorizontalLayout(specialization, filter, search, addDoctor, addSlot);
        HorizontalLayout mainContent = new HorizontalLayout(doctorGrid, doctorForm, slotForm);
        mainContent.setSizeFull();
        doctorGrid.setSizeFull();
        setHorizontalComponentAlignment(Alignment.CENTER, label);
        add(label, horizontalLayout, mainContent, back);
        setSizeFull();
        doctorForm.setDoctor(null);
        slotForm.setSlot(null);
        refresh();
        back.addClickListener(event -> back.getUI().ifPresent(ui -> ui.navigate("")));
        addDoctor.addClickListener(event -> {
            slotForm.setVisible(false);
            doctorGrid.asSingleSelect().clear();
            doctorForm.setDoctor(new Doctor());
        });
        addSlot.addClickListener(event -> {
            doctorForm.setVisible(false);
            slotForm.setSlot(new Slot());
        });
        doctorGrid.asSingleSelect().addValueChangeListener(event -> {
            slotForm.setVisible(false);
            doctorForm.setDoctor(doctorGrid.asSingleSelect().getValue());
        });
    }

    public void refresh() {
        doctorGrid.setItems(clinicClient.getDoctors());
    }

    private void update(String lastname) {
        doctorGrid.setItems(clinicClient.getDoctorsByLastname(lastname));
    }

    private Set<String> specializations() {
        return clinicClient.getDoctors().stream()
                .map(doctor -> doctor.getSpecialization())
                .collect(Collectors.toSet());
    }

    public List<Doctor> searching() {
        return clinicClient.getDoctors().stream()
                .filter(doctor -> doctor.getSpecialization().equalsIgnoreCase(specialization.getValue()))
                .filter(doctor -> doctor.getLastname().contains(filter.getValue()))
                .collect(Collectors.toList());
    }
}
