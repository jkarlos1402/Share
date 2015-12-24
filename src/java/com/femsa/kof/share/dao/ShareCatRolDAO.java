package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatRol;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ShareCatRolDAO {

    public List<ShareCatRol> getCatRol() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT r FROM ShareCatRol r");
        List<ShareCatRol> roles = query.list();       
        session.close();
        return roles;
    }

    public ShareCatRol getRol(Integer idRol) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        ShareCatRol rol = (ShareCatRol) session.get(ShareCatRol.class, idRol);       
        session.close();
        return rol;
    }
}
