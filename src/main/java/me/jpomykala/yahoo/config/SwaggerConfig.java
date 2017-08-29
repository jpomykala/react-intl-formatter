package me.jpomykala.yahoo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Jakub Pomykala on 22/05/2017.
 * @project PlaceFlareSpring
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("All")
        .select()
        .apis(RequestHandlerSelectors.basePackage("me.jpomykala"))
        .paths(PathSelectors.any())
        .build()
        .pathMapping("/")
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    Contact contact = new Contact(
        "Jakub Pomykała",
        "https://jpomykala.me",
        "jakub.pomykala@gmail.com");
    return new ApiInfoBuilder()
        .title("ReactIntl formatter")
        .contact(contact)
        .description("jpomykala.me")
        .version("1.0")
        .build();
  }


}
