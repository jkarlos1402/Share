package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatRol;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ShareCatRolDAO {

    public List<ShareCatRol> getCatRol() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT r FROM ShareCatRol r");
        List<ShareCatRol> roles = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return roles;
    }

    public ShareCatRol getRol(Integer idRol) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        ShareCatRol rol = (ShareCatRol) session.get(ShareCatRol.class, idRol);
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return rol;
    }
}
