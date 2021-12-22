package com.epam.hospital.config;

import com.epam.hospital.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class ApplicationConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    PatientDtoService patientDtoService() {
        String url = String.format("%s://%s:%d/patients", protocol, host, port);
        return new PatientDtoServiceRest(url, restTemplate());
    }

    @Bean
    NurseService nurseService() {
        String url = String.format("%s://%s:%d/nurses", protocol, host, port);
        return new NurseServiceRest(url, restTemplate());
    }

    @Bean
    PatientService patientService() {
        String url = String.format("%s://%s:%d/patients", protocol, host, port);
        return new PatientServiceRest(url, restTemplate());
    }
}
