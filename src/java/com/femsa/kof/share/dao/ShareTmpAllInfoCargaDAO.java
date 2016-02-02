package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareTmpAllInfoCarga;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class ShareTmpAllInfoCargaDAO {

    List<String> errors = new ArrayList<String>();

    /**
     *
     * @param listCarga
     * @param pais
     * @param usuario
     * @return
     */
    public boolean saveInfoCarga(List<ShareTmpAllInfoCarga> listCarga, ShareCatPais pais, ShareUsuario usuario) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo = null;
        boolean flagOk = true;
        long cont = 0L;
        try {
            session.beginTransaction();
            for (ShareTmpAllInfoCarga carga : listCarga) {
                session.save(carga);
                if(cont % 100 == 0){
                    session.flush();
                    session.clear();
                }
                cont++;
            }
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("TRUNCATE TABLE " + pais.getNombreTabla());
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("INSERT INTO " + pais.getNombreTabla() + " SELECT PAIS,CANAL,FECHA,GRUPO_CATEGORIA,CATEGORIA,FABRICANTE,VOLUMEN_MES,VENTA_MES FROM SHARE_TMP_ALL_INFO_CARGA");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("DELETE FROM SHARE_TMP_ALL_INFO_CARGA WHERE FK_USUARIO = " + usuario.getPkUsuario() + " AND PAIS = '" + pais.getNombre().toUpperCase() + "'");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("DROP SEQUENCE SHARE_SEQ_ALL_INFO_CARGA");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("CREATE SEQUENCE SHARE_SEQ_ALL_INFO_CARGA INCREMENT BY 1 START WITH 1");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            errors.clear();
        } catch (Exception e) {            
            errors.add("Error saving records: " + e.getMessage());
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            flagOk = false;
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return flagOk;
    }

    /**
     *
     * @return
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     *
     * @param errors
     */
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    
    
}
