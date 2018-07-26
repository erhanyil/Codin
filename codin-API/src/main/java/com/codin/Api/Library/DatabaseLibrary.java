package com.codin.Api.Library;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
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
                .createSQLQuery("CALL " + procedureName)
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

    public Object runQuery(String sql) {
        if (sql.contains("INSERT") || sql.contains("UPDATE") || sql.contains("DELETE")) {
            Object res = createSQLQuery(sql).executeUpdate();
            return res;
        } else {
            List res = createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
            closeSession();
            return res;
        }
    }

    public Object runQuerySelect(String SELECT, String FROM, String WHERE) {
        String sql = "SELECT @SELECT FROM @FROM WHERE @WHERE";
        sql = sql.replace("@SELECT", SELECT).replace("@FROM", FROM);
        if (WHERE == null || WHERE.equals("")) {
            sql = sql.replace("WHERE @WHERE", "");
        } else {
            sql = sql.replace("@WHERE", WHERE);
        }
        List res = createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
        closeSession();
        return res;
    }

    public Object runQueryUpdate(String UPDATE, String SET, String WHERE) {
        String sql = "UPDATE @UPDATE SET @SET WHERE @WHERE";
        sql = sql.replace("@UPDATE", UPDATE).replace("@SET", SET);
        if (WHERE == null || WHERE.equals("")) {
            sql = sql.replace("WHERE @WHERE", "");
        } else {
            sql = sql.replace("@WHERE", WHERE);
        }
        Object res = createSQLQuery(sql).executeUpdate();
        closeSession();
        return res;
    }

    //endregion

    //region SELECT

    public Object select(Class entity) {
        return runQuerySelect("*", ((Class<Table>) entity).getAnnotation(Table.class).name(), null);
    }

    public Object select(String sql) {
        return runQuery(sql);
    }

    public Object select(Class entity, Object ID) {
        return runQuerySelect("*", ((Class<Table>) entity).getAnnotation(Table.class).name(), "ID = " + ID);
    }

    public Object select(String SELECT, String FROM, String WHERE) {
        return runQuerySelect(SELECT, FROM, WHERE);
    }

    //endregion

    //region UPDATE

    public Object update(String UPDATE, String SET, String WHERE) {
        return runQueryUpdate(UPDATE, SET, WHERE);
    }

    public Object update(String sql) {
        return runQuery(sql);
    }

    //endregion

    //region SAVE/UPDATE

    public Object saveOrUpdate(Object object) {
        try {
            Session session = getSession();
            session.saveOrUpdate(object);
            session.flush();
            closeSession(session);
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
            String tableName = ((Class<Table>) entity).getAnnotation(Table.class).name();
            int delete = session.createSQLQuery("DELETE FROM " + tableName).executeUpdate();
            return delete != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(String sql) {
        try {
            Object res = runQuery(sql);
            return (Long) res > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(String DELETE_FROM, String WHERE) {
        try {
            Object res = runQuery(DELETE_FROM + " " + WHERE);
            return (Long) res > 0;
        } catch (Exception e) {
            return false;
        }
    }

    //endregion
}
