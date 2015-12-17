package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatEmpaque;
import com.femsa.kof.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class CatEmpaqueDAO {
private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatEmpaque> getEmpaquesAll() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT e FROM RvvdCatEmpaque e");
        List<RvvdCatEmpaque> empaques = (List<RvvdCatEmpaque>) query.getResultList();
        jpau.closeJPAUtil();
        return empaques;
    }

    public List<RvvdCatEmpaque> getEmpaques() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT e FROM RvvdCatEmpaque e WHERE e.status = 1");
        List<RvvdCatEmpaque> empaques = (List<RvvdCatEmpaque>) query.getResultList();
        jpau.closeJPAUtil();
        return empaques;
    }

    public RvvdCatEmpaque getEmpaque(Integer id) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        RvvdCatEmpaque empaque = em.find(RvvdCatEmpaque.class, id);
        jpau.closeJPAUtil();
        return empaque;
    }

    public RvvdCatEmpaque getEmpaque(String empaque) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT e FROM RvvdCatEmpaque e WHERE e.empaqueR = '" + empaque.toUpperCase() + "' OR e.empaqueEn = '" + empaque.toUpperCase() + "'");
        List<RvvdCatEmpaque> empaques = (List<RvvdCatEmpaque>) query.getResultList();
        RvvdCatEmpaque empaqueT = null;
        if (empaques.size() > 0) {
            empaqueT = empaques.get(0);
        }
        jpau.closeJPAUtil();
        return empaqueT;
    }

    public boolean saveEmpaque(RvvdCatEmpaque empaque) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (empaque.getIdEmpaque()!= null) {
                em.merge(empaque);
            } else if (getEmpaque(empaque.getEmpaqueR()) == null) {
                em.persist(empaque);
            } else {
                error = "Packing already exists";
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
