package edu.kantseryk.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/*
  @author Alona
  @project project
  @class AuditionConfiguration
  @version 1.0.0
  @since 07.05.2025 - 21.19
*/

@EnableMongoAuditing
@Configuration
public class AuditionConfiguration {

    @Bean
    public AuditorAwareImpl auditorAware() {
        return new AuditorAwareImpl();
    }

}