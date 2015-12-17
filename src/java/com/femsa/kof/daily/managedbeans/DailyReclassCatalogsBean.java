package com.femsa.kof.daily.managedbeans;

import com.femsa.kof.daily.dao.CatCategoriaDAO;
import com.femsa.kof.daily.dao.ReclasifCategoriaDAO;
import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import com.femsa.kof.daily.pojos.RvvdReclasifCategoria;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@SessionScoped
public class DailyReclassCatalogsBean implements Serializable {

    private List<RvvdReclasifCategoria> categoriasReclasificadas = new ArrayList<RvvdReclasifCategoria>();

    private List<RvvdCatCategoria> catCategorias;
    private RvvdCatCategoria catCategoriaSelected;

    public DailyReclassCatalogsBean() {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        ReclasifCategoriaDAO reclasifCategoriaDAO = new ReclasifCategoriaDAO();
        categoriasReclasificadas = reclasifCategoriaDAO.getReclasifCategoriasAll(usuario);
    }

    public RvvdCatCategoria getCatCategoriaSelected() {
        return catCategoriaSelected;
    }

    public void setCatCategoriaSelected(RvvdCatCategoria catCategoriaSelected) {
        this.catCategoriaSelected = catCategoriaSelected;
    }

    public List<RvvdCatCategoria> getCatCategorias() {
        CatCategoriaDAO categoriaDAO = new CatCategoriaDAO();
        catCategorias = categoriaDAO.getCategorias();
        return catCategorias;
    }

    public void setCatCategorias(List<RvvdCatCategoria> catCategorias) {
        this.catCategorias = catCategorias;
    }

    public List<RvvdReclasifCategoria> getCategoriasReclasificadas() {
        return categoriasReclasificadas;
    }

    public void setCategoriasReclasificadas(List<RvvdReclasifCategoria> categoriasReclasificadas) {
        this.categoriasReclasificadas = categoriasReclasificadas;
    }

    public void onRowEdit(RowEditEvent event) {
        RvvdReclasifCategoria categoria = (RvvdReclasifCategoria) event.getObject();
        categoria.setCategoriaR(catCategoriaSelected.getCategoria());
        categoria.setCategoriaEn(catCategoriaSelected.getCategoriaEn());
        categoria.setCategoriaOficialR(catCategoriaSelected.getIdCategoriaOficial().getCategoriaOficial());
        categoria.setCategoriaOficialEn(catCategoriaSelected.getIdCategoriaOficial().getCategoriaOficialEn());
        catCategoriaSelected = null;
    }

    public void refreshCategoriasReclasificadas() {  
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        ReclasifCategoriaDAO reclasifCategoriaDAO = new ReclasifCategoriaDAO();
        categoriasReclasificadas = reclasifCategoriaDAO.getReclasifCategoriasAll(usuario);
    }

    public void saveCategoriasReclasificadas() {
        FacesMessage message = null;
        ReclasifCategoriaDAO reclasifCategoriaDAO = new ReclasifCategoriaDAO();
        if (reclasifCategoriaDAO.saveReclasifCategorias(categoriasReclasificadas)) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Reclassified categories saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Reclassified categories, " + reclasifCategoriaDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
