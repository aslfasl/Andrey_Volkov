package com.Volkov;

import com.Volkov.service.WhatTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableSwagger2
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		WhatTime whatTime = applicationContext.getBean(WhatTime.class);
		whatTime.isNow();
	}

	@Bean
	public Docket swaggerConfig() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select() // API selector builder
//				.paths(PathSelectors.ant("/cars/*"))
				.paths(PathSelectors.ant("/drivers/*"))
				.apis(RequestHandlerSelectors.basePackage("com.Volkov"))
				.build();
	}
}
