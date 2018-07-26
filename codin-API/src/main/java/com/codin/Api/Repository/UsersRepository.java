package com.codin.Api.Repository;

import com.codin.Api.Library.DatabaseLibrary;
import com.codin.Api.Models.Users;
import com.codin.Api.Responses.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsersRepository extends ReturnResponse {

    @Autowired
    private DatabaseLibrary dbl;

    public ReturnResponse get(Object ID) {
        addResponse("examsA", dbl.select("SELECT users.*, profiles.profileImage AS profilFotosu FROM users AS users INNER JOIN profiles AS profiles ON users.ID = profiles.userID"));
        addResponse("examsB", dbl.select("*", "users", "email = 'erhan_yil@outlook.com.tr'"));

        /*
        addResponse("examsC", dbl.callProcedure("pgetExams(1)"));
        addResponse("examsD", dbl.callProcedure("pgetExams", 1));
        addResponse("examsE", dbl.callProcedure("pgetExams", Arrays.asList(1, 2)));
        */

        addResponse("examsF", dbl.select(Users.class));
        addResponse("examsG", dbl.select(Users.class, ID));
        addResponse("examsG", "examsH", dbl.select("SELECT users.*, profiles.profileImage FROM users AS users INNER JOIN profiles AS profiles ON users.ID = profiles.userID"));

        dbl.update("UPDATE profiles SET firstName = 'Erhanssss' WHERE ID = 14"); //RETURN number of effected rows
        dbl.update("profiles", "firstName = 'Erhanssss'", "ID = 14"); //RETURN number of effected rows

        dbl.delete("DELETE FROM profiles WHERE ID > 100"); //return true false
        dbl.delete("profiles", "ID > 100"); //return true false

        addResponse("examsH", "examsI", dbl.select("SELECT users.*, profiles.profileImage FROM users AS users INNER JOIN profiles AS profiles ON users.ID = profiles.userID"));
        addResponse("examsI", "examsErhan", dbl.select("SELECT users.*, profiles.profileImage FROM users AS users INNER JOIN profiles AS profiles ON users.ID = profiles.userID"));

        return response();
    }
}
