package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.Rvvd445Ph;
import com.femsa.kof.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Rvvd445PhDAO {

    private List<String> errors = new ArrayList<String>();

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public boolean save445Ph(List<Rvvd445Ph> diasOpPh) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = null;
        List<Object> resultado = null;
        boolean flagOk = true;
        long cont = 0L;
        try {
            session.beginTransaction();
            for (Rvvd445Ph carga : diasOpPh) {
                session.save(carga);
                if (cont % 100 == 0) {
                    session.flush();
                    session.clear();
                }
                cont++;
            }
            query = session.createSQLQuery("SELECT fecha_reasignacion FROM rvvd_445_ph WHERE fecha_reasignacion NOT IN(SELECT pk_tiempo FROM rvvd_dim_tiempo)");
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
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
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
