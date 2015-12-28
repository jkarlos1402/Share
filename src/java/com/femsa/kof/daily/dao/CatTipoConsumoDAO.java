package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatTipoConsumo;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CatTipoConsumoDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatTipoConsumo> getTiposConsumoAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT tc FROM RvvdCatTipoConsumo tc");
        List<RvvdCatTipoConsumo> tiposConsumo = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return tiposConsumo;
    }

    public List<RvvdCatTipoConsumo> getTiposConsumo() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT tc FROM RvvdCatTipoConsumo tc WHERE tc.status = 1");
        List<RvvdCatTipoConsumo> tiposConsumo = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return tiposConsumo;
    }

    public RvvdCatTipoConsumo getTipoConsumo(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatTipoConsumo tipoConsumo = (RvvdCatTipoConsumo) session.get(RvvdCatTipoConsumo.class, id);
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return tipoConsumo;
    }

    public RvvdCatTipoConsumo getTipoConsumo(String tipoConsumo) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT tc FROM RvvdCatTipoConsumo tc WHERE tc.tipoConsumoR = '" + tipoConsumo.toUpperCase() + "' OR tc.tipoConsumoEn = '" + tipoConsumo.toUpperCase() + "'");
        List<RvvdCatTipoConsumo> tiposConsumo = query.list();
        RvvdCatTipoConsumo tipoConsumoT = null;
        if (tiposConsumo.size() > 0) {
            tipoConsumoT = tiposConsumo.get(0);
        }
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return tipoConsumoT;
    }

    public boolean saveTipoConsumo(RvvdCatTipoConsumo tipoConsumo) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((tipoConsumo.getIdTipoConsumo() == null ? getTipoConsumo(tipoConsumo.getTipoConsumoR()) : null) == null) {
                session.saveOrUpdate(tipoConsumo);
            } else {
                error = "Type of consumption already exists";
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
