package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareLoadLog;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Permite la administración de logs de carga
 *
 * @author TMXIDSJPINAM
 */
public class ShareLoadLogDAO {

    private String error = "";
    private static final String MSG_ERROR_TITULO = "Mensaje de error...";

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

    public boolean saveLog(ShareLoadLog log) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            session.save(log);
            session.getTransaction().commit();
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
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

    public List<ShareLoadLog> getLogByUser(ShareUsuario usuario, String proyecto) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareLoadLog> logs = null;
        Query query = null;
        try {
            if ("daily".equalsIgnoreCase(proyecto)) {
                query = session.createQuery("SELECT sll FROM ShareLoadLog sll WHERE month(sll.fechaEjecucion) = month(CURRENT_DATE) AND NOMBRE_PROYECTO = 'DAILY DASHBOARD' AND sll.usuario.pkUsuario = " + usuario.getPkUsuario() + " ORDER BY sll.fechaEjecucion DESC");
            } else if ("share".equalsIgnoreCase(proyecto)) {
                query = session.createQuery("SELECT sll FROM ShareLoadLog sll WHERE month(sll.fechaEjecucion) = month(CURRENT_DATE) AND NOMBRE_PROYECTO = 'SHARE' AND sll.usuario.pkUsuario = " + usuario.getPkUsuario() + " ORDER BY sll.fechaEjecucion DESC");
            }
            logs = query != null ? query.list() : null;
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return logs;
    }

    public List<ShareLoadLog> getLogAll(String proyecto) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareLoadLog> logs = null;
        Query query = null;
        try {
            if ("daily".equalsIgnoreCase(proyecto)) {
                query = session.createQuery("SELECT sll FROM ShareLoadLog sll WHERE month(sll.fechaEjecucion) = month(CURRENT_DATE) AND NOMBRE_PROYECTO = 'DAILY DASHBOARD' ORDER BY sll.fechaEjecucion DESC");
            } else if ("share".equalsIgnoreCase(proyecto)) {
                query = session.createQuery("SELECT sll FROM ShareLoadLog sll WHERE month(sll.fechaEjecucion) = month(CURRENT_DATE) AND NOMBRE_PROYECTO = 'SHARE' ORDER BY sll.fechaEjecucion DESC");
            }
            logs = query != null ? query.list() : null;
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return logs;
    }
}
