package vn.edu.topica.config;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vn.edu.topica.authentication.config.AuditorAwareImpl;

@Configuration(value = "myConfiguration")
@ComponentScan({
    "vn.edu.topica.authentication.auth",
    "vn.edu.topica.authentication.controller",
    "vn.edu.topica.authentication.service",
    "vn.edu.topica.controller",
    "vn.edu.topica.service"
})
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories({
    "vn.edu.topica.authentication.repository",
    "vn.edu.topica.repository"
})
@EnableTransactionManagement
@EntityScan(basePackages = {
    "vn.edu.topica.authentication.model",
    "vn.edu.topica.model"})
@Slf4j
public class ModuleConfiguration {

  @Bean
  AuditorAware<Long> auditorProvider() {
    return new AuditorAwareImpl();
  }

  @PostConstruct
  public void postConstruct() {
    log.info("init vn.edu.topica.config complete");
  }
}