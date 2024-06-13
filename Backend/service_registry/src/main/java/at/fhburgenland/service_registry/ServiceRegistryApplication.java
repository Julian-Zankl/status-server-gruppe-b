package at.fhburgenland.service_registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Main class for the service registry
 */
@EnableEurekaServer
@SpringBootApplication
public class ServiceRegistryApplication {
	/**
	 * Main method to start the application
	 * @param args Arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistryApplication.class, args);
	}

}
