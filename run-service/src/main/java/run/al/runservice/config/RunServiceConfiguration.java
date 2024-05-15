package run.al.runservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RunServiceConfiguration {

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }

}
