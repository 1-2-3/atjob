package com.bzb.atjob;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class AtjobApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtjobApplication.class, args);
	}

}
