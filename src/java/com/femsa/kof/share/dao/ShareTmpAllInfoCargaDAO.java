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

public class ShareTmpAllInfoCargaDAO {

    List<String> errors = new ArrayList<String>();

    public boolean saveInfoCarga(List<ShareTmpAllInfoCarga> listCarga, ShareCatPais pais, ShareUsuario usuario) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo = null;
        boolean flagOk = true;
        try {
            session.beginTransaction();
            for (ShareTmpAllInfoCarga carga : listCarga) {
                session.save(carga);
            }
            queryNativo = session.createSQLQuery("TRUNCATE TABLE " + pais.getNombreTabla());
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("INSERT INTO " + pais.getNombreTabla() + " SELECT PAIS,CANAL,FECHA,GRUPO_CATEGORIA,CATEGORIA,FABRICANTE,VOLUMEN_MES,VENTA_MES FROM SHARE_TMP_ALL_INFO_CARGA");
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("DELETE FROM SHARE_TMP_ALL_INFO_CARGA WHERE FK_USUARIO = " + usuario.getPkUsuario() + " AND PAIS = '" + pais.getNombre() + "'");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            errors.add("Error saving records: " + e.getMessage());
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            flagOk = false;
        } finally {           
            session.close();
        }
        return flagOk;
    }
}
