package com.NoteSphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyNoteSphere {

	public static void main(String[] args) {
		SpringApplication.run(MyNoteSphere.class, args);
	}

}
