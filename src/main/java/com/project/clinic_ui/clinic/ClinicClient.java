package com.project.clinic_ui.clinic;

import com.project.clinic_ui.domain.*;
import com.project.clinic_ui.dto.AppointmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClinicClient {

    @Value("${clinic.api.endpoint.prod}")
    private String clinicApiEndpoint;

    @Autowired
    private RestTemplate restTemplate;

    public List<Doctor> getDoctors() {
        Doctor[] response = restTemplate
                .getForObject(clinicApiEndpoint + "/doctors", Doctor[].class);
        if(response != null) {
            return Arrays.asList(response);
        } else {
            return new ArrayList<>();
        }
    }

    public AppointmentDto makeAppointment(AppointmentDto appointmentDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/doctors/" + appointmentDto.getDoctorId() + "/appointments")
                .build().encode().toUri();
        AppointmentDto response = restTemplate
                .postForObject(url, appointmentDto, AppointmentDto.class);
        return response;
    }

    public List<Doctor> getDoctorsByLastname(String lastname) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/doctors/lastname")
                .queryParam("lastname", lastname)
                .build().encode().toUri();
        return getListOf(url);
    }

    public List<Doctor> getDoctorBySpecialization(String specialization) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/doctors/specialization")
                .queryParam("specialization", specialization)
                .build().encode().toUri();
        return getListOf(url);
    }

    private List<Doctor> getListOf(URI url) {
        Doctor[] response = restTemplate
                .getForObject(url, Doctor[].class);
        if(response != null) {
            return Arrays.asList(response);
        } else {
            return new ArrayList<>();
        }
    }

    public Patient createPatient(Patient patient) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/patients")
                .build().encode().toUri();
        Patient response = restTemplate
                .postForObject(url, patient, Patient.class);
        return response;
    }

    public Doctor createDoctor(Doctor doctor) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/doctors")
                .build().encode().toUri();
        Doctor response = restTemplate
                .postForObject(url, doctor, Doctor.class);
        return response;
    }

    public void editDoctor(Doctor doctor) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/doctors/" + doctor.getId())
                .build().encode().toUri();
        restTemplate.put(url, doctor);
    }

    public void deleteDoctor(Doctor doctor) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/doctors/" + doctor.getId())
                .build().encode().toUri();
        restTemplate.delete(url);
    }

    public void addSlot(Slot slot) {
        LocalDateTime slotDate = LocalDateTime.of(slot.getYear(), slot.getMonth(), slot.getDay(), slot.getHour(), slot.getMinute());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String date = slotDate.format(formatter);
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/doctors/" + slot.getDoctor().getId() + "/slots")
                .queryParam("slot", date)
                .build().encode().toUri();
        restTemplate.put(url, null);
    }

    public void addScore(Score score) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/doctors/" + score.getDoctor().getId() + "/scores")
                .queryParam("score", score.getScore())
                .build().encode().toUri();
        restTemplate.put(url, null);
    }

    public List<Appointment> getPatientAppointments(Patient patient) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/patients/" + patient.getId() + "/appointments")
                .build().encode().toUri();
        AppointmentDto[] response = restTemplate.getForObject(url, AppointmentDto[].class);
        List<AppointmentDto> appointmentDtos = Arrays.asList(response);
        List<Appointment> appointments = appointmentDtos.stream()
                .map(appointmentDto -> new Appointment(appointmentDto.getId(), getDoctorById(appointmentDto.getDoctorId()), patient, appointmentDto.getVisitDate()))
                .collect(Collectors.toList());
        return appointments;
    }

    private Doctor getDoctorById(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/doctors/" + id)
                .build().encode().toUri();
        Doctor response = restTemplate.getForObject(url, Doctor.class);
        return response;
    }

    public void editAppointment(Appointment appointment, LocalDateTime slot) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/doctors/" + appointment.getDoctor().getId() + "/appointments/" + appointment.getId())
                .queryParam("newTime", appointment.getSlot())
                .build().encode().toUri();
        restTemplate.put(url, null);
    }

    public void deleteAppointment(Appointment appointment) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/patients/" + appointment.getPatient().getId() + "/appointments/" + appointment.getId())
                .build().encode().toUri();
        restTemplate.delete(url);
    }

    public List<Patient> getPatients() {
        Patient[] response = restTemplate
                .getForObject(clinicApiEndpoint + "/patients", Patient[].class);
        if(response != null) {
            return Arrays.asList(response);
        } else {
            return new ArrayList<>();
        }
    }
}
