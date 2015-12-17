package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifCanal;
import com.femsa.kof.daily.pojos.RvvdReclasifCategoria;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.JPAUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class ReclasifCanalDAO {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdReclasifCanal> getReclasifCanalesAll(ShareUsuario usuario) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        List<String> paises = new ArrayList<String>();
        for (int i = 0; i < usuario.getPaises().size(); i++) {
            paises.add(usuario.getPaises().get(i).getClaveCorta());
        }
        Query query = em.createQuery("SELECT rc FROM RvvdReclasifCanal rc WHERE rc.pais IN :paises");
        query.setParameter("paises", paises);
        List<RvvdReclasifCanal> canalesReclasificados = (List<RvvdReclasifCanal>) query.getResultList();
        jpau.closeJPAUtil();
        return canalesReclasificados;
    }

    public boolean saveReclasifCanales(List<RvvdReclasifCanal> reclasifCanales) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (reclasifCanales != null) {
                for (RvvdReclasifCanal reclasifCanal : reclasifCanales) {
                    em.merge(reclasifCanal);
                }
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

    public long checkReclasifCanales(ShareUsuario usuario) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        List<String> paises = new ArrayList<String>();
        for (int i = 0; i < usuario.getPaises().size(); i++) {
            paises.add(usuario.getPaises().get(i).getClaveCorta());
        }
        Query query = em.createQuery("SELECT count(rc.idReclasifCanal) FROM RvvdReclasifCanal rc WHERE rc.pais IN :paises AND (rc.canalR IS NULL OR rc.canalEn IS NULL)");
        query.setParameter("paises", paises);
        long numNotReclass = ((Number) query.getSingleResult()).longValue();
        jpau.closeJPAUtil();
        return numNotReclass;
    }
}
