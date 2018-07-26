package com.codin.Oauth.Repository;

import com.codin.Oauth.Library.DatabaseLibrary;
import com.codin.Oauth.Models.Profiles;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProfilesRepository {

    @Autowired
    private DatabaseLibrary dbl;

    @SuppressWarnings("unchecked")
    public List get(Long id) {
        List res1 = dbl.select(Profiles.class, id);
        dbl.closeSession();
        return res1;
    }

    @SuppressWarnings("unchecked")
    public Profiles getProfileWith(Long userID) {
        List<Profiles> res1 = dbl.createCriteria(Profiles.class).add(Restrictions.eq("userID", userID)).list();
        dbl.closeSession();
        if (res1.size() == 1) {
            return res1.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Long findUserByEmail(String email) {
        List<Profiles> res1 = dbl.createCriteria(Profiles.class).add(Restrictions.eq("email", email)).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
        if (res1.size() == 1) {
            return res1.get(0).getID();
        }
        return Long.valueOf(-1);
    }

    @SuppressWarnings("unchecked")
    public Profiles save(Profiles profile) {
        return (Profiles) dbl.saveOrUpdate(profile);
    }

}
