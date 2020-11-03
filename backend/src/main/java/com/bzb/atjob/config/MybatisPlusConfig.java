package com.bzb.atjob.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.bzb.atjob.app.**.mapper")
public class MybatisPlusConfig {

  /** 分页插件. */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    // 开启 count 的 join 优化,只针对 left join !!!
    return new PaginationInterceptor()
        .setCountSqlParser(new JsqlParserCountOptimize(true))
        .setLimit(-1);
  }
}
