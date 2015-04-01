package eu.hinsch.spring.boot.actuator.endpointlist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lh on 01/04/15.
 */
@Configuration
public class EndpointListAutoconfiguration {

    @Bean
    public EndpointListEndpoint endpointListEndpoint() {
        return new EndpointListEndpoint();
    }
}
