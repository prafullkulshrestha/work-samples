/**
 * 
 */
package com.service.discovery.example.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author prafullkulshrestha
 *
 */
@Configuration
@EnableSwagger2
public class ApplicationConfiguration  {

	private List<ResponseMessage> responseMessages = new ArrayList<>();

	public ApplicationConfiguration() {
		this.responseMessages.add(new ResponseMessageBuilder().code(500).message("500 message")
				.responseModel(new ModelRef("string")).build());
		this.responseMessages.add(new ResponseMessageBuilder().code(400).message("Bad request!").build());
		this.responseMessages.add(new ResponseMessageBuilder().code(404).message("Not found!").build());
		this.responseMessages.add(new ResponseMessageBuilder().code(429).message("Too many requests!").build());
		this.responseMessages.add(new ResponseMessageBuilder().code(200).message("Ok").build());
	}

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.service.discovery.example.api.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo()).useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, this.responseMessages);
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Service discovery example REST API",
				"The project exposes the APIs for showing the service discovery, by reading the employer api details "
				+ "from consul, disovering the employer-api service and calling one of its endpoint",
				"API TOS", "Terms of service",
				new Contact("Prafull Kulshrestha", "www.example.com", "prafull.kulshrestha@gmail.com"),
				"License of API", "API license URL", Collections.emptyList());
	}
	
}
