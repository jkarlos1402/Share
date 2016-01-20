package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatCanales;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ShareCatCanalesDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ShareCatCanales> getCanales() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareCatCanales> canales = null;
        try {
            Query query = session.createQuery("SELECT canales FROM ShareCatCanales canales");
            canales = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return canales;
    }
}
