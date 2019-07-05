package com.hzq.config;

import com.hzq.handler.TokenClearLogoutHandler;
import com.hzq.handler.TokenRefreshSuccessHandler;
import com.hzq.handler.UserLoginSuccessHandler;
import com.hzq.security.MyAccessDeniedHandler;
import com.hzq.security.TokenAuthenticationProvider;
import com.hzq.security.UserInfoService;
import com.hzq.security.configurer.TokenLoginConfigurer;
import com.hzq.security.configurer.UserLoginConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/images/**").permitAll()//设置静态资源无权限限制
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()
                .antMatchers("/community/listall","/user/registry").permitAll()//指定可以直接访问的url
                .antMatchers("/file/upload","/file/uploads","/user/login").permitAll()//指定可以直接访问的url
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers(CorsUtils::isCorsRequest).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().disable()
                //登录请求的过滤
                .apply(new UserLoginConfigurer<>()).loginSuccessHandler(userLoginSuccessHandler())
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                //token请求的过滤
                .apply(new TokenLoginConfigurer<>())
                .tokenValidSuccessHandler(tokenRefreshSuccessHandler())
                .permissiveRequestUrls("/logout","/community/listall","/images/**","/user/registry","/swagger-resources/**","/swagger-ui.html")
                .permissiveRequestUrls("/webjars/**","/v2/api-docs","/configuration/ui","/configuration/security","/file/upload","/file/uploads","/user/login")
                .and()
                //登出的过滤器
                .logout()
                .addLogoutHandler(tokenClearLogoutHandler())
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .and()
                .sessionManagement().disable()
                .cors().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/user/registry").antMatchers("/user/hello");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider()).authenticationProvider(tokenAuthenticationProvider());
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return new UserInfoService();
    }



    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean("daoAuthenticationProvider")
    protected AuthenticationProvider daoAuthenticationProvider() throws Exception{
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setPasswordEncoder(passwordEncoder());
        daoProvider.setUserDetailsService(userInfoService());
        return daoProvider;
    }
    @Bean("tokenAuthenticationProvider")
    protected AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(userInfoService());
    }

    @Bean("userInfoService")
    protected UserInfoService userInfoService() {
        return new UserInfoService();
    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected UserLoginSuccessHandler userLoginSuccessHandler() {
        return new UserLoginSuccessHandler(userInfoService());
    }

    @Bean
    protected TokenRefreshSuccessHandler tokenRefreshSuccessHandler() {
        return new TokenRefreshSuccessHandler();
    }

    @Bean
    protected TokenClearLogoutHandler tokenClearLogoutHandler() {
        return new TokenClearLogoutHandler(userInfoService());
    }

    @Bean
    protected MyAccessDeniedHandler accessDeniedHandler(){
        return new MyAccessDeniedHandler();
    }

}
