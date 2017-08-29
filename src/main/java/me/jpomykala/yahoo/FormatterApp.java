package me.jpomykala.yahoo;

import me.jpomykala.yahoo.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SwaggerConfig.class)
public class FormatterApp {

	public static void main(String[] args) {
		SpringApplication.run(FormatterApp.class, args);
	}
}
