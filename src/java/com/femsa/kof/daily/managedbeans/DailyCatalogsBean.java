package com.femsa.kof.daily.managedbeans;

import com.femsa.kof.daily.dao.CatCanalDAO;
import com.femsa.kof.daily.dao.CatCategoriaDAO;
import com.femsa.kof.daily.dao.CatCategoriaOficialDAO;
import com.femsa.kof.daily.dao.CatContCaloricoDAO;
import com.femsa.kof.daily.dao.CatEmpaqueDAO;
import com.femsa.kof.daily.dao.CatGecDAO;
import com.femsa.kof.daily.dao.CatMarcaDAO;
import com.femsa.kof.daily.dao.CatTipoConsumoDAO;
import com.femsa.kof.daily.dao.CatUnidadNegocioDAO;
import com.femsa.kof.daily.pojos.RvvdCatCanal;
import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import com.femsa.kof.daily.pojos.RvvdCatCategoriaOficial;
import com.femsa.kof.daily.pojos.RvvdCatContenidoCalorico;
import com.femsa.kof.daily.pojos.RvvdCatEmpaque;
import com.femsa.kof.daily.pojos.RvvdCatGec;
import com.femsa.kof.daily.pojos.RvvdCatMarca;
import com.femsa.kof.daily.pojos.RvvdCatTipoConsumo;
import com.femsa.kof.daily.pojos.RvvdCatUnidadNegocio;
import com.femsa.kof.util.CatalogLoader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author TMXIDSJPINAM
 */
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

    private RvvdCatGec gecNuevo = new RvvdCatGec();
    private RvvdCatGec gecSelected;
    private List<RvvdCatGec> catGecsAll = new ArrayList<RvvdCatGec>();
    private List<RvvdCatUnidadNegocio> catUnidades = new ArrayList<RvvdCatUnidadNegocio>();

    private RvvdCatUnidadNegocio unidadNueva = new RvvdCatUnidadNegocio();
    private RvvdCatUnidadNegocio unidadSelected;
    private List<RvvdCatUnidadNegocio> catUnidadesAll = new ArrayList<RvvdCatUnidadNegocio>();

    private RvvdCatMarca marcaNueva = new RvvdCatMarca();
    private RvvdCatMarca marcaSelected;
    private List<RvvdCatMarca> catMarcasAll = new ArrayList<RvvdCatMarca>();

    private RvvdCatContenidoCalorico contenidoNueva = new RvvdCatContenidoCalorico();
    private RvvdCatContenidoCalorico contenidoSelected;
    private List<RvvdCatContenidoCalorico> catContenidosAll = new ArrayList<RvvdCatContenidoCalorico>();

    /**
     *
     */
    public DailyCatalogsBean() {
        CatContCaloricoDAO contCaloricoDAO = new CatContCaloricoDAO();
        catContenidosAll = contCaloricoDAO.getContsCalAll();

        CatMarcaDAO marcaDAO = new CatMarcaDAO();
        catMarcasAll = marcaDAO.getMarcasAll();

        CatGecDAO gecDAO = new CatGecDAO();
        catGecsAll = gecDAO.getGecAll();

        CatUnidadNegocioDAO unidadNegocioDAO = new CatUnidadNegocioDAO();
        catUnidadesAll = unidadNegocioDAO.getUnidadesNegAll();
        catUnidades = unidadNegocioDAO.getUnidadesNeg();

        CatTipoConsumoDAO tipoConsumoDAO = new CatTipoConsumoDAO();
        catTiposConsumoAll = tipoConsumoDAO.getTiposConsumoAll();

        CatEmpaqueDAO empaqueDAO = new CatEmpaqueDAO();
        catEmpaquesAll = empaqueDAO.getEmpaquesAll();

        CatCanalDAO canalDAO = new CatCanalDAO();
        catCanalesAll = canalDAO.getCanalesAll();

        CatCategoriaDAO categoriaDAO = new CatCategoriaDAO();
        catCategoriaAll = categoriaDAO.getCategoriasAll();

        CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
        catCategoriaOficialAll = categoriaOficialDAO.getCategoriasOficialesAll();
    }

    /**
     *
     * @return
     */
    public List<RvvdCatUnidadNegocio> getCatUnidades() {
        return catUnidades;
    }

    /**
     *
     * @param catUnidades
     */
    public void setCatUnidades(List<RvvdCatUnidadNegocio> catUnidades) {
        this.catUnidades = catUnidades;
    }

    /**
     *
     * @return
     */
    public RvvdCatContenidoCalorico getContenidoNueva() {
        return contenidoNueva;
    }

    /**
     *
     * @param contenidoNueva
     */
    public void setContenidoNueva(RvvdCatContenidoCalorico contenidoNueva) {
        this.contenidoNueva = contenidoNueva;
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
    public List<RvvdCatContenidoCalorico> getCatContenidosAll() {
        return catContenidosAll;
    }

    /**
     *
     * @param catContenidosAll
     */
    public void setCatContenidosAll(List<RvvdCatContenidoCalorico> catContenidosAll) {
        this.catContenidosAll = catContenidosAll;
    }

    /**
     *
     * @return
     */
    public RvvdCatMarca getMarcaNueva() {
        return marcaNueva;
    }

    /**
     *
     * @param marcaNueva
     */
    public void setMarcaNueva(RvvdCatMarca marcaNueva) {
        this.marcaNueva = marcaNueva;
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
    public List<RvvdCatMarca> getCatMarcasAll() {
        return catMarcasAll;
    }

    /**
     *
     * @param catMarcasAll
     */
    public void setCatMarcasAll(List<RvvdCatMarca> catMarcasAll) {
        this.catMarcasAll = catMarcasAll;
    }

    /**
     *
     * @return
     */
    public RvvdCatGec getGecNuevo() {
        return gecNuevo;
    }

    /**
     *
     * @param gecNuevo
     */
    public void setGecNuevo(RvvdCatGec gecNuevo) {
        this.gecNuevo = gecNuevo;
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
    public List<RvvdCatGec> getCatGecsAll() {
        return catGecsAll;
    }

    /**
     *
     * @param catGecsAll
     */
    public void setCatGecsAll(List<RvvdCatGec> catGecsAll) {
        this.catGecsAll = catGecsAll;
    }

    /**
     *
     * @return
     */
    public RvvdCatUnidadNegocio getUnidadNueva() {
        return unidadNueva;
    }

    /**
     *
     * @param unidadNueva
     */
    public void setUnidadNueva(RvvdCatUnidadNegocio unidadNueva) {
        this.unidadNueva = unidadNueva;
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
    public List<RvvdCatUnidadNegocio> getCatUnidadesAll() {
        return catUnidadesAll;
    }

    /**
     *
     * @param catUnidadesAll
     */
    public void setCatUnidadesAll(List<RvvdCatUnidadNegocio> catUnidadesAll) {
        this.catUnidadesAll = catUnidadesAll;
    }

    /**
     *
     * @return
     */
    public RvvdCatTipoConsumo getTipoConsumoNuevo() {
        return tipoConsumoNuevo;
    }

    /**
     *
     * @param tipoConsumoNuevo
     */
    public void setTipoConsumoNuevo(RvvdCatTipoConsumo tipoConsumoNuevo) {
        this.tipoConsumoNuevo = tipoConsumoNuevo;
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
    public List<RvvdCatTipoConsumo> getCatTiposConsumoAll() {
        return catTiposConsumoAll;
    }

    /**
     *
     * @param catTiposConsumoAll
     */
    public void setCatTiposConsumoAll(List<RvvdCatTipoConsumo> catTiposConsumoAll) {
        this.catTiposConsumoAll = catTiposConsumoAll;
    }

    /**
     *
     * @return
     */
    public RvvdCatEmpaque getEmpaqueNuevo() {
        return empaqueNuevo;
    }

    /**
     *
     * @param empaqueNuevo
     */
    public void setEmpaqueNuevo(RvvdCatEmpaque empaqueNuevo) {
        this.empaqueNuevo = empaqueNuevo;
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
    public List<RvvdCatEmpaque> getCatEmpaquesAll() {
        return catEmpaquesAll;
    }

    /**
     *
     * @param catEmpaquesAll
     */
    public void setCatEmpaquesAll(List<RvvdCatEmpaque> catEmpaquesAll) {
        this.catEmpaquesAll = catEmpaquesAll;
    }

    /**
     *
     * @return
     */
    public RvvdCatCanal getCanalNuevo() {
        return canalNuevo;
    }

    /**
     *
     * @param canalNuevo
     */
    public void setCanalNuevo(RvvdCatCanal canalNuevo) {
        this.canalNuevo = canalNuevo;
    }

    /**
     *
     * @return
     */
    public RvvdCatCanal getCanalSelected() {
        return canalSelected;
    }

    /**
     *
     * @param canalSelected
     */
    public void setCanalSelected(RvvdCatCanal canalSelected) {
        this.canalSelected = canalSelected;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatCanal> getCatCanalesAll() {
        return catCanalesAll;
    }

    /**
     *
     * @param catCanalesAll
     */
    public void setCatCanalesAll(List<RvvdCatCanal> catCanalesAll) {
        this.catCanalesAll = catCanalesAll;
    }

    /**
     *
     * @return
     */
    public RvvdCatCategoria getCategoriaNueva() {
        return categoriaNueva;
    }

    /**
     *
     * @param categoriaNueva
     */
    public void setCategoriaNueva(RvvdCatCategoria categoriaNueva) {
        this.categoriaNueva = categoriaNueva;
    }

    /**
     *
     * @return
     */
    public RvvdCatCategoria getCategoriaSelected() {
        return categoriaSelected;
    }

    /**
     *
     * @param categoriaSelected
     */
    public void setCategoriaSelected(RvvdCatCategoria categoriaSelected) {
        this.categoriaSelected = categoriaSelected;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatCategoria> getCatCategoriaAll() {
        return catCategoriaAll;
    }

    /**
     *
     * @param catCategoriaAll
     */
    public void setCatCategoriaAll(List<RvvdCatCategoria> catCategoriaAll) {
        this.catCategoriaAll = catCategoriaAll;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatCategoriaOficial> getCatCategoriaOficial() {
        CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
        catCategoriaOficial = categoriaOficialDAO.getCategoriasOficiales();
        return catCategoriaOficial;
    }

    /**
     *
     * @param catCategoriaOficial
     */
    public void setCatCategoriaOficial(List<RvvdCatCategoriaOficial> catCategoriaOficial) {
        this.catCategoriaOficial = catCategoriaOficial;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatCategoriaOficial> getCatCategoriaOficialAll() {
        return catCategoriaOficialAll;
    }

    /**
     *
     * @param catCategoriaOficialAll
     */
    public void setCatCategoriaOficialAll(List<RvvdCatCategoriaOficial> catCategoriaOficialAll) {
        this.catCategoriaOficialAll = catCategoriaOficialAll;
    }

    /**
     *
     * @return
     */
    public RvvdCatCategoriaOficial getCategoriaOficialSelected() {
        return categoriaOficialSelected;
    }

    /**
     *
     * @param categoriaOficialSelected
     */
    public void setCategoriaOficialSelected(RvvdCatCategoriaOficial categoriaOficialSelected) {
        this.categoriaOficialSelected = categoriaOficialSelected;
    }

    /**
     *
     * @return
     */
    public RvvdCatCategoriaOficial getCategoriaOficialNueva() {
        return categoriaOficialNueva;
    }

    /**
     *
     * @param categoriaOficialNueva
     */
    public void setCategoriaOficialNueva(RvvdCatCategoriaOficial categoriaOficialNueva) {
        this.categoriaOficialNueva = categoriaOficialNueva;
    }

    /**
     *
     */
    public void newOfficialCategory() {
        categoriaOficialNueva = new RvvdCatCategoriaOficial();
        categoriaOficialSelected = null;
    }

    /**
     *
     */
    public void selectOfficialCategory() {
        categoriaOficialNueva.setIdCategoriaOficial(categoriaOficialSelected.getIdCategoriaOficial());
        categoriaOficialNueva.setCategoriaOficial(categoriaOficialSelected.getCategoriaOficial());
        categoriaOficialNueva.setCategoriaOficialEn(categoriaOficialSelected.getCategoriaOficialEn());
        categoriaOficialNueva.setRvvdCatCategoriaList(categoriaOficialSelected.getRvvdCatCategoriaList());
        categoriaOficialNueva.setStatus(categoriaOficialSelected.isStatus());
    }

    /**
     *
     */
    public void saveOfficialCategory() {
        FacesMessage message;
        CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
        if (categoriaOficialDAO.saveCategoriaOficial(categoriaOficialNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("categoriaOficial");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Official category saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the official category, " + categoriaOficialDAO.getError());
            categoriaOficialNueva.setIdCategoriaOficial(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void newCategory() {
        categoriaNueva = new RvvdCatCategoria();
        categoriaSelected = null;
    }

    /**
     *
     */
    public void selectCategory() {
        categoriaNueva.setIdCategoria(categoriaSelected.getIdCategoria());
        categoriaNueva.setCategoriaEn(categoriaSelected.getCategoriaEn());
        categoriaNueva.setCategoria(categoriaSelected.getCategoria());
        categoriaNueva.setIdCategoriaOficial(categoriaSelected.getIdCategoriaOficial());
        categoriaNueva.setStatus(categoriaSelected.isStatus());
    }

    /**
     *
     */
    public void saveCategory() {
        FacesMessage message;
        CatCategoriaDAO categoriaDAO = new CatCategoriaDAO();
        if (categoriaDAO.saveCategoria(categoriaNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("categoria");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Category saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the category, " + categoriaDAO.getError());
            categoriaNueva.setIdCategoria(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void newChannel() {
        canalNuevo = new RvvdCatCanal();
        canalSelected = null;
    }

    /**
     *
     */
    public void selectChannel() {
        canalNuevo.setIdCanal(canalSelected.getIdCanal());
        canalNuevo.setCanalR(canalSelected.getCanalR());
        canalNuevo.setCanalEn(canalSelected.getCanalEn());
        canalNuevo.setStatus(canalSelected.getStatus());
    }

    /**
     *
     */
    public void saveChannel() {
        FacesMessage message;
        CatCanalDAO canalDAO = new CatCanalDAO();
        if (canalDAO.saveCanal(canalNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("canal");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Channel saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the channel, " + canalDAO.getError());
            canalNuevo.setIdCanal(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void newConsumption() {
        tipoConsumoNuevo = new RvvdCatTipoConsumo();
        tipoConsumoSelected = null;
    }

    /**
     *
     */
    public void selectConsumption() {
        tipoConsumoNuevo.setIdTipoConsumo(tipoConsumoSelected.getIdTipoConsumo());
        tipoConsumoNuevo.setStatus(tipoConsumoSelected.getStatus());
        tipoConsumoNuevo.setTipoConsumoEn(tipoConsumoSelected.getTipoConsumoEn());
        tipoConsumoNuevo.setTipoConsumoR(tipoConsumoSelected.getTipoConsumoR());
    }

    /**
     *
     */
    public void saveConsumption() {
        FacesMessage message;
        CatTipoConsumoDAO tipoConsumoDAO = new CatTipoConsumoDAO();
        if (tipoConsumoDAO.saveTipoConsumo(tipoConsumoNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("tipoConsumo");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Type of Consumption saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the types of Consumption, " + tipoConsumoDAO.getError());
            tipoConsumoNuevo.setIdTipoConsumo(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void newPacking() {
        empaqueNuevo = new RvvdCatEmpaque();
        empaqueSelected = null;
    }

    /**
     *
     */
    public void selectPacking() {
        empaqueNuevo.setIdEmpaque(empaqueSelected.getIdEmpaque());
        empaqueNuevo.setEmpaqueEn(empaqueSelected.getEmpaqueEn());
        empaqueNuevo.setEmpaqueR(empaqueSelected.getEmpaqueR());
        empaqueNuevo.setStatus(empaqueSelected.getStatus());
    }

    /**
     *
     */
    public void savePacking() {
        FacesMessage message;
        CatEmpaqueDAO empaqueDAO = new CatEmpaqueDAO();
        if (empaqueDAO.saveEmpaque(empaqueNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("empaque");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Packing saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the packing, " + empaqueDAO.getError());
            empaqueNuevo.setIdEmpaque(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void newGec() {
        gecNuevo = new RvvdCatGec();
        gecSelected = null;
    }

    /**
     *
     */
    public void selectGec() {
        gecNuevo.setIdGec(gecSelected.getIdGec());
        gecNuevo.setGecEn(gecSelected.getGecEn());
        gecNuevo.setGecR(gecSelected.getGecR());
        gecNuevo.setStatus(gecSelected.getStatus());
        gecNuevo.setIdUnidadNegocio(gecSelected.getIdUnidadNegocio());
    }

    /**
     *
     */
    public void saveGec() {
        FacesMessage message;
        CatGecDAO gecDAO = new CatGecDAO();
        if (gecDAO.saveGec(gecNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("gec");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Client type saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the client type, " + gecDAO.getError());
            gecNuevo.setIdGec(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void newBussinessUnit() {
        unidadNueva = new RvvdCatUnidadNegocio();
        unidadSelected = null;
    }

    /**
     *
     */
    public void selectBussinessUnit() {
        unidadNueva.setIdUnidadNegocio(unidadSelected.getIdUnidadNegocio());
        unidadNueva.setStatus(unidadSelected.getStatus());
        unidadNueva.setUnidadNegocioEn(unidadSelected.getUnidadNegocioEn());
        unidadNueva.setUnidadNegocioR(unidadSelected.getUnidadNegocioR());
    }

    /**
     *
     */
    public void saveBussinessUnit() {
        FacesMessage message;
        CatUnidadNegocioDAO unidadNegocioDAO = new CatUnidadNegocioDAO();
        if (unidadNegocioDAO.saveUnidadNeg(unidadNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("unidad");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Bussiness unit saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the bussiness unit, " + unidadNegocioDAO.getError());
            unidadNueva.setIdUnidadNegocio(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void newTrademark() {
        marcaNueva = new RvvdCatMarca();
        marcaSelected = null;
    }

    /**
     *
     */
    public void selectTrademark() {
        marcaNueva.setIdMarca(marcaSelected.getIdMarca());
        marcaNueva.setMarcaEn(marcaSelected.getMarcaEn());
        marcaNueva.setMarcaR(marcaSelected.getMarcaR());
        marcaNueva.setStatus(marcaSelected.getStatus());
    }

    /**
     *
     */
    public void saveTrademark() {
        FacesMessage message;
        CatMarcaDAO marcaDAO = new CatMarcaDAO();
        if (marcaDAO.saveMarca(marcaNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("marca");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Trademark saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Trademark, " + marcaDAO.getError());
            marcaNueva.setIdMarca(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void newCalorie() {
        contenidoNueva = new RvvdCatContenidoCalorico();
        contenidoSelected = null;
    }

    /**
     *
     */
    public void selectCalorie() {
        contenidoNueva.setIdContenidoCalorico(contenidoSelected.getIdContenidoCalorico());
        contenidoNueva.setContenidoCaloricoEn(contenidoSelected.getContenidoCaloricoEn());
        contenidoNueva.setContenidoCaloricoR(contenidoSelected.getContenidoCaloricoR());
        contenidoNueva.setStatus(contenidoSelected.getStatus());
    }

    /**
     *
     */
    public void saveCalorie() {
        FacesMessage message;
        CatContCaloricoDAO contCaloricoDAO = new CatContCaloricoDAO();
        if (contCaloricoDAO.saveContCal(contenidoNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("calorico");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Caloric content saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Caloric content, " + contCaloricoDAO.getError());
            marcaNueva.setIdMarca(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void refreshCatalog(String catalog) {
        if ("calorico".equalsIgnoreCase(catalog)) {
            CatContCaloricoDAO contCaloricoDAO = new CatContCaloricoDAO();
            catContenidosAll = contCaloricoDAO.getContsCalAll();
        } else if ("marca".equalsIgnoreCase(catalog)) {
            CatMarcaDAO marcaDAO = new CatMarcaDAO();
            catMarcasAll = marcaDAO.getMarcasAll();
        } else if ("gec".equalsIgnoreCase(catalog)) {
            CatGecDAO gecDAO = new CatGecDAO();
            catGecsAll = gecDAO.getGecAll();
        } else if ("unidad".equalsIgnoreCase(catalog)) {
            CatUnidadNegocioDAO unidadNegocioDAO = new CatUnidadNegocioDAO();
            catUnidadesAll = unidadNegocioDAO.getUnidadesNegAll();
            catUnidades = unidadNegocioDAO.getUnidadesNeg();
        } else if ("tipoConsumo".equalsIgnoreCase(catalog)) {
            CatTipoConsumoDAO tipoConsumoDAO = new CatTipoConsumoDAO();
            catTiposConsumoAll = tipoConsumoDAO.getTiposConsumoAll();
        } else if ("empaque".equalsIgnoreCase(catalog)) {
            CatEmpaqueDAO empaqueDAO = new CatEmpaqueDAO();
            catEmpaquesAll = empaqueDAO.getEmpaquesAll();
        } else if ("canal".equalsIgnoreCase(catalog)) {
            CatCanalDAO canalDAO = new CatCanalDAO();
            catCanalesAll = canalDAO.getCanalesAll();
        } else if ("categoria".equalsIgnoreCase(catalog)) {
            CatCategoriaDAO categoriaDAO = new CatCategoriaDAO();
            catCategoriaAll = categoriaDAO.getCategoriasAll();
        } else if ("categoriaOficial".equalsIgnoreCase(catalog)) {
            CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
            catCategoriaOficialAll = categoriaOficialDAO.getCategoriasOficialesAll();
        }
    }
}
