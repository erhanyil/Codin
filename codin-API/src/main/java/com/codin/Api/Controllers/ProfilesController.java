package com.codin.Api.Controllers;

import com.codin.Api.Models.LinkedInProfile;
import com.codin.Api.Repository.ProfilesRepository;
import com.codin.Api.Responses.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
@EnableOAuth2Resource
public class ProfilesController {

    @Autowired
    private ProfilesRepository profilesRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReturnResponse get(@PathVariable Long id) {
        return profilesRepository.get(id);
    }

    @RequestMapping(value = "firstLoginProcess/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Long firstLoginProcess(@PathVariable Long id, @RequestBody LinkedInProfile linkedInProfile) {
        return profilesRepository.firstLoginProcess(id, linkedInProfile);
    }
}
