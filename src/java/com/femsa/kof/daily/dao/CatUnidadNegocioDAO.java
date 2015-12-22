package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatUnidadNegocio;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CatUnidadNegocioDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatUnidadNegocio> getUnidadesNegAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT un FROM RvvdCatUnidadNegocio un");
        List<RvvdCatUnidadNegocio> unidades = query.list();
        session.close();
        return unidades;
    }

    public List<RvvdCatUnidadNegocio> getUnidadesNeg() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT un FROM RvvdCatUnidadNegocio un WHERE un.status = 1");
        List<RvvdCatUnidadNegocio> unidades = query.list();
        session.close();
        return unidades;
    }

    public RvvdCatUnidadNegocio getUnidadNeg(Integer id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatUnidadNegocio unidad = (RvvdCatUnidadNegocio) session.get(RvvdCatUnidadNegocio.class, id);
        session.close();
        return unidad;
    }

    public RvvdCatUnidadNegocio getUnidadNeg(String unidad) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT un FROM RvvdCatUnidadNegocio un WHERE un.unidadNegocioR = '" + unidad.toUpperCase() + "' OR un.unidadNegocioEn = '" + unidad.toUpperCase() + "'");
        List<RvvdCatUnidadNegocio> unidades = query.list();
        RvvdCatUnidadNegocio unidadT = null;
        if (unidades.size() > 0) {
            unidadT = unidades.get(0);
        }
        session.close();
        return unidadT;
    }

    public boolean saveUnidadNeg(RvvdCatUnidadNegocio unidad) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((unidad.getIdUnidadNegocio() == null ? getUnidadNeg(unidad.getUnidadNegocioR()) : null) == null) {
                session.saveOrUpdate(unidad);
            } else {
                error = "Bussiness unit already exists";
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
            session.close();
        }
        return flagOk;
    }
}
