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
        List<ShareCatFabricante> fabricantes = null;
        try {
            Query query = session.createQuery("SELECT fab FROM ShareCatFabricante fab");
            fabricantes = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return fabricantes;
    }

    public List<ShareCatFabricante> getFabricantes() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareCatFabricante> fabricantes = null;
        try {
            Query query = session.createQuery("SELECT fab FROM ShareCatFabricante fab WHERE fab.status = 1");
            fabricantes = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return fabricantes;
    }

    public ShareCatFabricante getFabricante(String name) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareCatFabricante> fabricantes = null;
        ShareCatFabricante fabricante = null;
        try {
            Query query = session.createQuery("SELECT fab FROM ShareCatFabricante fab WHERE fab.fabricante = '" + name.toUpperCase() + "'");
            fabricantes = query.list();
            if (fabricantes.size() > 0) {
                fabricante = fabricantes.get(0);
            }
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
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
                error = null;
            } else {
                error = "Manufacturer already exists";
                flagOk = false;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            error = e.getMessage();
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
