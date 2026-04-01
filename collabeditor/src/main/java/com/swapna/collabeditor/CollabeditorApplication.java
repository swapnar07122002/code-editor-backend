package com.swapna.collabeditor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CollabeditorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollabeditorApplication.class, args);
	}

}
