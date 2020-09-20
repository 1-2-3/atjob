package com.bzb.atjob.config.auth;

import com.bzb.atjob.common.auth.JwtFilter;
import java.util.LinkedHashMap;
import javax.servlet.Filter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalShiroConfig {
  /** 创建 ShiroFilter. */
  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    // 设置 securityManager
    shiroFilterFactoryBean.setSecurityManager(securityManager);

    // 在 Shiro过滤器链上加入 JWTFilter
    LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
    filters.put("jwt", new JwtFilter());
    shiroFilterFactoryBean.setFilters(filters);

    LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
    // 所有请求都要经过 jwt过滤器
    filterChainDefinitionMap.put("/**", "jwt");

    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    return shiroFilterFactoryBean;
  }

  /**
   * 创建 SecurityManager.
   *
   * <p>注意：返回值必须是 SessionsSecurityManager 才能顶替 ShiroAutoConfiguration
   * 中被标记为 @ConditionalOnMissingBean 的同名配置。否则会报 `The bean 'securityManager', defined in class path
   * resource [org/apache/shiro/spring/boot/autoconfigure /ShiroAutoConfiguration.class], could not
   * be registered.` 的异常。
   */
  @Bean
  public SessionsSecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    // 配置 SecurityManager，并注入 shiroRealm
    securityManager.setRealm(shiroRealm());
    return securityManager;
  }

  /**
   * 创建自定义实现的 Realm.
   *
   * @return
   */
  @Bean
  public ShiroRealm shiroRealm() {
    // 配置 Realm
    return new ShiroRealm();
  }

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
