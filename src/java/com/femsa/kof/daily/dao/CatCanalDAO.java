package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCanal;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class CatCanalDAO {

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
    public List<RvvdCatCanal> getCanalesAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatCanal> canales = null;
        try {
            Query query = session.createQuery("SELECT c FROM RvvdCatCanal c");
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

    /**
     *
     * @return
     */
    public List<RvvdCatCanal> getCanales() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatCanal> canales = null;
        try {
            Query query = session.createQuery("SELECT c FROM RvvdCatCanal c WHERE c.status = 1");
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

    /**
     *
     * @param id
     * @return
     */
    public RvvdCatCanal getCanal(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCanal canal = null;
        try {
            canal = (RvvdCatCanal) session.get(RvvdCatCanal.class, id);
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return canal;
    }

    /**
     *
     * @param canal
     * @return
     */
    public RvvdCatCanal getCanal(String canal) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCanal canalT = null;
        try {
            Query query = session.createQuery("SELECT c FROM RvvdCatCanal c WHERE c.canalR = '" + canal.toUpperCase() + "' OR c.canalEn = '" + canal.toUpperCase() + "'");
            List<RvvdCatCanal> canales = query.list();

            if (canales.size() > 0) {
                canalT = canales.get(0);
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
        return canalT;
    }

    /**
     *
     * @param canal
     * @return
     */
    public boolean saveCanal(RvvdCatCanal canal) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((canal.getIdCanal() == null ? getCanal(canal.getCanalR()) : null) == null) {
                session.saveOrUpdate(canal);
                error = null;
            } else {
                error = "Channel already exists";
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
