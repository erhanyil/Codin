package com.codin.Oauth.Library;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class DatabaseLibrary {

    @PersistenceContext
    private EntityManager entityManager;

    public DatabaseLibrary() {
    }

    //region SESSION

    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void closeSession(Session session) {
        session.close();
    }

    public void closeSession() {
        entityManager.close();
    }

    //endregion

    //region PROCEDURE

    public List<Object> callProcedure(String procedureName) {
        Session session = getSession();
        List<Object> result = session
                .createSQLQuery("CALL " + procedureName + "()")
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                .list();
        return result;
    }

    public List<Object> callProcedure(String procedureName, Object parameter) {
        Session session = getSession();
        List<Object> result = session
                .createSQLQuery("CALL " + procedureName + "(:parameter)")
                .setParameter("parameter", parameter)
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                .list();
        return result;
    }

    /**
     * @param procedureName Procedure Name
     * @param parameter     Parameter - Cannot be contains '(' and ')'
     * @return
     */
    public List<Object> callProcedure(String procedureName, String parameter) {
        Session session = getSession();
        List<Object> result = session
                .createSQLQuery("CALL " + procedureName + "(" + parameter + ")")
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                .list();
        return result;
    }

    public List<Object> callProcedure(String procedureName, List<Object> parameters) {
        Session session = getSession();
        String query = "(";
        Integer count = 0;
        for (Object parameter : parameters) {
            query += ", :parameter" + count.toString() + " ";
            count++;
        }
        query += ")";
        query = query.replace("(,", "(");
        count = 0;
        Query _q = session.createSQLQuery("CALL " + procedureName + query);
        for (Object parameter : parameters) {
            _q.setParameter("parameter" + count.toString(), parameter);
            count++;
        }

        List<Object> result = _q
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                .list();
        return result;
    }

    public List<Object> callProcedure(String procedureName, Class entity, Object parameter) {
        Session session = getSession();
        List<Object> result = session
                .createSQLQuery("CALL " + procedureName + "(:parameter)")
                .addEntity(entity)
                .setParameter("parameter", parameter)
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                .list();
        return result;
    }

    public List<Object> callProcedure(String procedureName, Class entity, List<Object> parameters) {
        Session session = getSession();
        String query = "(";
        Integer count = 0;
        for (Object parameter : parameters) {
            query += ", :parameter" + count.toString() + " ";
            count++;
        }
        query += ")";
        query = query.replace("(,", "(");
        count = 0;
        Query _q = session.createSQLQuery("CALL " + procedureName + query).addEntity(entity);
        for (Object parameter : parameters) {
            _q.setParameter("parameter" + count.toString(), parameter);
            count++;
        }

        List<Object> result = _q
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                .list();
        return result;
    }

    //endregion

    //region CREATE CRITERIA / SQL

    public Criteria createCriteria(Class entity) {
        return getSession().createCriteria(entity);
    }

    public Query createSQLQuery(String sql) {
        return getSession().createSQLQuery(sql);
    }

    public Query createQuery(String sql) {
        return getSession().createQuery(sql);
    }

    //endregion

    //region SELECT

    public List<Object> select(Class entity) {
        return getSession().createCriteria(entity).list();
    }

    public List<Object> select(String sql) {
        return createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                .list();
    }

    public List<Object> select(Class entity, Long id) {
        return getSession().createCriteria(entity).add(Restrictions.eq("id", id)).list();
    }

    //endregion

    //region SAVE/UPDATE

    public Object saveOrUpdate(Object object) {
        try {
            Session session = getSession();
            session.saveOrUpdate(object);
            session.flush();
            //session.close();
            return object;
        } catch (Exception e) {
            return null;
        }
    }

    //endregion

    //region DELETE

    public boolean delete(Class entity) {
        try {
            Session session = getSession();
            String tableName = entity.getName();
            int delete = session.createSQLQuery("DELETE FROM " + tableName).executeUpdate();
            closeSession(session);
            return delete != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(String sql) {
        try {
            Session session = getSession();
            int delete = session.createSQLQuery(sql).executeUpdate();
            closeSession(session);
            return delete != 0;
        } catch (Exception e) {
            return false;
        }
    }

    //endregion
}
