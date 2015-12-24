package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatCanales;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ShareCatCanalesDAO {

    public List<ShareCatCanales> getCanales() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT canales FROM ShareCatCanales canales");
        List<ShareCatCanales> canales = query.list();     
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return canales;
    }
}
