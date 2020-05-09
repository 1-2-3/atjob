package com.bzb.atjob.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo("全部")).groupName("全部").select()
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
        // .securitySchemes(Lists.newArrayList(apiKey()))
        // .securityContexts(Lists.newArrayList(securityContext()));
    }

    private ApiInfo apiInfo(String name) {
        return new ApiInfoBuilder().title("Web API 文档").description(name).version("0.0.1").build();
    }
}