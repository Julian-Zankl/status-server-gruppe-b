package at.fhburgenland.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for starting the gateway application
 */
@SpringBootApplication
public class GatewayApplication {
	/**
	 * Main method to start the application
	 * @param args Arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
