package org.myapp.eventsourcingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(AnalyticsBinding.class)
public class EventSourcingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventSourcingServiceApplication.class, args);
    }
}

