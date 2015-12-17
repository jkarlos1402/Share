package com.femsa.kof.daily.managedbeans;

import com.femsa.kof.daily.dao.CatCategoriaDAO;
import com.femsa.kof.daily.dao.CatCategoriaOficialDAO;
import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import com.femsa.kof.daily.pojos.RvvdCatCategoriaOficial;
import com.femsa.kof.util.CatalogLoader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class DailyCatalogsBean implements Serializable {

    private RvvdCatCategoriaOficial categoriaOficialNueva = new RvvdCatCategoriaOficial();
    private RvvdCatCategoriaOficial categoriaOficialSelected;
    private List<RvvdCatCategoriaOficial> catCategoriaOficialAll = new ArrayList<RvvdCatCategoriaOficial>();
    private List<RvvdCatCategoriaOficial> catCategoriaOficial = new ArrayList<RvvdCatCategoriaOficial>();

    private RvvdCatCategoria categoriaNueva = new RvvdCatCategoria();
    private RvvdCatCategoria categoriaSelected;
    private List<RvvdCatCategoria> catCategoriaAll = new ArrayList<RvvdCatCategoria>();

    public DailyCatalogsBean() {
    }

    public RvvdCatCategoria getCategoriaNueva() {
        return categoriaNueva;
    }

    public void setCategoriaNueva(RvvdCatCategoria categoriaNueva) {
        this.categoriaNueva = categoriaNueva;
    }

    public RvvdCatCategoria getCategoriaSelected() {
        return categoriaSelected;
    }

    public void setCategoriaSelected(RvvdCatCategoria categoriaSelected) {
        this.categoriaSelected = categoriaSelected;
    }

    public List<RvvdCatCategoria> getCatCategoriaAll() {
        CatCategoriaDAO categoriaDAO = new CatCategoriaDAO();
        catCategoriaAll = categoriaDAO.getCategoriasAll();
        return catCategoriaAll;
    }

    public void setCatCategoriaAll(List<RvvdCatCategoria> catCategoriaAll) {
        this.catCategoriaAll = catCategoriaAll;
    }

    public List<RvvdCatCategoriaOficial> getCatCategoriaOficial() {
        CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
        catCategoriaOficial = categoriaOficialDAO.getCategoriasOficiales();
        return catCategoriaOficial;
    }

    public void setCatCategoriaOficial(List<RvvdCatCategoriaOficial> catCategoriaOficial) {
        this.catCategoriaOficial = catCategoriaOficial;
    }

    public List<RvvdCatCategoriaOficial> getCatCategoriaOficialAll() {
        CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
        catCategoriaOficialAll = categoriaOficialDAO.getCategoriasOficialesAll();
        return catCategoriaOficialAll;
    }

    public void setCatCategoriaOficialAll(List<RvvdCatCategoriaOficial> catCategoriaOficialAll) {
        this.catCategoriaOficialAll = catCategoriaOficialAll;
    }

    public RvvdCatCategoriaOficial getCategoriaOficialSelected() {
        return categoriaOficialSelected;
    }

    public void setCategoriaOficialSelected(RvvdCatCategoriaOficial categoriaOficialSelected) {
        this.categoriaOficialSelected = categoriaOficialSelected;
    }

    public RvvdCatCategoriaOficial getCategoriaOficialNueva() {
        return categoriaOficialNueva;
    }

    public void setCategoriaOficialNueva(RvvdCatCategoriaOficial categoriaOficialNueva) {
        this.categoriaOficialNueva = categoriaOficialNueva;
    }

    public void newOfficialCategory() {
        categoriaOficialNueva = new RvvdCatCategoriaOficial();
        categoriaOficialSelected = null;
    }

    public void selectOfficialCategory() {
        categoriaOficialNueva.setIdCategoriaOficial(categoriaOficialSelected.getIdCategoriaOficial());
        categoriaOficialNueva.setCategoriaOficial(categoriaOficialSelected.getCategoriaOficial());
        categoriaOficialNueva.setCategoriaOficialEn(categoriaOficialSelected.getCategoriaOficialEn());
        categoriaOficialNueva.setRvvdCatCategoriaList(categoriaOficialSelected.getRvvdCatCategoriaList());
        categoriaOficialNueva.setStatus(categoriaOficialSelected.isStatus());
    }

    public void saveOfficialCategory() {
        FacesMessage message = null;
        CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
        if (categoriaOficialDAO.saveCategoriaOficial(categoriaOficialNueva)) {
            CatalogLoader.loadCatalogs("daily");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Official category saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the official category, " + categoriaOficialDAO.getError());
            categoriaOficialNueva.setIdCategoriaOficial(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void newCategory() {
        categoriaNueva = new RvvdCatCategoria();
        categoriaSelected = null;
    }

    public void selectCategory() {
        categoriaNueva.setIdCategoria(categoriaSelected.getIdCategoria());
        categoriaNueva.setCategoriaEn(categoriaSelected.getCategoriaEn());
        categoriaNueva.setCategoria(categoriaSelected.getCategoria());
        categoriaNueva.setIdCategoriaOficial(categoriaSelected.getIdCategoriaOficial());
        categoriaNueva.setStatus(categoriaSelected.isStatus());
    }

    public void saveCategory() {
        FacesMessage message = null;
        CatCategoriaDAO categoriaDAO = new CatCategoriaDAO();
        if (categoriaDAO.saveCategoria(categoriaNueva)) {
            CatalogLoader.loadCatalogs("daily");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Category saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the category, " + categoriaDAO.getError());
            categoriaNueva.setIdCategoria(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
