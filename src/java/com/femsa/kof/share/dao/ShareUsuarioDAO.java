package com.femsa.kof.share.dao;


import com.femsa.kof.share.pojos.ShareUsuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.femsa.kof.util.JPAUtil;

public class ShareUsuarioDAO {

    public ShareUsuario getUsuario(String user, String password) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT u FROM ShareUsuario u WHERE u.usuario = '" + user.toUpperCase() + "' AND u.password = '" + password + "' AND u.estatus = 1");
        List<ShareUsuario> usuarios = (List<ShareUsuario>) query.getResultList();
        ShareUsuario usuario = null;
        if (usuarios.size() > 0) {
            return usuarios.get(0);
        }
        em.clear();
        em.close();
        return usuario;
    }

    public List<ShareUsuario> getAllUsers() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT u FROM ShareUsuario u");
        List<ShareUsuario> usuarios = (List<ShareUsuario>) query.getResultList();
        em.clear();
        em.close();
        return usuarios;
    }

    public boolean saveUser(ShareUsuario usuario) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (usuario.getPkUsuario() == null) {
                em.persist(usuario);
            } else {                
                em.merge(usuario);
            }
            et.commit();
        } catch (Exception e) {            
            if (et.isActive()) {
                et.rollback();
            }
            flagOk = false;
        } finally {
            em.clear();
            em.close();
        }
        return flagOk;
    }
}
