package com.codin.Client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.security.oauth2.sso.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso
@EnableZuulProxy
public class ClientApplication extends SpringBootServletInitializer {

    private static Class<ClientApplication> applicationClass = ClientApplication.class;
    
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}
