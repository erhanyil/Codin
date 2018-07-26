package com.codin.Oauth.Controllers;

import com.codin.Oauth.Library.CoreLibrary;
import com.codin.Oauth.Library.DatabaseLibrary;
import com.codin.Oauth.Models.LinkedInProfile;
import com.codin.Oauth.Models.Profiles;
import com.codin.Oauth.Models.Users;
import com.codin.Oauth.Repository.LogsRepository;
import com.codin.Oauth.Repository.ProfilesRepository;
import com.codin.Oauth.Repository.UsersRepository;
import com.codin.Oauth.Web.LinkedInConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.management.MalformedObjectNameException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/linkedIn")
public class LinkedInController {

    @Autowired
    @Qualifier("authenticationManagerBean")
    protected AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProfilesRepository profilesRepository;

    @Autowired
    private LogsRepository logsRepository;

    @Autowired
    private DatabaseLibrary dbl;

    @Autowired
    private CoreLibrary cl;

    @RequestMapping(method = RequestMethod.GET, params = "code")
    public ModelAndView submit(HttpSession session, HttpServletRequest request, @RequestParam String code) throws Exception {
        try {
            LinkedInConnection obj_Post = new LinkedInConnection();
            Users newUser = new Users();
            String activationKey = "";
            Long userID = Long.valueOf(-1);

            LinkedInProfile linkedInProfile = obj_Post.sendPost(code);
            userID = usersRepository.findUserByEmail(linkedInProfile.getEmailAddress());
            if (userID == -1) {
                newUser.setRegisteredDate(cl.getCurrentTimeStamp());
                activationKey = cl.createLicenseKey(linkedInProfile.getEmailAddress(), linkedInProfile.getId(), linkedInProfile.getFormattedName());
                newUser.setActivationKey(activationKey);
                newUser.setPassword(cl.passwordEncoder(activationKey));
                newUser.setEmail(linkedInProfile.getEmailAddress());
                newUser.setIsActive(1);
                newUser.setRoleID(2);
                if (usersRepository.save(newUser) != null) {
                    Profiles newPorfile = new Profiles();
                    newPorfile.setUserID(newUser.getID());
                    newPorfile.setFirstName(linkedInProfile.getFirstName());
                    newPorfile.setLastName(linkedInProfile.getLastName());
                    newPorfile.setHeadLine(linkedInProfile.getHeadline());
                    newPorfile.setSummary(linkedInProfile.getSummary());
                    newPorfile.setProfileImage(linkedInProfile.getPictureUrlsFirst());
                    if (profilesRepository.save(newPorfile) != null) {
                        authenticateUserAndSetSession(newUser.getEmail(), newUser.getActivationKey(), request, linkedInProfile);
                    } else {
                        dbl.delete("DELETE FROM users WHERE id = " + newUser.getID());
                        dbl.closeSession();
                        return new ModelAndView(new RedirectView("login", true));
                    }
                }
            } else {
                newUser = usersRepository.getUser(userID);
                authenticateUserAndSetSession(newUser.getEmail(), newUser.getActivationKey(), request, linkedInProfile);
            }
            dbl.closeSession();
            logsRepository.writeLogs("Login", "Login Success - LinkedIn", userID);
            return new ModelAndView(new RedirectView("http://localhost:8001/", true));
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView(new RedirectView("login", true));
        }
    }

    @RequestMapping(method = RequestMethod.GET, params = "error")
    public ModelAndView error(HttpSession session, HttpServletRequest request, @RequestParam String error) throws Exception {
        return new ModelAndView(new RedirectView("login", true));
    }

    private void authenticateUserAndSetSession(String username, String password, HttpServletRequest request, Object detail) throws MalformedObjectNameException {
        request.getSession();
        HashMap<String, Object> s = new HashMap<String, Object>();
        s.put("linkedInProfile", detail);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, getGrantedAuthorities("USER"));
        token.setDetails(s);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

    public List<GrantedAuthority> getGrantedAuthorities(String roleName) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(roleName));
        return authorities;
    }
}
