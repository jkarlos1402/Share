package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatRol;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.femsa.kof.util.JPAUtil;

public class ShareCatRolDAO {

    public List<ShareCatRol> getCatRol() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT r FROM ShareCatRol r");
        List<ShareCatRol> roles = query.getResultList();
        jpau.closeJPAUtil();
        return roles;
    }

    public ShareCatRol getRol(Integer idRol) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        ShareCatRol rol = em.find(ShareCatRol.class, idRol);
        jpau.closeJPAUtil();
        return rol;
    }
}
