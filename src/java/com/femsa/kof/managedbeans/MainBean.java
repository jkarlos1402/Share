package com.femsa.kof.managedbeans;

import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "mainBean")
@SessionScoped
public class MainBean implements Serializable {

    private String page = "/WEB-INF/pages/welcome.xhtml";

    private String catalog = "";

    private ShareUsuario usuario;

    public ShareUsuario getUsuario() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario = (ShareUsuario) httpSession.getAttribute("session_user");
        return usuario;
    }

    public void setUsuario(ShareUsuario usuario) {
        this.usuario = usuario;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page, String catalog, String proyecto) {
        this.page = "/WEB-INF/pages/" + proyecto + "/" + page + ".xhtml";
        this.catalog = catalog;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

}
