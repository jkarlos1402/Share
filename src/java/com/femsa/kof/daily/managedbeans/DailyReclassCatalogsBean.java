package com.femsa.kof.daily.managedbeans;

import com.femsa.kof.daily.dao.ReclasifCanalDAO;
import com.femsa.kof.daily.dao.ReclasifCategoriaDAO;
import com.femsa.kof.daily.dao.ReclasifDiasOpDAO;
import com.femsa.kof.daily.dao.ReclasifEmpaqueDAO;
import com.femsa.kof.daily.dao.ReclasifGecDAO;
import com.femsa.kof.daily.dao.ReclasifMarcaDAO;
import com.femsa.kof.daily.pojos.RvvdCatCanal;
import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import com.femsa.kof.daily.pojos.RvvdCatContenidoCalorico;
import com.femsa.kof.daily.pojos.RvvdCatEmpaque;
import com.femsa.kof.daily.pojos.RvvdCatGec;
import com.femsa.kof.daily.pojos.RvvdCatMarca;
import com.femsa.kof.daily.pojos.RvvdCatTipoConsumo;
import com.femsa.kof.daily.pojos.RvvdCatUnidadNegocio;
import com.femsa.kof.daily.pojos.RvvdReclasifCanal;
import com.femsa.kof.daily.pojos.RvvdReclasifCategoria;
import com.femsa.kof.daily.pojos.RvvdReclasifDiasOp;
import com.femsa.kof.daily.pojos.RvvdReclasifEmpaque;
import com.femsa.kof.daily.pojos.RvvdReclasifMarca;
import com.femsa.kof.daily.pojos.RvvdReclasifUnGec;
import com.femsa.kof.daily.util.CheckCatalogs;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author TMXIDSJPINAM
 */
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
    
    private SimpleDateFormat formatDay = new SimpleDateFormat("dd/MM/yy");

    private List<RvvdReclasifUnGec> gecsReclasificados = new ArrayList<RvvdReclasifUnGec>();
    private List<RvvdCatGec> catGecs;
    private RvvdCatGec gecSelected;
    private List<RvvdCatUnidadNegocio> catUnidadesNegocio;
    private RvvdCatUnidadNegocio unidadSelected; 
    
    private List<RvvdReclasifMarca> marcasReclasificados = new ArrayList<RvvdReclasifMarca>();
    private List<RvvdCatMarca> catMarcas;
    private RvvdCatMarca marcaSelected;
    private List<RvvdCatContenidoCalorico> catContenidosCaloricos;
    private RvvdCatContenidoCalorico contenidoSelected; 
    
    private List<RvvdReclasifDiasOp> diasOpReclasificados = new ArrayList<RvvdReclasifDiasOp>();       

    /**
     *
     */
    public DailyReclassCatalogsBean() {
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        ReclasifCategoriaDAO reclasifCategoriaDAO = new ReclasifCategoriaDAO();
        categoriasReclasificadas = reclasifCategoriaDAO.getReclasifCategoriasAll(usuario);
        
        ReclasifCanalDAO reclasifCanalDAO = new ReclasifCanalDAO();
        canalesReclasificados = reclasifCanalDAO.getReclasifCanalesAll(usuario);
        
        ReclasifEmpaqueDAO reclasifEmpaqueDAO = new ReclasifEmpaqueDAO();
        empaquesReclasificados = reclasifEmpaqueDAO.getReclasifEmpaquesAll(usuario);
        
        ReclasifGecDAO reclasifGecDAO = new ReclasifGecDAO();
        gecsReclasificados = reclasifGecDAO.getReclasifUnGecAll(usuario);
        
        ReclasifDiasOpDAO reclasifDiasOpDAO = new ReclasifDiasOpDAO();
        diasOpReclasificados = reclasifDiasOpDAO.getReclasifDiasOpAll(usuario);
        
        ReclasifMarcaDAO reclasifMarcaDAO = new ReclasifMarcaDAO();
        marcasReclasificados = reclasifMarcaDAO.getReclasifMarcasAll(usuario);
                
        catCategorias = (List<RvvdCatCategoria>)context.getAttribute("categoria_daily_catalog");
                
        catMarcas = (List<RvvdCatMarca>)context.getAttribute("marca_daily_catalog");
                
        catContenidosCaloricos = (List<RvvdCatContenidoCalorico>)context.getAttribute("contCalorico_daily_catalog");
                         
        catGecs = (List<RvvdCatGec>)context.getAttribute("gec_daily_catalog");
                        
        catUnidadesNegocio = (List<RvvdCatUnidadNegocio>)context.getAttribute("unidadNegocio_daily_catalog");
                       
        catTiposConsumo = (List<RvvdCatTipoConsumo>)context.getAttribute("tipoConsumo_daily_catalog");
                       
        catEmpaques = (List<RvvdCatEmpaque>)context.getAttribute("empaque_daily_catalog");
                       
        catCanales = (List<RvvdCatCanal>)context.getAttribute("canal_daily_catalog");
    }

    /**
     *
     * @return
     */
    public List<RvvdReclasifMarca> getMarcasReclasificados() {
        return marcasReclasificados;
    }

    /**
     *
     * @param marcasReclasificados
     */
    public void setMarcasReclasificados(List<RvvdReclasifMarca> marcasReclasificados) {
        this.marcasReclasificados = marcasReclasificados;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatMarca> getCatMarcas() {
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        catMarcas = (List<RvvdCatMarca>)context.getAttribute("marca_daily_catalog");
        return catMarcas;
    }

    /**
     *
     * @param catMarcas
     */
    public void setCatMarcas(List<RvvdCatMarca> catMarcas) {
        this.catMarcas = catMarcas;
    }

    /**
     *
     * @return
     */
    public RvvdCatMarca getMarcaSelected() {
        return marcaSelected;
    }

    /**
     *
     * @param marcaSelected
     */
    public void setMarcaSelected(RvvdCatMarca marcaSelected) {
        this.marcaSelected = marcaSelected;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatContenidoCalorico> getCatContenidosCaloricos() {  
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        catContenidosCaloricos = (List<RvvdCatContenidoCalorico>)context.getAttribute("contCalorico_daily_catalog");
        return catContenidosCaloricos;
    }

    /**
     *
     * @param catContenidosCaloricos
     */
    public void setCatContenidosCaloricos(List<RvvdCatContenidoCalorico> catContenidosCaloricos) {
        this.catContenidosCaloricos = catContenidosCaloricos;
    }

    /**
     *
     * @return
     */
    public RvvdCatContenidoCalorico getContenidoSelected() {
        return contenidoSelected;
    }

    /**
     *
     * @param contenidoSelected
     */
    public void setContenidoSelected(RvvdCatContenidoCalorico contenidoSelected) {
        this.contenidoSelected = contenidoSelected;
    }

    /**
     *
     * @return
     */
    public SimpleDateFormat getFormatDay() {
        return formatDay;
    }

    /**
     *
     * @param formatDay
     */
    public void setFormatDay(SimpleDateFormat formatDay) {
        this.formatDay = formatDay;
    }

    /**
     *
     * @return
     */
    public List<RvvdReclasifDiasOp> getDiasOpReclasificados() {
        return diasOpReclasificados;
    }

    /**
     *
     * @param diasOpReclasificados
     */
    public void setDiasOpReclasificados(List<RvvdReclasifDiasOp> diasOpReclasificados) {
        this.diasOpReclasificados = diasOpReclasificados;
    }

    /**
     *
     * @return
     */
    public List<RvvdReclasifUnGec> getGecsReclasificados() {
        return gecsReclasificados;
    }

    /**
     *
     * @param gecsReclasificados
     */
    public void setGecsReclasificados(List<RvvdReclasifUnGec> gecsReclasificados) {
        this.gecsReclasificados = gecsReclasificados;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatGec> getCatGecs() {
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        catGecs = (List<RvvdCatGec>)context.getAttribute("gec_daily_catalog");
        return catGecs;
    }

    /**
     *
     * @param catGecs
     */
    public void setCatGecs(List<RvvdCatGec> catGecs) {
        this.catGecs = catGecs;
    }

    /**
     *
     * @return
     */
    public RvvdCatGec getGecSelected() {
        return gecSelected;
    }

    /**
     *
     * @param gecSelected
     */
    public void setGecSelected(RvvdCatGec gecSelected) {
        this.gecSelected = gecSelected;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatUnidadNegocio> getCatUnidadesNegocio() {
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        catUnidadesNegocio = (List<RvvdCatUnidadNegocio>)context.getAttribute("unidadNegocio_daily_catalog");
        return catUnidadesNegocio;
    }

    /**
     *
     * @param catUnidadesNegocio
     */
    public void setCatUnidadesNegocio(List<RvvdCatUnidadNegocio> catUnidadesNegocio) {
        this.catUnidadesNegocio = catUnidadesNegocio;
    }

    /**
     *
     * @return
     */
    public RvvdCatUnidadNegocio getUnidadSelected() {
        return unidadSelected;
    }

    /**
     *
     * @param unidadSelected
     */
    public void setUnidadSelected(RvvdCatUnidadNegocio unidadSelected) {
        this.unidadSelected = unidadSelected;
    }

    /**
     *
     * @return
     */
    public List<RvvdReclasifEmpaque> getEmpaquesReclasificados() {
        return empaquesReclasificados;
    }

    /**
     *
     * @param empaquesReclasificados
     */
    public void setEmpaquesReclasificados(List<RvvdReclasifEmpaque> empaquesReclasificados) {
        this.empaquesReclasificados = empaquesReclasificados;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatTipoConsumo> getCatTiposConsumo() {
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        catTiposConsumo = (List<RvvdCatTipoConsumo>)context.getAttribute("tipoConsumo_daily_catalog");
        return catTiposConsumo;
    }

    /**
     *
     * @param catTiposConsumo
     */
    public void setCatTiposConsumo(List<RvvdCatTipoConsumo> catTiposConsumo) {
        this.catTiposConsumo = catTiposConsumo;
    }

    /**
     *
     * @return
     */
    public RvvdCatTipoConsumo getTipoConsumoSelected() {        
        return tipoConsumoSelected;
    }

    /**
     *
     * @param tipoConsumoSelected
     */
    public void setTipoConsumoSelected(RvvdCatTipoConsumo tipoConsumoSelected) {
        this.tipoConsumoSelected = tipoConsumoSelected;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatEmpaque> getCatEmpaques() {
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        catEmpaques = (List<RvvdCatEmpaque>)context.getAttribute("empaque_daily_catalog");
        return catEmpaques;
    }

    /**
     *
     * @param catEmpaques
     */
    public void setCatEmpaques(List<RvvdCatEmpaque> catEmpaques) {
        this.catEmpaques = catEmpaques;
    }

    /**
     *
     * @return
     */
    public RvvdCatEmpaque getEmpaqueSelected() {
        return empaqueSelected;
    }

    /**
     *
     * @param empaqueSelected
     */
    public void setEmpaqueSelected(RvvdCatEmpaque empaqueSelected) {
        this.empaqueSelected = empaqueSelected;
    }

    /**
     *
     * @return
     */
    public List<RvvdReclasifCanal> getCanalesReclasificados() {
        return canalesReclasificados;
    }

    /**
     *
     * @param canalesReclasificados
     */
    public void setCanalesReclasificados(List<RvvdReclasifCanal> canalesReclasificados) {
        this.canalesReclasificados = canalesReclasificados;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatCanal> getCatCanales() {
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        catCanales = (List<RvvdCatCanal>)context.getAttribute("canal_daily_catalog");
        return catCanales;
    }

    /**
     *
     * @param catCanales
     */
    public void setCatCanales(List<RvvdCatCanal> catCanales) {
        this.catCanales = catCanales;
    }

    /**
     *
     * @return
     */
    public RvvdCatCanal getCatCanalSelected() {
        return catCanalSelected;
    }

    /**
     *
     * @param catCanalSelected
     */
    public void setCatCanalSelected(RvvdCatCanal catCanalSelected) {
        this.catCanalSelected = catCanalSelected;
    }

    /**
     *
     * @return
     */
    public RvvdCatCategoria getCatCategoriaSelected() {
        return catCategoriaSelected;
    }

    /**
     *
     * @param catCategoriaSelected
     */
    public void setCatCategoriaSelected(RvvdCatCategoria catCategoriaSelected) {
        this.catCategoriaSelected = catCategoriaSelected;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatCategoria> getCatCategorias() {  
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        catCategorias = (List<RvvdCatCategoria>)context.getAttribute("categoria_daily_catalog");
        return catCategorias;
    }

    /**
     *
     * @param catCategorias
     */
    public void setCatCategorias(List<RvvdCatCategoria> catCategorias) {
        this.catCategorias = catCategorias;
    }

    /**
     *
     * @return
     */
    public List<RvvdReclasifCategoria> getCategoriasReclasificadas() {
        return categoriasReclasificadas;
    }

    /**
     *
     * @param categoriasReclasificadas
     */
    public void setCategoriasReclasificadas(List<RvvdReclasifCategoria> categoriasReclasificadas) {
        this.categoriasReclasificadas = categoriasReclasificadas;
    }

    /**
     *
     * @param event
     */
    public void onRowEdit(RowEditEvent event) {
        RvvdReclasifCategoria categoria = (RvvdReclasifCategoria) event.getObject();
        categoria.setCategoriaR(catCategoriaSelected.getCategoria());
        categoria.setCategoriaEn(catCategoriaSelected.getCategoriaEn());
        categoria.setCategoriaOficialR(catCategoriaSelected.getIdCategoriaOficial().getCategoriaOficial());
        categoria.setCategoriaOficialEn(catCategoriaSelected.getIdCategoriaOficial().getCategoriaOficialEn());
        catCategoriaSelected = null;
    }

    /**
     *
     */
    public void refreshCategoriasReclasificadas() {  
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        ReclasifCategoriaDAO reclasifCategoriaDAO = new ReclasifCategoriaDAO();
        categoriasReclasificadas = reclasifCategoriaDAO.getReclasifCategoriasAll(usuario);
        CheckCatalogs.checkAllCatalogs();
    }

    /**
     *
     */
    public void saveCategoriasReclasificadas() {
        FacesMessage message;
        ReclasifCategoriaDAO reclasifCategoriaDAO = new ReclasifCategoriaDAO();
        if (reclasifCategoriaDAO.saveReclasifCategorias(categoriasReclasificadas != null ? categoriasReclasificadas : new ArrayList<RvvdReclasifCategoria>())) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Reclassified categories saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Reclassified categories, " + reclasifCategoriaDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     * @param event
     */
    public void onRowEditCanal(RowEditEvent event) {
        RvvdReclasifCanal canal = (RvvdReclasifCanal) event.getObject();
        canal.setCanalR(catCanalSelected.getCanalR());
        canal.setCanalEn(catCanalSelected.getCanalEn());
        catCanalSelected = null;
    }

    /**
     *
     */
    public void refreshCanalesReclasificados() {  
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        ReclasifCanalDAO reclasifCanalDAO = new ReclasifCanalDAO();
        canalesReclasificados = reclasifCanalDAO.getReclasifCanalesAll(usuario);
        CheckCatalogs.checkAllCatalogs();
    }

    /**
     *
     */
    public void saveCanalesReclasificados() {
        FacesMessage message;
        ReclasifCanalDAO reclasifCanalDAO = new ReclasifCanalDAO();
        if (reclasifCanalDAO.saveReclasifCanales(canalesReclasificados != null ? canalesReclasificados :  new ArrayList<RvvdReclasifCanal>())) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Reclassified channels saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Reclassified channels, " + reclasifCanalDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    /**
     *
     * @param event
     */
    public void onRowEditEmpaque(RowEditEvent event) {
        RvvdReclasifEmpaque empaque = (RvvdReclasifEmpaque) event.getObject();
        empaque.setEmpaqueR(empaqueSelected.getEmpaqueR());
        empaque.setEmpaqueEn(empaqueSelected.getEmpaqueEn());
        empaque.setTipoConsumoR(tipoConsumoSelected.getTipoConsumoR());
        empaque.setTipoConsumoEn(tipoConsumoSelected.getTipoConsumoEn());
        empaqueSelected = null;
        tipoConsumoSelected = null;
    }

    /**
     *
     */
    public void refreshEmpaquesReclasificados() {  
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        ReclasifEmpaqueDAO reclasifEmpaqueDAO = new ReclasifEmpaqueDAO();
        empaquesReclasificados = reclasifEmpaqueDAO.getReclasifEmpaquesAll(usuario);
        CheckCatalogs.checkAllCatalogs();
    }

    /**
     *
     */
    public void saveEmpaquesReclasificados() {
        FacesMessage message;
        ReclasifEmpaqueDAO reclasifEmpaqueDAO = new ReclasifEmpaqueDAO();
        if (reclasifEmpaqueDAO.saveReclasifEmpaques(empaquesReclasificados != null ? empaquesReclasificados : new ArrayList<RvvdReclasifEmpaque>())) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Reclassified packaging saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Reclassified packaging, " + reclasifEmpaqueDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    /**
     *
     * @param event
     */
    public void onRowEditMarca(RowEditEvent event) {
        RvvdReclasifMarca marca = (RvvdReclasifMarca) event.getObject();
        marca.setMarcaR(marcaSelected.getMarcaR());
        marca.setMarcaEn(marcaSelected.getMarcaEn());
        marca.setContenidoCaloricoR(contenidoSelected.getContenidoCaloricoR());
        marca.setContenidoCaloricoEn(contenidoSelected.getContenidoCaloricoEn());
        marcaSelected = null;
        contenidoSelected = null;
    }

    /**
     *
     */
    public void refreshMarcasReclasificados() {  
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        ReclasifMarcaDAO marcaDAO = new ReclasifMarcaDAO();
        marcasReclasificados = marcaDAO.getReclasifMarcasAll(usuario);
        CheckCatalogs.checkAllCatalogs();
    }

    /**
     *
     */
    public void saveMarcasReclasificados() {
        FacesMessage message;
        ReclasifMarcaDAO  reclasifMarcaDAO = new ReclasifMarcaDAO();
        if (reclasifMarcaDAO.saveReclasifMarcas(marcasReclasificados != null ? marcasReclasificados : new ArrayList<RvvdReclasifMarca>())) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Reclassified Brands saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Reclassified Brands, " + reclasifMarcaDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    /**
     *
     * @param event
     */
    public void onRowEditGec(RowEditEvent event) {
        RvvdReclasifUnGec gec = (RvvdReclasifUnGec) event.getObject();
        gec.setGecR(gecSelected.getGecR());
        gec.setGecEn(gecSelected.getGecEn());
        gec.setUnidadNegocioR(unidadSelected.getUnidadNegocioR());
        gec.setUnidadNegocioEn(unidadSelected.getUnidadNegocioEn());
        gecSelected = null;
        unidadSelected = null;
    }

    /**
     *
     */
    public void refreshGecsReclasificados() {  
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        ReclasifGecDAO reclasifGecDAO = new ReclasifGecDAO();
        gecsReclasificados = reclasifGecDAO.getReclasifUnGecAll(usuario);
        CheckCatalogs.checkAllCatalogs();
    }

    /**
     *
     */
    public void saveGecsReclasificados() {
        FacesMessage message;
        ReclasifGecDAO reclasifGecDAO = new ReclasifGecDAO();
        if (reclasifGecDAO.saveReclasifUnGec(gecsReclasificados != null ? gecsReclasificados : new ArrayList<RvvdReclasifUnGec>())) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Reclassified client types saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Reclassified client types, " + reclasifGecDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }        
    
    /**
     *
     */
    public void refreshDiasOpReclasificados() {  
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        ReclasifDiasOpDAO diasOpDAO = new ReclasifDiasOpDAO();
        diasOpReclasificados = diasOpDAO.getReclasifDiasOpAll(usuario);
        CheckCatalogs.checkAllCatalogs();
    }

    /**
     *
     */
    public void saveDiasOpReclasificados() {
        FacesMessage message;
        ReclasifDiasOpDAO diasOpDAO = new ReclasifDiasOpDAO();
        if (diasOpDAO.saveReclasifDiasOp(diasOpReclasificados != null ? diasOpReclasificados : new ArrayList<RvvdReclasifDiasOp>())) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Operative days saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Reclassified Operative days, " + diasOpDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
