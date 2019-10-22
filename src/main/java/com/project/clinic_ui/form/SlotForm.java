package com.project.clinic_ui.form;

import com.project.clinic_ui.clinic.ClinicClient;
import com.project.clinic_ui.domain.Doctor;
import com.project.clinic_ui.domain.Slot;
import com.project.clinic_ui.view.AdminView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;

import java.util.ArrayList;
import java.util.List;

public class SlotForm extends FormLayout {

    private final static int SHORT_MONTH = 28;
    private final static int MEDIUM_MONTH = 30;
    private final static int LONG_MONTH = 31;

    private AdminView adminView;
    private ClinicClient clinicClient;

    private ComboBox<Doctor> doctor = new ComboBox<>("Select doctor");
    private ComboBox<Integer> year = new ComboBox<>("Select year");
    private ComboBox<Integer> month = new ComboBox<>("Select month");
    private ComboBox<Integer> day = new ComboBox<>("Select day");
    private ComboBox<Integer> hour = new ComboBox<>("Select hour");
    private ComboBox<Integer> minute = new ComboBox<>("Select minute");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private Binder<Slot> binder = new Binder<>(Slot.class);

    public SlotForm(AdminView adminView, ClinicClient clinicClient) {
        this.adminView = adminView;
        this.clinicClient = clinicClient;
        binder.bindInstanceFields(this);
        doctor.setItems(clinicClient.getDoctors());
        year.setItems(getYears());
        month.setItems(getMonths());
        month.addValueChangeListener(event -> {
            Integer monthValue = month.getValue();
            if(monthValue == null) {
                day.setItems(new ArrayList<>());
            } else if(monthValue == 2) {
                day.setItems(getDays(SHORT_MONTH));
            } else if(monthValue == 4 || monthValue == 6 || monthValue == 9 || monthValue == 11) {
                day.setItems(getDays(MEDIUM_MONTH));
            } else {
                day.setItems(getDays(LONG_MONTH));
            }
        });
        hour.setItems(getHours());
        minute.setItems(getMinutes());
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> save());
        cancel.addClickListener(event -> cancel());
        add(doctor, year, month, day, hour, minute, buttons);
    }

    private void save() {
        Slot slot = binder.getBean();
        clinicClient.addSlot(slot);
        adminView.refresh();
        setSlot(null);
    }

    private void cancel() {
        setSlot(null);
    }

    public void setSlot(Slot slot) {
        binder.setBean(slot);
        if(slot == null) {
            setVisible(false);
        } else {
            doctor.setItems(clinicClient.getDoctors());
            setVisible(true);
        }
    }

    public List<Integer> getYears() {
        List<Integer> years = new ArrayList<>();
        for(int i = 2019; i <= 2022; i++) {
            years.add(i);
        }
        return years;
    }

    public List<Integer> getMonths() {
        List<Integer> months = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
            months.add(i);
        }
        return months;
    }

    public List<Integer> getDays(int daysNumber) {
        List<Integer> days = new ArrayList<>();
        for(int i = 1; i <= daysNumber; i++) {
            days.add(i);
        }
        return days;
    }

    public List<Integer> getHours() {
        List<Integer> hours = new ArrayList<>();
        for(int i = 8; i <= 19; i++) {
            hours.add(i);
        }
        return hours;
    }

    public List<Integer> getMinutes() {
        List<Integer> minutes = new ArrayList<>();
        minutes.add(0);
        minutes.add(15);
        minutes.add(30);
        minutes.add(45);
        return minutes;
    }
}
