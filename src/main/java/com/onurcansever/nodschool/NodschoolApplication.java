package com.onurcansever.nodschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.onurcansever.nodschool.repository")
@EntityScan("com.onurcansever.nodschool.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class NodschoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(NodschoolApplication.class, args);
	}

}
