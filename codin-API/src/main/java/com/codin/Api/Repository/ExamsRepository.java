package com.codin.Api.Repository;

import com.codin.Api.Library.DatabaseLibrary;
import com.codin.Api.Responses.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExamsRepository extends ReturnResponse {

    @Autowired
    private DatabaseLibrary dbl;

    public ReturnResponse get(Object lanugageID, Object ID) {
        return response();
    }
}
