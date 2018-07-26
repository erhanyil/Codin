package com.codin.Oauth;

import com.codin.Oauth.Library.DatabaseLibrary;
import com.codin.Oauth.Models.LinkedInProfile;
import com.codin.Oauth.Repository.LogsRepository;
import com.codin.Oauth.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private Environment env;

    @Autowired
    private DatabaseLibrary dbl;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LogsRepository logsRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userRoleQuery = env.getProperty("spring.queries.userRoleQuery");
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<Object> roles = new ArrayList<>();
        Long userID = usersRepository.findUserByEmail(name);
        UsernamePasswordAuthenticationToken token = null;
        if (userID != -1 && usersRepository.checkCredential(name, password)) {
            userRoleQuery = userRoleQuery.replace("!", "'" + name + "'");
            roles = dbl.select(userRoleQuery);
            HashMap<String, Object> s = new HashMap<String, Object>();
            Boolean isFirstLogin = logsRepository.isUserFirsLogin(userID);
            token = new UsernamePasswordAuthenticationToken(name, password, getGrantedAuthorities(roles));
            s.put("isFirstLogin", isFirstLogin);

            LinkedInProfile linkedInProfile = new LinkedInProfile();
            try {
                HashMap hashMap = (HashMap) authentication.getDetails();
                linkedInProfile = (LinkedInProfile) hashMap.get("linkedInProfile");
            } catch (Exception e) {
                linkedInProfile = null;
                System.out.println("not linkedin login");
            }
            s.put("linkedInProfile", linkedInProfile);
            token.setDetails(s);
        }
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


    public List<GrantedAuthority> getGrantedAuthorities(List<Object> roleNames) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Object role : roleNames) {
            authorities.add(new SimpleGrantedAuthority(role.toString().replace("role=", "").replace("{", "").replace("}", "")));
        }
        return authorities;
    }
}
