package com.codin.Api.Repository;

import com.codin.Api.Library.DatabaseLibrary;
import com.codin.Api.Models.LinkedInProfile;
import com.codin.Api.Responses.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProfilesRepository extends ReturnResponse {

    @Autowired
    private DatabaseLibrary dbl;

    @SuppressWarnings("unchecked")
    public ReturnResponse get(Long ID) {
        addResponse("profile", dbl.select("*", "profiles", "id = " + ID.toString()));
        dbl.closeSession();
        return response();
    }

    public Long firstLoginProcess(Long userID, LinkedInProfile linkedInProfile) {
        return Long.valueOf(-1); //TODO Save linkedIn Profile data to database
    }
}
