package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import com.femsa.kof.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class CatCategoriaDAO {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatCategoria> getCategoriasAll() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT c FROM RvvdCatCategoria c");
        List<RvvdCatCategoria> categorias = (List<RvvdCatCategoria>) query.getResultList();
        jpau.closeJPAUtil();
        return categorias;
    }

    public List<RvvdCatCategoria> getCategorias() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT c FROM RvvdCatCategoria c WHERE c.status = 1");
        List<RvvdCatCategoria> categorias = (List<RvvdCatCategoria>) query.getResultList();
        jpau.closeJPAUtil();
        return categorias;
    }

    public RvvdCatCategoria getCategoria(Integer id) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        RvvdCatCategoria categ = em.find(RvvdCatCategoria.class, id);
        jpau.closeJPAUtil();
        return categ;
    }

    public RvvdCatCategoria getCategoria(String categoria) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT c FROM RvvdCatCategoria c WHERE c.categoria = '" + categoria.toUpperCase() + "' OR c.categoriaEn = '"+categoria.toUpperCase()+"'");
        List<RvvdCatCategoria> categorias = (List<RvvdCatCategoria>) query.getResultList();
        RvvdCatCategoria categ = null;
        if(categorias.size() > 0){
            categ = categorias.get(0);
        }
        jpau.closeJPAUtil();
        return categ;
    }
    
    public boolean saveCategoria(RvvdCatCategoria catCategoria){
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (catCategoria.getIdCategoria()!= null) {
                em.merge(catCategoria);
            } else if (getCategoria(catCategoria.getCategoria()) == null) {
                em.persist(catCategoria);
            } else {
                error = "Category already exists";
                flagOk = false;
            }
            et.commit();
        } catch (Exception e) {
            error = e.getMessage();
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
