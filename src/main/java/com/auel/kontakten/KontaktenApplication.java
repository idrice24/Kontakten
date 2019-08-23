package com.auel.kontakten;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;

//import com.auel.kontakten.web.controller.KontaktPageController;

@SpringBootApplication
//@ComponentScan(basePackageClasses=KontaktPageController.class)
public class KontaktenApplication {

	public static void main(String[] args) {
		SpringApplication.run(KontaktenApplication.class, args);
	}

}
