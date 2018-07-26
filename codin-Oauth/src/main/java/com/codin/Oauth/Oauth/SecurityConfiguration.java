package com.codin.Oauth.Oauth;

import com.codin.Oauth.CustomAuthenticationProvider;
import com.codin.Oauth.Handler.AuthenticationLoginSuccessHandler;
import com.codin.Oauth.Handler.AuthenticationLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@Order(-10)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthenticationLogoutSuccessHandler authenticationLogoutSuccessHandler;
    @Autowired
    AuthenticationLoginSuccessHandler authenticationLoginSuccessHandler;

    @Value("${spring.queries.usersQuery}")
    private String usersQuery;
    @Value("${spring.queries.rolesQuery}")
    private String rolesQuery;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.csrf().disable();
        //http.formLogin().loginPage("/login").successHandler(customLoginSuccessHandler).permitAll();
        http.formLogin().loginPage("/login").successHandler(authenticationLoginSuccessHandler).permitAll();
        http.requestMatchers().antMatchers("/login", "/Oauth/authorize", "/Oauth/confirm_access", "/register", "/forgot-password", "/reset-password", "/verify", "/linkedIn");
        http.authorizeRequests().antMatchers("/login", "/register", "/forgot-password", "/reset-password", "/verify", "/linkedIn").permitAll().anyRequest().authenticated();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login").logoutSuccessHandler(authenticationLogoutSuccessHandler).invalidateHttpSession(true).permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/img/**");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/register");
        web.ignoring().antMatchers("/forgot-password");
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(usersQuery)
                .passwordEncoder(bCryptPasswordEncoder)
                .authoritiesByUsernameQuery(rolesQuery);
    }
}
