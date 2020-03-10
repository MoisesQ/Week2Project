package com.projects.springboot.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfig {

  /**
   * This method is created to establish documentation of swagger.
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(swaggerApiInformation())
        .select().paths(PathSelectors.any())
        .build();
  }

  private ApiInfo swaggerApiInformation() {
    return new ApiInfoBuilder().title("Everis Bootcamp School Project")
        .description("Spring Webflux Project (Unit testing, Clean code, Checkstyle)")
        .termsOfServiceUrl("https://gitlab.com/MoisesQ")
        .contact(
            new Contact("Moisés Andrés Alejandro Quispe Herrera", "https://gitlab.com/MoisesQ", "mquispeh@everis.com"))
        .version("1.5").build();
  }



}