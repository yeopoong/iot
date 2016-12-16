package kyun.iot.manager;

import javax.annotation.Resource;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import kyun.iot.framework.rest.RestAuthenticationEntryPoint;
import kyun.iot.framework.rest.RestLoginFailureHandler;
import kyun.iot.framework.rest.RestLoginSuccessHandler;
import kyun.iot.framework.rest.RestLogoutSuccessHandler;

//@Configuration
//@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Resource
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    RestLoginSuccessHandler restLoginSuccessHandler;

    @Resource
    RestLogoutSuccessHandler restLogoutSuccessHandler;

    @Resource
    RestLoginFailureHandler restLoginFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
            .formLogin()
                .loginProcessingUrl("/auth/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(restLoginSuccessHandler)
                .failureHandler(restLoginFailureHandler)
                .and()
            .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler(restLogoutSuccessHandler) 
                .and()
            .authorizeRequests().anyRequest().authenticated();
    }
}
