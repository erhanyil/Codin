package com.codin.Client.Web;

import com.codin.Client.Handler.CustomLogoutSuccessHandler;
import com.codin.Client.Utils.CsrfHeaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.sso.OAuth2SsoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class SecurityConfiguration extends OAuth2SsoConfigurerAdapter {

    @Autowired
    CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    public void match(RequestMatchers matchers) {
        matchers.anyRequest();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.logout().logoutUrl("/logout").logoutSuccessHandler(customLogoutSuccessHandler).invalidateHttpSession(true)
                .permitAll();
        http.authorizeRequests().antMatchers("/Controllers/**", "/Repository/**", "/Views/**", "/index.html", "/home.html", "/", "/**", "/Lib/**")
                .permitAll().anyRequest().authenticated()
                .and().csrf().csrfTokenRepository(CsrfHeaderFilter.csrfTokenRepository())
                .and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }


}
