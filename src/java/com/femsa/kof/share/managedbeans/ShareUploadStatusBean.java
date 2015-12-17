package com.femsa.kof.share.managedbeans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ShareUploadStatusBean implements Serializable {

    Map<String, String> status;

    @ManagedProperty("#{ShareLoadBean}")
    private ShareLoadBean loadBean;

    public ShareUploadStatusBean() {
    }

    public ShareLoadBean getLoadBean() {
        return loadBean;
    }

    public void setLoadBean(ShareLoadBean loadBean) {
        this.loadBean = loadBean;
    }

    public List<Map.Entry<String, String>> getStatus() {
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        status = new HashMap<String, String>();
        status.put("Project", "SHARE");
        status.put("Process", "SHARE CARGA");
        status.put("Data Entries",loadBean != null && loadBean.getNumEntriesSaved() != null ? loadBean.getNumEntriesSaved() + "" : "N/A");
        status.put("Execution date",loadBean != null && loadBean.getDateExecution() != null ? loadBean.getDateExecution() + "" : "N/A");
        status.put("Start",loadBean != null && loadBean.getDateExecution() != null ? hourFormat.format(loadBean.getDateExecution()) : "N/A");
        status.put("End",loadBean != null && loadBean.getDateEndExecution() != null ? hourFormat.format(loadBean.getDateEndExecution()) : "N/A ");
        Set<Map.Entry<String, String>> statusSet = status.entrySet();
        return new ArrayList<Map.Entry<String, String>>(statusSet);
    }

    public void setStatus(Map<String, String> status) {
        this.status = status;
    }

}
