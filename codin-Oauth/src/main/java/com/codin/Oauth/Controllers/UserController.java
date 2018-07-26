package com.codin.Oauth.Controllers;

import com.codin.Oauth.Library.DatabaseLibrary;
import com.codin.Oauth.Models.Profiles;
import com.codin.Oauth.Repository.ProfilesRepository;
import com.codin.Oauth.Repository.UsersRepository;
import com.codin.Oauth.Responses.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@EnableResourceServer
public class UserController {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private ProfilesRepository profilesRepository;

	@Autowired
	private DatabaseLibrary dbl;
	
	@RequestMapping("/user")
	@ResponseBody
	public ReturnResponse user(Principal user) {
		Profiles userProfile = new Profiles();
		Long userID = usersRepository.findUserByEmail(user.getName());
		userProfile = profilesRepository.getProfileWith(userID);
		ReturnResponse returnResponse = new ReturnResponse();
		returnResponse.add("userAuthentication", ((OAuth2Authentication) user).getUserAuthentication());
		returnResponse.add("userProfile", userProfile);
		dbl.closeSession();
		return returnResponse;
	}
}
