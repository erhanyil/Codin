package com.codin.Oauth.Handler;

import com.codin.Oauth.Repository.LogsRepository;
import com.codin.Oauth.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AuthenticationLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LogsRepository logsRepository;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = "http://localhost:8001/index";
        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }

        Long userID = usersRepository.findUserByEmail(authentication.getName());
        if (logsRepository.isUserFirsLogin(userID)) {
            //TODO PUT LOGIC FOR FIRST LOGIN
        }
        logsRepository.writeLogs("Login", "Login Succes", userID);
        response.sendRedirect(targetUrl);
        //redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
