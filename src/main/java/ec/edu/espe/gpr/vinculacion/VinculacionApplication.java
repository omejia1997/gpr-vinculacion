package ec.edu.espe.gpr.vinculacion;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VinculacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(VinculacionApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Guayaquil"));
	}

}
