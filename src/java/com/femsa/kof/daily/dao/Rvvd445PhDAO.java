package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.Rvvd445PhTmp;
import com.femsa.kof.managedbeans.MainBean;
import com.femsa.kof.util.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
     * @param diasOpPh
     * @param mainBean
     * @return
     */
    public boolean save445Ph(List<Rvvd445PhTmp> diasOpPh, MainBean mainBean) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query;
        Query queryNativo;
        List<Object> resultado;
        boolean flagOk = true;
        long cont = 0L;
        String pais = "";
        Object[] pkTimempo;
        String idTiempo;
        Date fecha;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            session.beginTransaction();
            for (Rvvd445PhTmp carga : diasOpPh) {
                mainBean.setNumRegistrosProcesados(cont);
                mainBean.setPorcentajeAvance((int) ((cont * 100) / (diasOpPh.size() + 5)));
                pais = carga.getGvDescPais();
                session.save(carga);
                if (cont % 100 == 0) {
                    session.flush();
                    session.clear();
                }
                cont++;
            }
            session.getTransaction().commit();
            session.beginTransaction();
            query = session.createSQLQuery("SELECT DISTINCT(PK_TIEMPO),GD_FECHA_ACT FROM RVVD_445_PH_TMP WHERE SUBSTR(PK_TIEMPO,7,2) <> '00' AND PK_TIEMPO NOT IN(SELECT PK_TIEMPO FROM RVVD_DIM_TIEMPO)");
            resultado = query.list();
            for (Object object : resultado) {                
                pkTimempo = (Object[]) object;
                idTiempo = pkTimempo[0].toString();
                fecha = (Date) pkTimempo[1];
                query = session.createSQLQuery("INSERT INTO RVVD_DIM_TIEMPO(PK_TIEMPO,GD_FECHA,GV_ANIO,GV_MES_ANIO,GV_N_MES,GV_DIA) "
                        + "VALUES "
                        + "(" + idTiempo + ",'" + sdf.format(fecha) + "'," + idTiempo.substring(0, 4) + ","
                        + idTiempo.substring(0, 6) + "," + Integer.parseInt(idTiempo.substring(4, 6)) + ","
                        + Integer.parseInt(idTiempo.substring(6, 8)) + ")");
                query.executeUpdate();
                if (cont % 100 == 0) {
                    session.flush();
                    session.clear();
                }
                cont++;
            }
            errors.clear();
            queryNativo = session.createSQLQuery("DELETE FROM RVVD_445_PH WHERE GV_DESC_PAIS = '" + pais + "' AND GD_FECHA_ACT IN (SELECT DISTINCT(GD_FECHA_ACT) FROM RVVD_445_PH_TMP)");
            queryNativo.executeUpdate();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (diasOpPh.size() + 5)));
            queryNativo = session.createSQLQuery("INSERT INTO RVVD_445_PH(GV_DESC_PAIS,GD_FECHA_AA,GV_DIA_OP_AA,GD_FECHA_ACT,GV_DIA_OP_ACT,GV_N_MES,PK_TIEMPO,PK_TIEMPO_AA,PK_TIEMPO_ACT) SELECT GV_DESC_PAIS,GD_FECHA_AA,GV_DIA_OP_AA,GD_FECHA_ACT,GV_DIA_OP_ACT,GV_N_MES,PK_TIEMPO,PK_TIEMPO_AA,PK_TIEMPO_ACT FROM RVVD_445_PH_TMP");
            queryNativo.executeUpdate();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (diasOpPh.size() + 5)));
            queryNativo = session.createSQLQuery("DELETE FROM RVVD_445_PH_TMP");
            queryNativo.executeUpdate();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (diasOpPh.size() + 5)));
            queryNativo = session.createSQLQuery("COMMIT");
            queryNativo.executeUpdate();
            session.getTransaction().commit();
            queryNativo = session.createSQLQuery("DROP SEQUENCE RVVD_SEQ_445_PH_TMP");
            queryNativo.executeUpdate();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (diasOpPh.size() + 5)));
            queryNativo = session.createSQLQuery("CREATE SEQUENCE RVVD_SEQ_445_PH_TMP INCREMENT BY 1 START WITH 1");
            queryNativo.executeUpdate();
            mainBean.setPorcentajeAvance((int) ((++cont * 100) / (diasOpPh.size() + 5)));
        } catch (Exception e) {
            queryNativo = session.createSQLQuery("ROLLBACK");
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("COMMIT");
            queryNativo.executeUpdate();
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            flagOk = false;
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            errors.add("Error saving records: " + e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return flagOk;
    }

    public List<String> getDescPaisGeografia() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo = session.createSQLQuery("SELECT GV_DESC_PAIS FROM RVVD_DIM_GEOGRAFIA");
        List<Object> descs = queryNativo.list();
        List<String> descripciones = new ArrayList<String>();
        for (Object desc : descs) {
            descripciones.add(desc.toString());
        }
        return descripciones;
    }
}
