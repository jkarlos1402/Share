package com.femsa.kof.dao;

import com.femsa.kof.pojos.ShareCatPais;
import com.femsa.kof.pojos.ShareTmpAllInfoCarga;
import com.femsa.kof.pojos.ShareUsuario;
import com.femsa.kof.util.ScriptAnalizer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class ShareTmpAllInfoCargaDAO {
    List<String> errors = new ArrayList<String>();

    public boolean saveInfoCarga(List<ShareTmpAllInfoCarga> listCarga, ShareCatPais pais, ShareUsuario usuario) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        Query queryNativo = null;
        boolean flagOk = true;
        try {
            et.begin();
            for (ShareTmpAllInfoCarga carga : listCarga) {
                em.persist(carga);
            }
            queryNativo = em.createNativeQuery("INSERT INTO " + pais.getNombreTabla() + " SELECT PAIS,CANAL,FECHA,GRUPO_CATEGORIA,CATEGORIA,FABRICANTE,VOLUMEN_MES,VENTA_MES FROM SHARE_TMP_ALL_INFO_CARGA");
            queryNativo.executeUpdate();
            queryNativo = em.createNativeQuery("DELETE FROM SHARE_TMP_ALL_INFO_CARGA WHERE FK_USUARIO = " + usuario.getPkUsuario()+" AND PAIS = '"+pais.getNombre()+"'");
            queryNativo.executeUpdate();                          
            et.commit();
        } catch (Exception e) {  
            errors.add("Error saving records: "+e.getMessage());
            if (et.isActive()) {
                et.rollback();
                flagOk = false;
            }
        } finally {
            em.clear();
            em.close();
        }        
        return flagOk;
    }

}
