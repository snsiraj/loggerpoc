package org.loggerpoc.loggepoc;

import jdk.jfr.Category;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  @Bean
  ModelMapper getModelMapper() {
    return new ModelMapper();
  }

}
