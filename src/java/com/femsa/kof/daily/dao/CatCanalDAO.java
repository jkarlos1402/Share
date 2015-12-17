package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCanal;
import com.femsa.kof.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class CatCanalDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatCanal> getCanalesAll() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT c FROM RvvdCatCanal c");
        List<RvvdCatCanal> canales = (List<RvvdCatCanal>) query.getResultList();
        jpau.closeJPAUtil();
        return canales;
    }

    public List<RvvdCatCanal> getCanales() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT c FROM RvvdCatCanal c WHERE c.status = 1");
        List<RvvdCatCanal> canales = (List<RvvdCatCanal>) query.getResultList();
        jpau.closeJPAUtil();
        return canales;
    }

    public RvvdCatCanal getCanal(Integer id) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        RvvdCatCanal canal = em.find(RvvdCatCanal.class, id);
        jpau.closeJPAUtil();
        return canal;
    }

    public RvvdCatCanal getCanal(String canal) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT c FROM RvvdCatCanal c WHERE c.canalR = '" + canal.toUpperCase() + "' OR c.canalEn = '" + canal.toUpperCase() + "'");
        List<RvvdCatCanal> canales = (List<RvvdCatCanal>) query.getResultList();
        RvvdCatCanal canalT = null;
        if (canales.size() > 0) {
            canalT = canales.get(0);
        }
        jpau.closeJPAUtil();
        return canalT;
    }

    public boolean saveCanal(RvvdCatCanal canal) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (canal.getIdCanal() != null) {
                em.merge(canal);
            } else if (getCanal(canal.getCanalR()) == null) {
                em.persist(canal);
            } else {
                error = "Channel already exists";
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
