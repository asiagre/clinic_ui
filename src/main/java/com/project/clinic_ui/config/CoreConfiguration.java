package com.project.clinic_ui.config;

import com.project.clinic_ui.clinic.ClinicClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoreConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ClinicClient clinicClient() {
        return new ClinicClient();
    }
}
