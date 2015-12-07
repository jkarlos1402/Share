package com.femsa.kof.dao;

import com.femsa.kof.pojos.ShareCatCategorias;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class ShareCatCategoriasDAO {

    public List<ShareCatCategorias> getCategorias() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT categ FROM ShareCatCategorias categ WHERE categ.status = 1");
        List<ShareCatCategorias> categories = query.getResultList();
        em.clear();
        em.close();
        return categories;
    }

    public List<ShareCatCategorias> getCategoriasAll() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT categ FROM ShareCatCategorias categ");
        List<ShareCatCategorias> categories = query.getResultList();
        em.clear();
        em.close();
        return categories;
    }

    public boolean saveCategoria(ShareCatCategorias categoria) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (categoria.getPkCategoria() != null) {
                em.merge(categoria);
            } else {
                em.persist(categoria);
            }
            et.commit();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
