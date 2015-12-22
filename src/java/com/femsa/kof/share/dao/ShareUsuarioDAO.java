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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM ShareUsuario u WHERE u.usuario = '" + user.toUpperCase() + "' AND u.password = '" + password + "' AND u.estatus = 1");
        List<ShareUsuario> usuarios = query.list();
        ShareUsuario usuario = null;
        if (usuarios.size() > 0) {
            return usuarios.get(0);
        }
        session.close();
        return usuario;
    }

    public ShareUsuario getUsuario(String user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM ShareUsuario u WHERE u.usuario = '" + user.toUpperCase() + "'");
        List<ShareUsuario> usuarios = query.list();
        ShareUsuario usuario = null;
        if (usuarios.size() > 0) {
            return usuarios.get(0);
        }
        session.close();
        return usuario;
    }

    public List<ShareUsuario> getAllUsers() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM ShareUsuario u");
        List<ShareUsuario> usuarios = (List<ShareUsuario>) query.list();
        session.close();
        return usuarios;
    }

    public boolean saveUser(ShareUsuario usuario) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((usuario.getPkUsuario() == null ? getUsuario(usuario.getUsuario()) : null) == null) {
                session.saveOrUpdate(usuario);
            } else {
                error = "User already exists";
                flagOk = false;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
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
