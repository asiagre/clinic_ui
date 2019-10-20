package com.project.clinic_ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.Doc;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ClinicClient {

    @Value("${clinic.api.endpoint.prod}")
    private String clinicApiEndpoint = "http://localhost:8080/v1/clinic";

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
        Doctor[] response = restTemplate
                .getForObject(url, Doctor[].class);
        if(response != null) {
            return Arrays.asList(response);
        } else {
            return new ArrayList<>();
        }
    }

    public List<Doctor> getDoctorBySpecialization(String specialization) {
        URI url = UriComponentsBuilder.fromHttpUrl(clinicApiEndpoint + "/doctors/specialization")
                .queryParam("specialization", specialization)
                .build().encode().toUri();
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
}
