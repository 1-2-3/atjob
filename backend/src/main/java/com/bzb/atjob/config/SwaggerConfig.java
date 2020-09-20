package com.bzb.atjob.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  /**
   * 创建Swagger文档.
   *
   * @return
   */
  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo("全部"))
        .groupName("全部")
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        // 为Swagger添加全局身份认证输入窗体
        .securitySchemes(securitySchemes())
        .securityContexts(securityContexts());
    // .securitySchemes(Lists.newArrayList(apiKey()))
    // .securityContexts(Lists.newArrayList(securityContext()));
  }

  private ApiInfo apiInfo(String name) {
    return new ApiInfoBuilder().title("Web API 文档").description(name).version("0.0.1").build();
  }

  private List<ApiKey> securitySchemes() {
    List<ApiKey> result = new ArrayList<ApiKey>();
    result.add(new ApiKey("Authorization", "Authorization", "header"));
    return result;
  }

  private List<SecurityContext> securityContexts() {
    List<SecurityContext> result = new ArrayList<SecurityContext>();
    result.add(
        SecurityContext.builder()
            .securityReferences(defaultAuth())
            // .forPaths(PathSelectors.regex("^(?!auth).*$"))
            .build());
    return result;
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    List<SecurityReference> result = new ArrayList<SecurityReference>();
    result.add(new SecurityReference("Authorization", authorizationScopes));
    return result;
  }
}
