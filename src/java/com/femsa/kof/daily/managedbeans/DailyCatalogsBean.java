package com.femsa.kof.daily.managedbeans;

import com.femsa.kof.daily.dao.CatCanalDAO;
import com.femsa.kof.daily.dao.CatCategoriaDAO;
import com.femsa.kof.daily.dao.CatCategoriaOficialDAO;
import com.femsa.kof.daily.dao.CatEmpaqueDAO;
import com.femsa.kof.daily.dao.CatTipoConsumoDAO;
import com.femsa.kof.daily.pojos.RvvdCatCanal;
import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import com.femsa.kof.daily.pojos.RvvdCatCategoriaOficial;
import com.femsa.kof.daily.pojos.RvvdCatEmpaque;
import com.femsa.kof.daily.pojos.RvvdCatTipoConsumo;
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

    private RvvdCatCanal canalNuevo = new RvvdCatCanal();
    private RvvdCatCanal canalSelected;
    private List<RvvdCatCanal> catCanalesAll = new ArrayList<RvvdCatCanal>();

    private RvvdCatTipoConsumo tipoConsumoNuevo = new RvvdCatTipoConsumo();
    private RvvdCatTipoConsumo tipoConsumoSelected;
    private List<RvvdCatTipoConsumo> catTiposConsumoAll = new ArrayList<RvvdCatTipoConsumo>();

    private RvvdCatEmpaque empaqueNuevo = new RvvdCatEmpaque();
    private RvvdCatEmpaque empaqueSelected;
    private List<RvvdCatEmpaque> catEmpaquesAll = new ArrayList<RvvdCatEmpaque>();

    public DailyCatalogsBean() {
    }

    public RvvdCatTipoConsumo getTipoConsumoNuevo() {
        return tipoConsumoNuevo;
    }

    public void setTipoConsumoNuevo(RvvdCatTipoConsumo tipoConsumoNuevo) {
        this.tipoConsumoNuevo = tipoConsumoNuevo;
    }

    public RvvdCatTipoConsumo getTipoConsumoSelected() {
        return tipoConsumoSelected;
    }

    public void setTipoConsumoSelected(RvvdCatTipoConsumo tipoConsumoSelected) {
        this.tipoConsumoSelected = tipoConsumoSelected;
    }

    public List<RvvdCatTipoConsumo> getCatTiposConsumoAll() {
        CatTipoConsumoDAO tipoConsumoDAO = new CatTipoConsumoDAO();
        catTiposConsumoAll = tipoConsumoDAO.getTiposConsumoAll();
        return catTiposConsumoAll;
    }

    public void setCatTiposConsumoAll(List<RvvdCatTipoConsumo> catTiposConsumoAll) {
        this.catTiposConsumoAll = catTiposConsumoAll;
    }

    public RvvdCatEmpaque getEmpaqueNuevo() {
        return empaqueNuevo;
    }

    public void setEmpaqueNuevo(RvvdCatEmpaque empaqueNuevo) {
        this.empaqueNuevo = empaqueNuevo;
    }

    public RvvdCatEmpaque getEmpaqueSelected() {
        return empaqueSelected;
    }

    public void setEmpaqueSelected(RvvdCatEmpaque empaqueSelected) {
        this.empaqueSelected = empaqueSelected;
    }

    public List<RvvdCatEmpaque> getCatEmpaquesAll() {
        CatEmpaqueDAO empaqueDAO = new CatEmpaqueDAO();
        catEmpaquesAll = empaqueDAO.getEmpaquesAll();
        return catEmpaquesAll;
    }

    public void setCatEmpaquesAll(List<RvvdCatEmpaque> catEmpaquesAll) {
        this.catEmpaquesAll = catEmpaquesAll;
    }

    public RvvdCatCanal getCanalNuevo() {
        return canalNuevo;
    }

    public void setCanalNuevo(RvvdCatCanal canalNuevo) {
        this.canalNuevo = canalNuevo;
    }

    public RvvdCatCanal getCanalSelected() {
        return canalSelected;
    }

    public void setCanalSelected(RvvdCatCanal canalSelected) {
        this.canalSelected = canalSelected;
    }

    public List<RvvdCatCanal> getCatCanalesAll() {
        CatCanalDAO canalDAO = new CatCanalDAO();
        catCanalesAll = canalDAO.getCanalesAll();
        return catCanalesAll;
    }

    public void setCatCanalesAll(List<RvvdCatCanal> catCanalesAll) {
        this.catCanalesAll = catCanalesAll;
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
    
    public void newChannel() {
        canalNuevo = new RvvdCatCanal();
        canalSelected = null;
    }

    public void selectChannel() {
        canalNuevo.setIdCanal(canalSelected.getIdCanal());
        canalNuevo.setCanalR(canalSelected.getCanalR());
        canalNuevo.setCanalEn(canalSelected.getCanalEn());
        canalNuevo.setStatus(canalSelected.getStatus());
    }

    public void saveChannel() {
        FacesMessage message = null;
        CatCanalDAO canalDAO = new CatCanalDAO();
        if (canalDAO.saveCanal(canalNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Channel saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the channel, " + canalDAO.getError());
            canalNuevo.setIdCanal(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void newConsumption() {
        tipoConsumoNuevo = new RvvdCatTipoConsumo();
        tipoConsumoSelected = null;
    }

    public void selectConsumption() {
        tipoConsumoNuevo.setIdTipoConsumo(tipoConsumoSelected.getIdTipoConsumo());
        tipoConsumoNuevo.setStatus(tipoConsumoSelected.getStatus());
        tipoConsumoNuevo.setTipoConsumoEn(tipoConsumoSelected.getTipoConsumoEn());
        tipoConsumoNuevo.setTipoConsumoR(tipoConsumoSelected.getTipoConsumoR());
    }

    public void saveConsumption() {
        FacesMessage message = null;
        CatTipoConsumoDAO tipoConsumoDAO = new CatTipoConsumoDAO();
        if (tipoConsumoDAO.saveTipoConsumo(tipoConsumoNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Type of Consumption saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the types of Consumption, " + tipoConsumoDAO.getError());
            tipoConsumoNuevo.setIdTipoConsumo(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void newPacking() {
        empaqueNuevo = new RvvdCatEmpaque();
        empaqueSelected = null;
    }

    public void selectPacking() {
        empaqueNuevo.setIdEmpaque(empaqueSelected.getIdEmpaque());
        empaqueNuevo.setEmpaqueEn(empaqueSelected.getEmpaqueEn());
        empaqueNuevo.setEmpaqueR(empaqueSelected.getEmpaqueR());
        empaqueNuevo.setStatus(empaqueSelected.getStatus());
    }

    public void savePacking() {
        FacesMessage message = null;
        CatEmpaqueDAO empaqueDAO = new CatEmpaqueDAO();
        if (empaqueDAO.saveEmpaque(empaqueNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Packing saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the packing, " + empaqueDAO.getError());
            empaqueNuevo.setIdEmpaque(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
