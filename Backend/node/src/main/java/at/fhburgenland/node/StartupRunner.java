package at.fhburgenland.node;

import at.fhburgenland.node.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class StartupRunner implements ApplicationRunner {
    @Value("${api-gateway.route}")
    private String apiGatewayRoute;
    private int retries = 2;
    private final StatusService statusService;
    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public StartupRunner(StatusService statusService, DiscoveryClient discoveryClient) {
        this.statusService = statusService;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public void run(ApplicationArguments args) {
        if(discoveryClient.getInstances("api").isEmpty()){
            log.info("No API yet available");
            return;
        }

        try {
            if(retries > 0) {
                ResponseEntity<List<Status>> data = restTemplate.exchange("http://"+apiGatewayRoute+"/api/statuses",
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });

                List<Status> status = data.getBody();

                if(status != null) {
                    status.forEach(statusService::createStatus);
                } else {
                    retries--;
                    run(args);
                }
            }
        } catch (Exception e) {
            log.info("There was an error calling the API");
            if(retries > 0) {
                retries--;
                run(args);
            }
        }
    }
}
