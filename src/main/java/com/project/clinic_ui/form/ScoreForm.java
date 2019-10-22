package com.project.clinic_ui.form;

import com.project.clinic_ui.clinic.ClinicClient;
import com.project.clinic_ui.domain.Doctor;
import com.project.clinic_ui.domain.Score;
import com.project.clinic_ui.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;

import java.util.ArrayList;
import java.util.List;

public class ScoreForm extends FormLayout {

    private MainView mainView;
    private ClinicClient clinicClient;

    private ComboBox<Doctor> doctor = new ComboBox<>("Select doctor");
    private ComboBox<Integer> score = new ComboBox<>("Score");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private Binder<Score> binder = new Binder<>(Score.class);

    public ScoreForm(MainView mainView, ClinicClient clinicClient) {
        this.mainView = mainView;
        this.clinicClient = clinicClient;
        binder.bindInstanceFields(this);
        doctor.setItems(clinicClient.getDoctors());
        score.setItems(getScores());
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> save());
        cancel.addClickListener(event -> cancel());
        add(doctor, score, buttons);
    }

    private void save() {
        Score score = binder.getBean();
        clinicClient.addScore(score);
        mainView.refresh();
        setScore(null);
    }

    private void cancel() {
        setScore(null);
    }

    public void setScore(Score score) {
        binder.setBean(score);
        if(score == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }

    public List<Integer> getScores() {
        List<Integer> scores = new ArrayList<>();
        for(int i = 0; i <= 5; i++) {
            scores.add(i);
        }
        return scores;
    }

}
