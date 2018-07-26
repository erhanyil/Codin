package com.codin.Oauth.Repository;

import com.codin.Oauth.Library.CoreLibrary;
import com.codin.Oauth.Library.DatabaseLibrary;
import com.codin.Oauth.Models.Users;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UsersRepository {

    @Autowired
    private DatabaseLibrary dbl;

    @Autowired
    private CoreLibrary cl;

    @SuppressWarnings("unchecked")
    public List get(Long id) {
        List res1 = dbl.select(Users.class, id);
        dbl.closeSession();
        return res1;
    }

    @SuppressWarnings("unchecked")
    public Users getUser(Long id) {
        List<Users> res1 = dbl.createCriteria(Users.class).add(Restrictions.eq("ID", id)).list();
        if (res1.size() == 1) {
            return res1.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Long findUserByEmail(String email) {
        List<Users> res1 = dbl.createCriteria(Users.class).add(Restrictions.eq("email", email)).list();
        if (res1.size() == 1) {
            return res1.get(0).getID();
        }
        dbl.closeSession();
        return Long.valueOf(-1);
    }

    @SuppressWarnings("unchecked")
    public String findAtivationKeyByEmail(Long id) {
        List<Users> res1 = dbl.createCriteria(Users.class).add(Restrictions.eq("ID", id)).list();
        if (res1.size() == 1) {
            return res1.get(0).getActivationKey();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Users save(Users users) {
        return (Users) dbl.saveOrUpdate(users);
    }

    public Boolean checkCredential(String email, String password) {
        List<Users> res1 = dbl.createCriteria(Users.class).add(Restrictions.eq("email", email)).list();
        if (res1.size() == 1) {
            return cl.passwordEqual(password, res1.get(0).getPassword());
        }
        return false;
    }

}
