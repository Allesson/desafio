package br.com.allesson.desafio.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Value("${version}")
    private String version;
	
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
            .apis(RequestHandlerSelectors.basePackage("br.com.allesson.desafio.controller"))
            .paths(PathSelectors.regex("/.*"))
            .build().apiInfo(apiEndPointsInfo());
    }
    
    private ApiInfo apiEndPointsInfo() {
    	ApiInfo apiInfo = new ApiInfo("CLIENTE REST API", "Cliente REST API", version, "Termos do serviço", 
    			new Contact("Allesson Cavalcante", "", "allessoncavalcante@gmail.com"), 
    			"Apache License Version 2.0", "http://www.apache.org/licenses/LICENSE-2.0.html", new ArrayList<VendorExtension>());
        return apiInfo;
    }
}
