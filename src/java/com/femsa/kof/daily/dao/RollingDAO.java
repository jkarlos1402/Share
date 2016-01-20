package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RollingDaily;
import com.femsa.kof.daily.pojos.RvvdDistribucionMx;
import com.femsa.kof.daily.pojos.RvvdStRolling;
import com.femsa.kof.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RollingDAO {

    private List<String> errors = new ArrayList<String>();

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public boolean saveDaily(List<RollingDaily> dailys, List<RvvdDistribucionMx> distribuciones) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        long cont = 0L;
        try {
            session.beginTransaction();
            for (RollingDaily carga : dailys) {
                session.save(carga.getDiasOperativos());
                if (carga.getStRollings() != null) {
                    for (RvvdStRolling rolling : carga.getStRollings()) {
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
                for (RvvdDistribucionMx carga : distribuciones) {
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
