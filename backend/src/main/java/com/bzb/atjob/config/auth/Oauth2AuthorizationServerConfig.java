// package com.bzb.atjob.config.auth;

// import com.bzb.atjob.app.auth.core.service.UserService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import
// org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
// import
// org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
// import
// org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
// import
// org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
// import
// org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
// import org.springframework.security.oauth2.provider.token.TokenStore;
// import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

// @Configuration
// @RequiredArgsConstructor
// @EnableAuthorizationServer
// public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//   //   private final SysClientDetailsService sysClientDetailsService;
//   //   private final SysUserService sysUserService;
//   private final TokenStore tokenStore;
//   private final AuthenticationManager authenticationManager;
//   private final JwtAccessTokenConverter jwtAccessTokenConverter;
//   private final UserService userService;

//   @Override
//   public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//     endpoints
//         .authenticationManager(authenticationManager)
//         .userDetailsService(userService)
//         .tokenStore(tokenStore)
//         .accessTokenConverter(jwtAccessTokenConverter);
//   }

//   @Override
//   public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//     // 从数据库读取我们自定义的客户端信息
//     // clients.withClientDetails(sysClientDetailsService);
//   }

//   @Override
//   public void configure(AuthorizationServerSecurityConfigurer security) {
//     security
//         // 获取 token key 需要进行 basic 认证客户端信息
//         .tokenKeyAccess("isAuthenticated()")
//         // 获取 token 信息同样需要 basic 认证客户端信息
//         .checkTokenAccess("isAuthenticated()");
//   }
// }
