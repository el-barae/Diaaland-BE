package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.project")
public class DiaalandApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiaalandApplication.class, args);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("L'application Spring Boot se ferme.");
        }));
	}
	
	/*@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("*")
						.allowedHeaders("*")
						.allowCredentials(false).maxAge(3600);
			}
		};
	}*/

}
