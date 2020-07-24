package com.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories
public class ToDoApplication extends SpringBootServletInitializer {

	public ToDoApplication() {
	    super();
	    setRegisterErrorPageFilter(false);
	}
	public static void main(String[] args) {
		SpringApplication.run(ToDoApplication.class,args);

	}

}
