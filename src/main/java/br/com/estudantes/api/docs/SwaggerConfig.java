package br.com.estudantes.api.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiDoc() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage("br.com.api.controller"))
			.paths(regex("/v1.*"))
			.build()
			.globalOperationParameters(Collections.singletonList(new ParameterBuilder()
					.name("Authorization")
					.description("Bearer Token")
					.modelRef(new ModelRef("string"))
					.parameterType("header")
					.required(true)
					.build()))
			.apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("API - Estudantes")
				.description("Endpoints para manipulação e gerenciamento de dados de estudantes")
				.version("1.0")
				.contact(new Contact("Deivison", "http://site.com.br", "deivisonoliveira.info@gmail.com"))
				.license("Apache License v.2.0")
				.licenseUrl("")
				.build();
	}
}
