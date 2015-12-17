package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifEmpaque;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.JPAUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class ReclasifEmpaqueDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdReclasifEmpaque> getReclasifEmpaquesAll(ShareUsuario usuario) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        List<String> paises = new ArrayList<String>();
        for (int i = 0; i < usuario.getPaises().size(); i++) {
            paises.add(usuario.getPaises().get(i).getClaveCorta());
        }
        Query query = em.createQuery("SELECT re FROM RvvdReclasifEmpaque re WHERE re.pais IN :paises");
        query.setParameter("paises", paises);
        List<RvvdReclasifEmpaque> empaquesReclasificados = (List<RvvdReclasifEmpaque>) query.getResultList();
        jpau.closeJPAUtil();
        return empaquesReclasificados;
    }

    public boolean saveReclasifEmpaques(List<RvvdReclasifEmpaque> reclasifEmpaques) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (reclasifEmpaques != null) {
                for (RvvdReclasifEmpaque reclasifEmpaque : reclasifEmpaques) {
                    em.merge(reclasifEmpaque);
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

    public long checkReclasifEmpaques(ShareUsuario usuario) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        List<String> paises = new ArrayList<String>();
        for (int i = 0; i < usuario.getPaises().size(); i++) {
            paises.add(usuario.getPaises().get(i).getClaveCorta());
        }
        Query query = em.createQuery("SELECT count(re.idReclasifEmpaque) FROM RvvdReclasifEmpaque re WHERE re.pais IN :paises AND (re.tipoConsumoR IS NULL OR re.tipoConsumoEn IS NULL OR re.empaqueR IS NULL OR re.empaqueEn IS NULL)");
        query.setParameter("paises", paises);
        long numNotReclass = ((Number) query.getSingleResult()).longValue();
        jpau.closeJPAUtil();
        return numNotReclass;
    }
}
