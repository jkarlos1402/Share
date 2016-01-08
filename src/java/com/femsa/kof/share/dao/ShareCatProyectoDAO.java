package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatProyecto;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ShareCatProyectoDAO {

    private String error = "";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ShareCatProyecto getProyecto(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        ShareCatProyecto proyecto = (ShareCatProyecto) session.get(ShareCatProyecto.class, id);
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return proyecto;
    }

    public List<ShareCatProyecto> getCatProyectos() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT proyecto FROM ShareCatProyecto proyecto WHERE proyecto.status = 1");
        List<ShareCatProyecto> proyectos = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return proyectos;
    }

    public ShareCatProyecto getProyecto(String nombrProyecto) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT proyecto FROM ShareCatProyecto proyecto WHERE proyecto.nombreProyecto = '" + nombrProyecto.toUpperCase() + "'");
        List<ShareCatProyecto> proyectos = query.list();
        ShareCatProyecto proyecto = null;
        if (proyectos.size() > 0) {
            proyecto = proyectos.get(0);
        }
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return proyecto;
    }

    public List<ShareCatProyecto> getCatProyectosAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT proyecto FROM ShareCatProyecto proyecto");
        List<ShareCatProyecto> proyectos = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return proyectos;
    }

    public boolean saveProyecto(ShareCatProyecto proyecto) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = null;
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if (proyecto.getIdProyecto() != null) {
                session.update(proyecto);
            } else if (getProyecto(proyecto.getNombreProyecto()) == null) {
                session.save(proyecto);
            } else {
                error = "Project already exists";
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
