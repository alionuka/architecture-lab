package edu.kantseryk.project.config;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
/*
  @author Alona
  @project project
  @class AuditorAwareImpl
  @version 1.0.0
  @since 07.05.2025 - 21.23
*/

    public class AuditorAwareImpl implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            //return Optional.of("admin");
            return Optional.of(System.getProperty("user.name"));
        }
    }
