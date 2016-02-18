package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.Rvvd445Ph;
import com.femsa.kof.daily.pojos.Rvvd445PhTmp;
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
public class Rvvd445PhDAO {

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
     * @return
     */
    public List<Rvvd445Ph> get445Ph() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Rvvd445Ph> diasOpPh = null;
        try {
            Query query = session.createQuery("SELECT do FROM Rvvd445Ph do");
            diasOpPh = query.list();
            errors.clear();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            errors.add(e.getMessage());
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return diasOpPh;
    }

    /**
     *
     * @param diasOpPh
     * @return
     */
    public boolean save445Ph(List<Rvvd445PhTmp> diasOpPh, MainBean mainBean) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = null;
        Query queryNativo = null;
        List<Object> resultado = null;
        boolean flagOk = true;
        long cont = 0L;
        String pais = "";
        try {
            session.beginTransaction();
            for (Rvvd445PhTmp carga : diasOpPh) {
                mainBean.setNumRegistrosProcesados(cont);
                mainBean.setPorcentajeAvance((int) ((cont * 100) / (diasOpPh.size() + 5)));
                pais = carga.getPais();
                session.save(carga);
                if (cont % 100 == 0) {
                    session.flush();
                    session.clear();
                }
                cont++;
            }
            query = session.createSQLQuery("SELECT fecha_reasignacion FROM rvvd_445_ph_tmp WHERE fecha_reasignacion NOT IN(SELECT pk_tiempo FROM rvvd_dim_tiempo)");
            resultado = query.list();
            for (Object object : resultado) {
                String pkTimempo = object.toString();
                query = session.createSQLQuery("INSERT INTO RVVD_DIM_TIEMPO(PK_TIEMPO,GV_ANIO,GV_MES_ANIO,GV_N_MES,GV_DIA) "
                        + "VALUES "
                        + "(" + pkTimempo + "," + pkTimempo.substring(0, 4) + ","
                        + pkTimempo.substring(0, 6) + "," + Integer.parseInt(pkTimempo.substring(4, 6)) + ","
                        + Integer.parseInt(pkTimempo.substring(6, 8)) + ")");
                query.executeUpdate();
                if (cont % 100 == 0) {
                    session.flush();
                    session.clear();
                }
                cont++;
            }            
            errors.clear();
            session.getTransaction().commit();           
            session.beginTransaction();
            queryNativo = session.createSQLQuery("DELETE FROM RVVD_445_PH WHERE PAIS = '" + pais + "' AND FECHA IN (SELECT DISTINCT(FECHA) FROM RVVD_445_PH_TMP)");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (diasOpPh.size() + 5)));
            session.beginTransaction();
            queryNativo = session.createSQLQuery("INSERT INTO RVVD_445_PH(PAIS,FECHA,FECHA_REASIGNACION,FECHA_AA) SELECT PAIS,FECHA,FECHA_REASIGNACION,FECHA_AA FROM RVVD_445_PH_TMP");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (diasOpPh.size() + 5)));
            session.beginTransaction();
            queryNativo = session.createSQLQuery("TRUNCATE TABLE RVVD_445_PH_TMP");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (diasOpPh.size() + 5)));
            session.beginTransaction();
            queryNativo = session.createSQLQuery("DROP SEQUENCE RVVD_SEQ_445_PH_TMP");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (diasOpPh.size() + 5)));
            session.beginTransaction();
            queryNativo = session.createSQLQuery("CREATE SEQUENCE RVVD_SEQ_445_PH_TMP INCREMENT BY 1 START WITH 1");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
            queryNativo = session.createSQLQuery("COMMIT");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (diasOpPh.size() + 5)));
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
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
