package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatTipoConsumo;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class CatTipoConsumoDAO {

    private String error;

    /**
     *
     * @return
     */
    public String getError() {
        return error;
    }

    /**
     *
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatTipoConsumo> getTiposConsumoAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatTipoConsumo> tiposConsumo = null;
        try {
            Query query = session.createQuery("SELECT tc FROM RvvdCatTipoConsumo tc");
            tiposConsumo = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return tiposConsumo;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatTipoConsumo> getTiposConsumo() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatTipoConsumo> tiposConsumo = null;
        try {
            Query query = session.createQuery("SELECT tc FROM RvvdCatTipoConsumo tc WHERE tc.status = 1");
            tiposConsumo = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return tiposConsumo;
    }

    /**
     *
     * @param id
     * @return
     */
    public RvvdCatTipoConsumo getTipoConsumo(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatTipoConsumo tipoConsumo = null;
        try {
            tipoConsumo = (RvvdCatTipoConsumo) session.get(RvvdCatTipoConsumo.class, id);
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return tipoConsumo;
    }

    /**
     *
     * @param tipoConsumo
     * @return
     */
    public RvvdCatTipoConsumo getTipoConsumo(String tipoConsumo) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatTipoConsumo tipoConsumoT = null;
        try {
            Query query = session.createQuery("SELECT tc FROM RvvdCatTipoConsumo tc WHERE tc.tipoConsumoR = '" + tipoConsumo.toUpperCase() + "' OR tc.tipoConsumoEn = '" + tipoConsumo.toUpperCase() + "'");
            List<RvvdCatTipoConsumo> tiposConsumo = query.list();
            if (tiposConsumo.size() > 0) {
                tipoConsumoT = tiposConsumo.get(0);
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
        return tipoConsumoT;
    }

    /**
     *
     * @param tipoConsumo
     * @return
     */
    public boolean saveTipoConsumo(RvvdCatTipoConsumo tipoConsumo) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((tipoConsumo.getIdTipoConsumo() == null ? getTipoConsumo(tipoConsumo.getTipoConsumoR()) : null) == null) {
                session.saveOrUpdate(tipoConsumo);
                error = null;
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
