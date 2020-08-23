package com.bzb.atjob.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
// @EnableConfigurationProperties(CustomConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 禁用 csrf：引入 spring-boot-starter-security 后会自动启用 csrf，导致所有非 get 请求被阻止，
    //           由于此后端仅用作提供 web api，所以可以禁用 csrf
    http.cors().and().csrf().disable();
  }
}
