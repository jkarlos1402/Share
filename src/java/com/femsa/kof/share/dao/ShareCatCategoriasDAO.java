package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatCategorias;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.femsa.kof.util.JPAUtil;

public class ShareCatCategoriasDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ShareCatCategorias> getCategorias() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT categ FROM ShareCatCategorias categ WHERE categ.status = 1");
        List<ShareCatCategorias> categories = query.getResultList();
        jpau.closeJPAUtil();
        return categories;
    }

    public ShareCatCategorias getCategoria(String name) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT categ FROM ShareCatCategorias categ WHERE categ.categoria = '" + name.toUpperCase() + "'");
        List<ShareCatCategorias> categories = query.getResultList();
        ShareCatCategorias category = null;
        if (categories.size() > 0) {
            category = categories.get(0);
        }
        jpau.closeJPAUtil();
        return category;
    }

    public List<ShareCatCategorias> getCategoriasAll() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT categ FROM ShareCatCategorias categ");
        List<ShareCatCategorias> categories = query.getResultList();
        jpau.closeJPAUtil();
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
            } else if (getCategoria(categoria.getCategoria()) == null) {
                em.persist(categoria);
            } else {
                error = "Category already exists";
                flagOk = false;
            }
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            flagOk = false;
        } finally {
            jpau.closeJPAUtil();
        }
        return flagOk;
    }
}
