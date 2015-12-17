package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatTipoConsumo;
import com.femsa.kof.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class CatTipoConsumoDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatTipoConsumo> getTiposConsumoAll() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT tc FROM RvvdCatTipoConsumo tc");
        List<RvvdCatTipoConsumo> tiposConsumo = (List<RvvdCatTipoConsumo>) query.getResultList();
        jpau.closeJPAUtil();
        return tiposConsumo;
    }

    public List<RvvdCatTipoConsumo> getTiposConsumo() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT tc FROM RvvdCatTipoConsumo tc WHERE tc.status = 1");
        List<RvvdCatTipoConsumo> tiposConsumo = (List<RvvdCatTipoConsumo>) query.getResultList();
        jpau.closeJPAUtil();
        return tiposConsumo;
    }

    public RvvdCatTipoConsumo getTipoConsumo(Integer id) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        RvvdCatTipoConsumo tipoConsumo = em.find(RvvdCatTipoConsumo.class, id);
        jpau.closeJPAUtil();
        return tipoConsumo;
    }

    public RvvdCatTipoConsumo getTipoConsumo(String tipoConsumo) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT tc FROM RvvdCatTipoConsumo tc WHERE tc.tipoConsumoR = '" + tipoConsumo.toUpperCase() + "' OR tc.tipoConsumoEn = '" + tipoConsumo.toUpperCase() + "'");
        List<RvvdCatTipoConsumo> tiposConsumo = (List<RvvdCatTipoConsumo>) query.getResultList();
        RvvdCatTipoConsumo tipoConsumoT = null;
        if (tiposConsumo.size() > 0) {
            tipoConsumoT = tiposConsumo.get(0);
        }
        jpau.closeJPAUtil();
        return tipoConsumoT;
    }

    public boolean saveTipoConsumo(RvvdCatTipoConsumo tipoConsumo) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (tipoConsumo.getIdTipoConsumo() != null) {
                em.merge(tipoConsumo);
            } else if (getTipoConsumo(tipoConsumo.getTipoConsumoR()) == null) {
                em.persist(tipoConsumo);
            } else {
                error = "Type of consumption already exists";
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
