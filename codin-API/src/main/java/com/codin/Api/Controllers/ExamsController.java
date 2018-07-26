package com.codin.Api.Controllers;

import com.codin.Api.Repository.ExamsRepository;
import com.codin.Api.Responses.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exams")
@EnableOAuth2Resource
public class ExamsController {

    @Autowired
    private ExamsRepository examsRepository;

    @RequestMapping(value = "{languageID}/{id}", method = RequestMethod.GET)
    public ReturnResponse get(@PathVariable Long languageID, @PathVariable Long id) {
        return examsRepository.get(languageID, id);
    }
}
