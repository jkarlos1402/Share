package com.femsa.kof.share.managedbeans;

import com.femsa.kof.util.Record;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author TMXIDSJPINAM
 */
@ManagedBean
@SessionScoped
public class ShareUploadStatusBean implements Serializable {
    
    private List<Record> cargasSession = new ArrayList<Record>();
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    /**
     *
     */
    public ShareUploadStatusBean() {
    }

    /**
     *
     * @return
     */
    public SimpleDateFormat getDateTimeFormat() {
        return dateTimeFormat;
    }

    /**
     *
     * @param dateTimeFormat
     */
    public void setDateTimeFormat(SimpleDateFormat dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    /**
     *
     * @return
     */
    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     *
     * @param dateFormat
     */
    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     *
     * @return
     */
    public List<Record> getCargasSession() {
        return cargasSession;
    }

    /**
     *
     * @param cargasSession
     */
    public void setCargasSession(List<Record> cargasSession) {
        this.cargasSession = cargasSession;
    }

    /**
     *
     * @param carga
     */
    public void addRecord(Record carga){
        cargasSession.add(carga);
    }

}
