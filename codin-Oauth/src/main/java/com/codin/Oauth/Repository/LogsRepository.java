package com.codin.Oauth.Repository;

import com.codin.Oauth.Library.CoreLibrary;
import com.codin.Oauth.Library.DatabaseLibrary;
import com.codin.Oauth.Models.Logs;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class LogsRepository {

    @Autowired
    private DatabaseLibrary dbl;

    @Autowired
    private CoreLibrary cl;

    @SuppressWarnings("unchecked")
    public Boolean isUserFirsLogin(Long userID) {
        List<Logs> res1 = dbl.createCriteria(Logs.class).add(Restrictions.eq("userID", userID)).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
        return res1.size() == 0;
    }

    @SuppressWarnings("unchecked")
    public Boolean writeLogs(String logType, String logMessage, Long userID) {
        HttpServletRequest requests = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = requests.getRemoteAddr();

        Logs log = new Logs();
        log.setUserID(userID);
        log.setLogType(logType);
        log.setLogMessage(logMessage);
        log.setLogDate(cl.getCurrentTimeStamp());
        return dbl.saveOrUpdate(log) != null;
    }

}
