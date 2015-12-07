package com.femsa.kof.dao;

import com.femsa.kof.pojos.ShareCatGrupoCategorias;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class ShareCatGrupoCategoriasDAO {

    public List<ShareCatGrupoCategorias> getCategoryGroups() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT gc FROM ShareCatGrupoCategorias gc WHERE gc.status = 1");
        List<ShareCatGrupoCategorias> grupos = (List<ShareCatGrupoCategorias>) query.getResultList();
        em.clear();
        em.close();
        return grupos;
    }

    public List<ShareCatGrupoCategorias> getCategoryGroupsAll() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT gc FROM ShareCatGrupoCategorias gc");
        List<ShareCatGrupoCategorias> grupos = (List<ShareCatGrupoCategorias>) query.getResultList();
        em.clear();
        em.close();
        return grupos;
    }

    public boolean saveGroupCategory(ShareCatGrupoCategorias grupoCategorias) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (grupoCategorias.getPkGrupoCategoria() != null) {
                em.merge(grupoCategorias);
            } else {
                em.persist(grupoCategorias);
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
