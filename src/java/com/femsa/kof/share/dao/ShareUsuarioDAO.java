package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ShareUsuarioDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ShareUsuario getUsuario(String user, String password) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        ShareUsuario usuario = null;
        try {
            Query query = session.createQuery("FROM ShareUsuario u WHERE u.usuario = '" + user.toUpperCase() + "' AND u.password = '" + password + "' AND u.estatus = 1");
            List<ShareUsuario> usuarios = query.list();

            if (usuarios.size() > 0) {
                return usuarios.get(0);
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
        return usuario;
    }   

    public ShareUsuario getUsuario(String user, boolean filtrado) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        ShareUsuario usuario = null;
        Query query = null;
        try {
            if (filtrado) {                  
                query = session.createQuery("FROM ShareUsuario u WHERE u.usuario = '" + user.toUpperCase() + "' AND u.estatus = 1");
            } else {
                query = session.createQuery("FROM ShareUsuario u WHERE u.usuario = '" + user.toUpperCase() + "'");
            }
            List<ShareUsuario> usuarios = query.list();
            if (usuarios.size() > 0) {
                return usuarios.get(0);
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
        return usuario;
    }

    public List<ShareUsuario> getAllUsers() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareUsuario> usuarios = null;
        try {
            Query query = session.createQuery("FROM ShareUsuario u");
            usuarios = (List<ShareUsuario>) query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return usuarios;
    }

    public boolean saveUser(ShareUsuario usuario) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((usuario.getPkUsuario() == null ? getUsuario(usuario.getUsuario(),false) : null) == null) {
                session.saveOrUpdate(usuario);
                error = null;
            } else {
                error = "User already exists";
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
