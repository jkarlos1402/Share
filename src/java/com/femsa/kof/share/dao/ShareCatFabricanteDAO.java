package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatFabricante;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ShareCatFabricanteDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ShareCatFabricante> getFabricantesAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT fab FROM ShareCatFabricante fab");
        List<ShareCatFabricante> fabricantes = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return fabricantes;
    }

    public List<ShareCatFabricante> getFabricantes() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT fab FROM ShareCatFabricante fab WHERE fab.status = 1");
        List<ShareCatFabricante> fabricantes = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return fabricantes;
    }

    public ShareCatFabricante getFabricante(String name) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT fab FROM ShareCatFabricante fab WHERE fab.fabricante = '" + name.toUpperCase() + "'");
        List<ShareCatFabricante> fabricantes = query.list();
        ShareCatFabricante fabricante = null;
        if (fabricantes.size() > 0) {
            fabricante = fabricantes.get(0);
        }
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return fabricante;
    }

    public boolean saveFabricante(ShareCatFabricante fabricante) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((fabricante.getPkFabricante() == null ? getFabricante(fabricante.getFabricante()) : null) == null) {
                session.saveOrUpdate(fabricante);
            } else {
                error = "Manufacturer already exists";
                flagOk = false;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            flagOk = false;
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return flagOk;
    }
}
