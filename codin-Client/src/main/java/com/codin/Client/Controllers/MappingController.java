package com.codin.Client.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@RestController
public class MappingController {

    @RequestMapping("/")
    @ResponseBody
    public ModelAndView user(Principal user) {
        return user == null ? new ModelAndView(new RedirectView("login", true)) : new ModelAndView(new RedirectView("index", true));
    }
}
