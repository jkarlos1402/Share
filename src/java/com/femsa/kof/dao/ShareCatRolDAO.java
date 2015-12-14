package com.femsa.kof.dao;

import com.femsa.kof.pojos.ShareCatRol;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ShareCatRolDAO {

    public List<ShareCatRol> getCatRol() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT r FROM ShareCatRol r");
        List<ShareCatRol> roles = query.getResultList();
        em.clear();
        em.close();
        return roles;
    }

    public ShareCatRol getRol(Integer idRol) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        ShareCatRol rol = em.find(ShareCatRol.class, idRol);
        em.clear();
        em.close();
        return rol;
    }
}
