package kdr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration enables API documentation on endpoints such as:
 * http://localhost:8080/swagger-ui.html
 *and
 * http://localhost:8080/v2/api-docs
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kdr.rest"))
                //.paths(regex("/price*"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo= new ApiInfo(
                "Black Scholes Pricing API",
                "REST interface for a Black Scholes calculator",
                "1.0",
                "Terms of Service",
                new Contact("Kenneth Roper", "", "kennethdroper@googlemail.com"),
                "License Pending",
                "License Pending");
        return apiInfo;
    }
}
