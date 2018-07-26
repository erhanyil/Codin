package com.codin.Oauth.Controllers;

import com.codin.Oauth.Library.CoreLibrary;
import com.codin.Oauth.Library.DatabaseLibrary;
import com.codin.Oauth.Models.Profiles;
import com.codin.Oauth.Models.RegisterForm;
import com.codin.Oauth.Models.Users;
import com.codin.Oauth.Repository.ProfilesRepository;
import com.codin.Oauth.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AccountProcessController {

    @Autowired
    private DatabaseLibrary dbl;

    @Autowired
    private CoreLibrary cl;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProfilesRepository profilesRepository;

    private String currentUser;

    //region Register
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String submit(Model model, Principal principal) throws Exception {
        model.addAttribute("title", "Register an Account");
        RegisterForm registerForm = new RegisterForm();
        model.addAttribute("registerForm", registerForm);
        model.addAttribute("firstNameError", "");
        model.addAttribute("lastNameError", "");
        model.addAttribute("emailAddressError", "");
        model.addAttribute("passwordError", "");
        model.addAttribute("passwordCError", "");
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Model processRegistration(@Valid @ModelAttribute("registerForm") RegisterForm registerForm, BindingResult result, Model model, HttpServletRequest request) {
        Users newUser = new Users();
        model.addAttribute("registerForm", registerForm);
        String activationKey = "";

        for (ObjectError errorMessage : result.getAllErrors()) {
            model.addAttribute(((FieldError) errorMessage).getField() + "Error", errorMessage.getDefaultMessage());
        }
        if (result.getErrorCount() > 0) {
            return model;
        }

        if (usersRepository.findUserByEmail(registerForm.getEmailAddress()) != -1) {
            model.addAttribute("emailAddressError", "This email already registered. Please enter different email address.");
            return model;
        }
        try {
            activationKey = cl.createLicenseKey(registerForm.getFirstName(), registerForm.getEmailAddress(), registerForm.getLastName());
            newUser.setActivationKey(activationKey);
            newUser.setPassword(cl.passwordEncoder(registerForm.getPassword()));
            newUser.setEmail(registerForm.getEmailAddress());
            newUser.setIsActive(0);
            newUser.setRoleID(2);
            newUser.setRegisteredDate(cl.getCurrentTimeStamp());
            if (usersRepository.save(newUser) != null) {
                Long userID = usersRepository.findUserByEmail(registerForm.getEmailAddress());
                Profiles newPorfile = new Profiles();
                newPorfile.setFirstName(registerForm.getFirstName());
                newPorfile.setLastName(registerForm.getLastName());
                newPorfile.setUserID(userID);
                if (profilesRepository.save(newPorfile) == null) {
                    model.addAttribute("db.errorMessage", "User Cannot saved");
                    return model;
                } else {
                    dbl.closeSession();
                }
            } else {
                model.addAttribute("db.errorMessage", "User cannot saved");
                return model;
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("db.errorMessage", "Db error!");
            return model;
        }
        String confirmationUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/auth" + "/verify?token=" + activationKey + "&email=" + newUser.getEmail();
        if (cl.sendMail(registerForm.getEmailAddress(), "Codin", "Codin - Verify Account", confirmationUrl)) {
            model.addAttribute("successMessage", "please activate your account");
            registerForm = new RegisterForm();
            model.addAttribute("registerForm", registerForm);
            model.addAttribute("title", "Successfully Registered");
        } else {
            model.addAttribute("db.errorMessage", "Mail couldn't sent");
            return model;
        }
        return model;
    }
    //endregion

    //region Verify
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token") String token, @RequestParam("email") String email) {
        Long userId = Long.valueOf(-1);
        Users newUser = new Users();
        String activationKey = "";
        if ((userId = usersRepository.findUserByEmail(email)) != -1) {
            newUser = usersRepository.getUser(userId);
            if ((activationKey = usersRepository.findAtivationKeyByEmail(userId)) != null) {
                if (activationKey.equals(token)) {
                    newUser.setIsActive(1);
                    newUser.setActivationKey(cl.createLicenseKey(newUser.getEmail(), newUser.getPassword(), newUser.getActivationKey()));
                    usersRepository.save(newUser);
                    dbl.getSession().flush();
                }
            }
        }
        dbl.closeSession();
        return "login";
    }
    //endregion

    //region Forgot Password
    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public String get() {
        return "forgot-password";
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public Model processForgotPassword(@Valid @ModelAttribute("email") String email, Model model, HttpServletRequest request) {
        Long userID = Long.valueOf(-1);
        Users newUser = new Users();
        String activationKey = cl.createLicenseKey(email, "reset-password", "codin");
        model.addAttribute("message", "Failed");
        String confirmationUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/auth" + "/reset-password?email=" + email + "&token=" + activationKey;
        if ((userID = usersRepository.findUserByEmail(email)) != -1) {
            if ((newUser = usersRepository.getUser(userID)) != null) {
                if (newUser.getIsActive() != 0) {
                    newUser.setActivationKey(activationKey);
                    if (usersRepository.save(newUser) != null) {
                        if (cl.sendMail(email, "Codin", "Codin Reset Password", confirmationUrl)) {
                            model.addAttribute("message", "Reset Password Email sent");
                        }
                    }
                } else {
                    model.addAttribute("message", "you didnt veriy your account");
                }
            }
        }
        return model;
    }
    //endregion

    //region Reset Password
    @RequestMapping(value = "/reset-password", method = RequestMethod.GET)
    public String submit(@RequestParam("token") String token, @RequestParam("email") String email, Model model) {
        currentUser = "";
        Long userID = Long.valueOf(-1);
        String activationKey = "";
        if ((userID = usersRepository.findUserByEmail(email)) != -1) {
            if ((activationKey = usersRepository.findAtivationKeyByEmail(userID)) != null) {
                if (activationKey.equals(token)) {
                    currentUser = email;
                    dbl.closeSession();
                    return "reset-password";
                }
            }
        }
        model.addAttribute("message", "Activation key expired");
        dbl.closeSession();
        return "login";
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public String processResetPassword(@Valid @ModelAttribute("password") String password) {
        Users newUser = new Users();
        Long userID = Long.valueOf(-1);
        if ((userID = usersRepository.findUserByEmail(currentUser)) != -1) {
            if ((newUser = usersRepository.getUser(userID)) != null) {
                newUser.setPassword(cl.passwordEncoder(password));
                newUser.setActivationKey(cl.createLicenseKey(newUser.getEmail(), newUser.getPassword(), newUser.getActivationKey()));
                if (usersRepository.save(newUser) != null) {
                    dbl.closeSession();
                    return "login";
                }
            }
        }
        dbl.closeSession();
        return "reset-password";
    }
    //endregion

}
