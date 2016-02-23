package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RollingDaily;
import com.femsa.kof.daily.pojos.RvvdDistribucionMxTmp;
import com.femsa.kof.daily.pojos.RvvdStRollingTmp;
import com.femsa.kof.managedbeans.MainBean;
import com.femsa.kof.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class RollingDAO {

    private List<String> errors = new ArrayList<String>();
    private static final String MSG_ERROR_TITULO = "Mensaje de error...";

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

    /**
     *
     * @param dailys
     * @param distribuciones
     * @param mainBean
     * @return
     */
    public boolean saveDaily(List<RollingDaily> dailys, List<RvvdDistribucionMxTmp> distribuciones, MainBean mainBean) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo;
        boolean flagOk = true;
        long cont = 0L;
        String pais = "";
        try {
            session.beginTransaction();
            for (RollingDaily carga : dailys) {
                pais = carga.getDiasOperativos().getPais();
                session.save(carga.getDiasOperativos());
                mainBean.setNumRegistrosProcesados(cont);
                mainBean.setPorcentajeAvance((int) ((cont * 100) / ((carga.getStRollings() != null ? carga.getStRollings().size() : 0) + dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));
                for (RvvdStRollingTmp rolling : carga.getStRollings() != null ? carga.getStRollings() : new ArrayList<RvvdStRollingTmp>()) {
                    mainBean.setNumRegistrosProcesados(cont);
                    mainBean.setPorcentajeAvance((int) ((cont * 100) / ((carga.getStRollings() != null ? carga.getStRollings().size() : 0) + dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));
                    session.save(rolling);
                    cont++;
                }
                if (cont % 100 == 0) {
                    session.flush();
                    session.clear();
                }
                cont++;
            }
            for (RvvdDistribucionMxTmp carga : distribuciones) {
                session.save(carga);
                mainBean.setNumRegistrosProcesados(cont);
                mainBean.setPorcentajeAvance((int) ((cont * 100) / (dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));
                if (cont % 100 == 0) {
                    session.flush();
                    session.clear();
                }
                cont++;
            }
            errors.clear();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("DELETE FROM RVVD_RECLASIF_DIAS_OP WHERE PAIS = '" + pais + "' AND FECHA IN (SELECT DISTINCT(FECHA) FROM RVVD_RECLASIF_DIAS_OP_TMP)");
            queryNativo.executeUpdate();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));
            queryNativo = session.createSQLQuery("INSERT INTO RVVD_RECLASIF_DIAS_OP(PAIS,FECHA,ID_TIEMPO,FECHA_R,ID_TIEMPO_R) SELECT PAIS,FECHA,(SELECT PK_TIEMPO FROM RVVD_DIM_TIEMPO WHERE GD_FECHA = FECHA),FECHA_R,(SELECT PK_TIEMPO FROM RVVD_DIM_TIEMPO WHERE GD_FECHA = FECHA_R) FROM RVVD_RECLASIF_DIAS_OP_TMP");
            queryNativo.executeUpdate();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));
            queryNativo = session.createSQLQuery("DELETE FROM RVVD_RECLASIF_DIAS_OP_TMP");
            queryNativo.executeUpdate();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));
            queryNativo = session.createSQLQuery("DELETE FROM RVVD_ST_ROLLING WHERE PAIS = '" + pais + "' AND FECHA IN (SELECT DISTINCT(FECHA) FROM RVVD_ST_ROLLING_TMP)");
            queryNativo.executeUpdate();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));
            queryNativo = session.createSQLQuery("INSERT INTO RVVD_ST_ROLLING(ANIO,MES,DIA,FECHA,PAIS,DESC_PAIS,ZONA,CATEGORIA,CATEGORIA_OFICIAL_R,CATEGORIA_OFICIAL_EN,GEC,ROLLING_CU,ROLLING_INGRESO,ROLLING_TA) SELECT ANIO,MES,DIA,FECHA,PAIS,DESC_PAIS,ZONA,CATEGORIA,CATEGORIA_OFICIAL_R,CATEGORIA_OFICIAL_EN,GEC,ROLLING_CU,ROLLING_INGRESO,ROLLING_TA FROM RVVD_ST_ROLLING_TMP");
            queryNativo.executeUpdate();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));
            queryNativo = session.createSQLQuery("DELETE FROM RVVD_ST_ROLLING_TMP");
            queryNativo.executeUpdate();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));

            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));
            if (distribuciones != null) {
                queryNativo = session.createSQLQuery("DELETE FROM RVVD_DISTRIBUCION_MX WHERE PAIS = '" + pais + "' AND FECHA_ORIGEN IN (SELECT DISTINCT(FECHA_ORIGEN) FROM RVVD_DISTRIBUCION_MX_TMP)");
                queryNativo.executeUpdate();
                mainBean.setPorcentajeAvance((int) ((++cont * 100) / (dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));
                queryNativo = session.createSQLQuery("INSERT INTO RVVD_DISTRIBUCION_MX(PAIS,FECHA_ORIGEN,FECHA_DESTINO,PORCENTAJE) SELECT PAIS,FECHA_ORIGEN,FECHA_DESTINO,PORCENTAJE FROM RVVD_DISTRIBUCION_MX_TMP");
                queryNativo.executeUpdate();
                mainBean.setPorcentajeAvance((int) ((++cont * 100) / (dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));
                queryNativo = session.createSQLQuery("DELETE FROM RVVD_DISTRIBUCION_MX_TMP");
                queryNativo.executeUpdate();
                mainBean.setPorcentajeAvance((int) ((++cont * 100) / (dailys.size() + (distribuciones != null ? distribuciones.size() : 0))));                
            }
            queryNativo = session.createSQLQuery("COMMIT");
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("DROP SEQUENCE RVVD_SEQ_RECLASIF_DIAS_OP_TMP");
            queryNativo.executeUpdate();            
            queryNativo = session.createSQLQuery("CREATE SEQUENCE RVVD_SEQ_RECLASIF_DIAS_OP_TMP INCREMENT BY 1 START WITH 1");
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("DROP SEQUENCE RVVD_SEQ_ST_ROLLING_TMP");
            queryNativo.executeUpdate();                   
            queryNativo = session.createSQLQuery("CREATE SEQUENCE RVVD_SEQ_ST_ROLLING_TMP INCREMENT BY 1 START WITH 1");
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("DROP SEQUENCE RVVD_SEQ_DISTRIBUCION_MX_TMP");
            queryNativo.executeUpdate();            
            queryNativo = session.createSQLQuery("CREATE SEQUENCE RVVD_SEQ_DISTRIBUCION_MX_TMP INCREMENT BY 1 START WITH 1");
            queryNativo.executeUpdate();     
            session.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            errors.add("Error saving records: " + e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
            errors.add("One or more records are not valid, contact the administrator");
            queryNativo = session.createSQLQuery("ROLLBACK");
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("DELETE FROM RVVD_RECLASIF_DIAS_OP_TMP");
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("COMMIT");
            queryNativo.executeUpdate();
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
}
