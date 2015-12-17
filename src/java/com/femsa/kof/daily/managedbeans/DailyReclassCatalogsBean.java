package com.femsa.kof.daily.managedbeans;

import com.femsa.kof.daily.dao.CatCanalDAO;
import com.femsa.kof.daily.dao.CatCategoriaDAO;
import com.femsa.kof.daily.dao.CatEmpaqueDAO;
import com.femsa.kof.daily.dao.CatTipoConsumoDAO;
import com.femsa.kof.daily.dao.ReclasifCanalDAO;
import com.femsa.kof.daily.dao.ReclasifCategoriaDAO;
import com.femsa.kof.daily.dao.ReclasifEmpaqueDAO;
import com.femsa.kof.daily.pojos.RvvdCatCanal;
import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import com.femsa.kof.daily.pojos.RvvdCatEmpaque;
import com.femsa.kof.daily.pojos.RvvdCatTipoConsumo;
import com.femsa.kof.daily.pojos.RvvdReclasifCanal;
import com.femsa.kof.daily.pojos.RvvdReclasifCategoria;
import com.femsa.kof.daily.pojos.RvvdReclasifEmpaque;
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

    private List<RvvdReclasifCanal> canalesReclasificados = new ArrayList<RvvdReclasifCanal>();
    private List<RvvdCatCanal> catCanales;
    private RvvdCatCanal catCanalSelected;

    private List<RvvdReclasifEmpaque> empaquesReclasificados = new ArrayList<RvvdReclasifEmpaque>();
    private List<RvvdCatTipoConsumo> catTiposConsumo;
    private RvvdCatTipoConsumo tipoConsumoSelected;
    private List<RvvdCatEmpaque> catEmpaques;
    private RvvdCatEmpaque empaqueSelected;

    public DailyReclassCatalogsBean() {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        ReclasifCategoriaDAO reclasifCategoriaDAO = new ReclasifCategoriaDAO();
        categoriasReclasificadas = reclasifCategoriaDAO.getReclasifCategoriasAll(usuario);
        
        ReclasifCanalDAO reclasifCanalDAO = new ReclasifCanalDAO();
        canalesReclasificados = reclasifCanalDAO.getReclasifCanalesAll(usuario);
        
        ReclasifEmpaqueDAO reclasifEmpaqueDAO = new ReclasifEmpaqueDAO();
        empaquesReclasificados = reclasifEmpaqueDAO.getReclasifEmpaquesAll(usuario);
    }

    public List<RvvdReclasifEmpaque> getEmpaquesReclasificados() {
        return empaquesReclasificados;
    }

    public void setEmpaquesReclasificados(List<RvvdReclasifEmpaque> empaquesReclasificados) {
        this.empaquesReclasificados = empaquesReclasificados;
    }

    public List<RvvdCatTipoConsumo> getCatTiposConsumo() {
        CatTipoConsumoDAO tipoConsumoDAO = new CatTipoConsumoDAO();
        catTiposConsumo = tipoConsumoDAO.getTiposConsumo();
        return catTiposConsumo;
    }

    public void setCatTiposConsumo(List<RvvdCatTipoConsumo> catTiposConsumo) {
        this.catTiposConsumo = catTiposConsumo;
    }

    public RvvdCatTipoConsumo getTipoConsumoSelected() {        
        return tipoConsumoSelected;
    }

    public void setTipoConsumoSelected(RvvdCatTipoConsumo tipoConsumoSelected) {
        this.tipoConsumoSelected = tipoConsumoSelected;
    }

    public List<RvvdCatEmpaque> getCatEmpaques() {
        CatEmpaqueDAO empaqueDAO = new CatEmpaqueDAO();
        catEmpaques = empaqueDAO.getEmpaques();
        return catEmpaques;
    }

    public void setCatEmpaques(List<RvvdCatEmpaque> catEmpaques) {
        this.catEmpaques = catEmpaques;
    }

    public RvvdCatEmpaque getEmpaqueSelected() {
        return empaqueSelected;
    }

    public void setEmpaqueSelected(RvvdCatEmpaque empaqueSelected) {
        this.empaqueSelected = empaqueSelected;
    }

    public List<RvvdReclasifCanal> getCanalesReclasificados() {
        return canalesReclasificados;
    }

    public void setCanalesReclasificados(List<RvvdReclasifCanal> canalesReclasificados) {
        this.canalesReclasificados = canalesReclasificados;
    }

    public List<RvvdCatCanal> getCatCanales() {
        CatCanalDAO canalDAO = new CatCanalDAO();
        catCanales = canalDAO.getCanales();
        return catCanales;
    }

    public void setCatCanales(List<RvvdCatCanal> catCanales) {
        this.catCanales = catCanales;
    }

    public RvvdCatCanal getCatCanalSelected() {
        return catCanalSelected;
    }

    public void setCatCanalSelected(RvvdCatCanal catCanalSelected) {
        this.catCanalSelected = catCanalSelected;
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

    public void onRowEditCanal(RowEditEvent event) {
        RvvdReclasifCanal canal = (RvvdReclasifCanal) event.getObject();
        canal.setCanalR(catCanalSelected.getCanalR());
        canal.setCanalEn(catCanalSelected.getCanalEn());
        catCanalSelected = null;
    }

    public void refreshCanalesReclasificados() {  
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        ReclasifCanalDAO reclasifCanalDAO = new ReclasifCanalDAO();
        canalesReclasificados = reclasifCanalDAO.getReclasifCanalesAll(usuario);
    }

    public void saveCanalesReclasificados() {
        FacesMessage message = null;
        ReclasifCanalDAO reclasifCanalDAO = new ReclasifCanalDAO();
        if (reclasifCanalDAO.saveReclasifCanales(canalesReclasificados)) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Reclassified channels saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Reclassified channels, " + reclasifCanalDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void onRowEditEmpaque(RowEditEvent event) {
        RvvdReclasifEmpaque empaque = (RvvdReclasifEmpaque) event.getObject();
        empaque.setEmpaqueR(empaqueSelected.getEmpaqueR());
        empaque.setEmpaqueEn(empaqueSelected.getEmpaqueEn());
        empaque.setTipoConsumoR(tipoConsumoSelected.getTipoConsumoR());
        empaque.setTipoConsumoEn(tipoConsumoSelected.getTipoConsumoEn());
        empaqueSelected = null;
        tipoConsumoSelected = null;
    }

    public void refreshEmpaquesReclasificados() {  
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        ReclasifEmpaqueDAO reclasifEmpaqueDAO = new ReclasifEmpaqueDAO();
        empaquesReclasificados = reclasifEmpaqueDAO.getReclasifEmpaquesAll(usuario);
    }

    public void saveEmpaquesReclasificados() {
        FacesMessage message = null;
        ReclasifEmpaqueDAO reclasifEmpaqueDAO = new ReclasifEmpaqueDAO();
        if (reclasifEmpaqueDAO.saveReclasifEmpaques(empaquesReclasificados)) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Reclassified packaging saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Reclassified packaging, " + reclasifEmpaqueDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
