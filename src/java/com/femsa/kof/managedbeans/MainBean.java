package com.femsa.kof.managedbeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "mainBean")
@ViewScoped
public class MainBean implements Serializable{
    private String page = "welcome.xhtml";
    
    private String catalog = "";

    public String getPage() {
        return page;
    }

    public void setPage(String page,String catalog) {
        this.page = page+".xhtml";
        this.catalog = catalog;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
    
}
