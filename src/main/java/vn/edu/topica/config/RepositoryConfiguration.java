package vn.edu.topica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import vn.edu.topica.model.Query;
import vn.edu.topica.model.ReportStructure;

@Configuration("customRpositoryConfiguration")
public class RepositoryConfiguration extends RepositoryRestConfigurerAdapter {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.exposeIdsFor(Query.class);
    config.exposeIdsFor(ReportStructure.class);
  }
}
