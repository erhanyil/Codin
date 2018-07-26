package com.codin.Api.Controllers;

import com.codin.Api.Models.Users;
import com.codin.Api.Repository.UsersRepository;
import com.codin.Api.Responses.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@EnableOAuth2Resource
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ReturnResponse get() {
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReturnResponse get(@PathVariable Long id) {
        return usersRepository.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Users post(@RequestBody Users users) { // id = null -> insert otherwise update
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Integer delete(@PathVariable Long id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Boolean delete() {
        return null;
    }
}
