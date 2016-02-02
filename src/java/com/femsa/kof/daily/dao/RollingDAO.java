package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RollingDaily;
import com.femsa.kof.daily.pojos.RvvdDistribucionMx;
import com.femsa.kof.daily.pojos.RvvdDistribucionMxTmp;
import com.femsa.kof.daily.pojos.RvvdStRolling;
import com.femsa.kof.daily.pojos.RvvdStRollingTmp;
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
public class RollingDAO {

    private List<String> errors = new ArrayList<String>();

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
     * @return
     */
    public boolean saveDaily(List<RollingDaily> dailys, List<RvvdDistribucionMxTmp> distribuciones) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo = null;
        boolean flagOk = true;
        long cont = 0L;
        String pais = "";
        try {
            session.beginTransaction();
            for (RollingDaily carga : dailys) {
                pais = carga.getDiasOperativos().getPais();
                session.save(carga.getDiasOperativos());
                if (carga.getStRollings() != null) {
                    for (RvvdStRollingTmp rolling : carga.getStRollings()) {
                        session.save(rolling);
                        cont++;
                    }
                }
                if (cont % 100 == 0) {
                    session.flush();
                    session.clear();
                }
                cont++;
            }
            if (distribuciones != null) {
                for (RvvdDistribucionMxTmp carga : distribuciones) {
                    session.save(carga);
                    if (cont % 100 == 0) {
                        session.flush();
                        session.clear();
                    }
                    cont++;
                }
            }
            errors.clear();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("DELETE FROM RVVD_RECLASIF_DIAS_OP WHERE PAIS = '" + pais + "' AND FECHA IN (SELECT DISTINCT(FECHA) FROM RVVD_RECLASIF_DIAS_OP_TMP)");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("INSERT INTO RVVD_RECLASIF_DIAS_OP(PAIS,FECHA,FECHA_R) SELECT PAIS,FECHA,FECHA_R FROM RVVD_RECLASIF_DIAS_OP_TMP");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("TRUNCATE TABLE RVVD_RECLASIF_DIAS_OP_TMP");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("DROP SEQUENCE RVVD_SEQ_RECLASIF_DIAS_OP_TMP");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("CREATE SEQUENCE RVVD_SEQ_RECLASIF_DIAS_OP_TMP INCREMENT BY 1 START WITH 1");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("DELETE FROM RVVD_ST_ROLLING WHERE PAIS = '" + pais + "' AND FECHA IN (SELECT DISTINCT(FECHA) FROM RVVD_ST_ROLLING_TMP)");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("INSERT INTO RVVD_ST_ROLLING(ANIO,MES,DIA,FECHA,PAIS,DESC_PAIS,ZONA,CATEGORIA,CATEGORIA_OFICIAL_R,CATEGORIA_OFICIAL_EN,GEC,ROLLING_CU,ROLLING_INGRESO,ROLLING_TA) SELECT ANIO,MES,DIA,FECHA,PAIS,DESC_PAIS,ZONA,CATEGORIA,CATEGORIA_OFICIAL_R,CATEGORIA_OFICIAL_EN,GEC,ROLLING_CU,ROLLING_INGRESO,ROLLING_TA FROM RVVD_ST_ROLLING_TMP");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("TRUNCATE TABLE RVVD_ST_ROLLING_TMP");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("DROP SEQUENCE RVVD_SEQ_ST_ROLLING_TMP");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("CREATE SEQUENCE RVVD_SEQ_ST_ROLLING_TMP INCREMENT BY 1 START WITH 1");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            if (distribuciones != null) {
                session.beginTransaction();
                queryNativo = session.createSQLQuery("DELETE FROM RVVD_DISTRIBUCION_MX WHERE PAIS = '" + pais + "' AND FECHA_ORIGEN IN (SELECT DISTINCT(FECHA_ORIGEN) FROM RVVD_DISTRIBUCION_MX_TMP)");
                queryNativo.executeUpdate();
                session.getTransaction().commit();
                session.beginTransaction();
                queryNativo = session.createSQLQuery("INSERT INTO RVVD_DISTRIBUCION_MX(PAIS,FECHA_ORIGEN,FECHA_DESTINO,PORCENTAJE) SELECT PAIS,FECHA_ORIGEN,FECHA_DESTINO,PORCENTAJE FROM RVVD_DISTRIBUCION_MX_TMP");
                queryNativo.executeUpdate();
                session.getTransaction().commit();
                session.beginTransaction();
                queryNativo = session.createSQLQuery("TRUNCATE TABLE RVVD_DISTRIBUCION_MX_TMP");
                queryNativo.executeUpdate();
                session.getTransaction().commit();
                session.beginTransaction();
                queryNativo = session.createSQLQuery("DROP SEQUENCE RVVD_SEQ_DISTRIBUCION_MX_TMP");
                queryNativo.executeUpdate();
                session.getTransaction().commit();
                session.beginTransaction();
                queryNativo = session.createSQLQuery("CREATE SEQUENCE RVVD_SEQ_DISTRIBUCION_MX_TMP INCREMENT BY 1 START WITH 1");
                queryNativo.executeUpdate();
                session.getTransaction().commit();
            }
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
}
