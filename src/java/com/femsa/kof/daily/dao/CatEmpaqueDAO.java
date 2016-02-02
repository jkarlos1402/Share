package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatEmpaque;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class CatEmpaqueDAO {

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
    public List<RvvdCatEmpaque> getEmpaquesAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatEmpaque> empaques = null;
        try {
            Query query = session.createQuery("SELECT e FROM RvvdCatEmpaque e");
            empaques = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return empaques;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatEmpaque> getEmpaques() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatEmpaque> empaques = null;
        try {
            Query query = session.createQuery("SELECT e FROM RvvdCatEmpaque e WHERE e.status = 1");
            empaques = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return empaques;
    }

    /**
     *
     * @param id
     * @return
     */
    public RvvdCatEmpaque getEmpaque(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatEmpaque empaque = null;
        try {
            empaque = (RvvdCatEmpaque) session.get(RvvdCatEmpaque.class, id);
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return empaque;
    }

    /**
     *
     * @param empaque
     * @return
     */
    public RvvdCatEmpaque getEmpaque(String empaque) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatEmpaque empaqueT = null;
        try {
            Query query = session.createQuery("SELECT e FROM RvvdCatEmpaque e WHERE e.empaqueR = '" + empaque.toUpperCase() + "' OR e.empaqueEn = '" + empaque.toUpperCase() + "'");
            List<RvvdCatEmpaque> empaques = query.list();
            if (empaques.size() > 0) {
                empaqueT = empaques.get(0);
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
        return empaqueT;
    }

    /**
     *
     * @param empaque
     * @return
     */
    public boolean saveEmpaque(RvvdCatEmpaque empaque) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((empaque.getIdEmpaque() == null ? getEmpaque(empaque.getEmpaqueR()) : null) == null) {
                session.saveOrUpdate(empaque);
                error = null;
            } else {
                error = "Packing already exists";
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
