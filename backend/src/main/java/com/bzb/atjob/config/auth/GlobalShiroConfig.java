package com.bzb.atjob.config.auth;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalShiroConfig {

  /**
   * 强制使用 CGlib.
   *
   * <p>引入 shiro 之后，它会把 AOP 改为使用 dynamic proxy 实现，而我们的 xxApplicationService 都没有使用接口。 在
   * ApplicationService 类上使用 @Transactional 标注后，因为 dynamic proxy 要求有接口而引发 The bean 'xxService' could
   * not be injected as a 'xxService' because it is a JDK dynamic proxy that implements 异常。 只有通过下面的
   * creator.setProxyTargetClass(true) 才能将 AOP 实现强制转变为使用 CGLib。
   *
   * <p>注：在 application.yml 中 配置 spring.aop.proxy-target-class: true 不生效；在 Application
   * 上配置 @EnableTransactionManagement(proxyTargetClass = true) 不生效；在 ApplicationService 上
   * 配置 @EnableCaching(proxyTargetClass = true) 不生效。
   */
  @Bean
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
    creator.setProxyTargetClass(true);
    return creator;
  }
}
