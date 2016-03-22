package com.femsa.kof.daily.managedbeans;

import com.femsa.kof.daily.dao.CatCanalDAO;
import com.femsa.kof.daily.dao.CatCategoriaDAO;
import com.femsa.kof.daily.dao.CatCategoriaOficialDAO;
import com.femsa.kof.daily.dao.CatContCaloricoDAO;
import com.femsa.kof.daily.dao.CatEmpaqueDAO;
import com.femsa.kof.daily.dao.CatGecDAO;
import com.femsa.kof.daily.dao.CatMarcaDAO;
import com.femsa.kof.daily.dao.CatRetornabilidadDAO;
import com.femsa.kof.daily.dao.CatSubCanalDAO;
import com.femsa.kof.daily.dao.CatTipoConsumoDAO;
import com.femsa.kof.daily.dao.CatUnidadNegocioDAO;
import com.femsa.kof.daily.dao.CatZonaDAO;
import com.femsa.kof.daily.pojos.RvvdCatCanal;
import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import com.femsa.kof.daily.pojos.RvvdCatCategoriaOficial;
import com.femsa.kof.daily.pojos.RvvdCatContenidoCalorico;
import com.femsa.kof.daily.pojos.RvvdCatEmpaque;
import com.femsa.kof.daily.pojos.RvvdCatGec;
import com.femsa.kof.daily.pojos.RvvdCatMarca;
import com.femsa.kof.daily.pojos.RvvdCatRetornabilidad;
import com.femsa.kof.daily.pojos.RvvdCatSubCanal;
import com.femsa.kof.daily.pojos.RvvdCatTipoConsumo;
import com.femsa.kof.daily.pojos.RvvdCatUnidadNegocio;
import com.femsa.kof.daily.pojos.RvvdCatZona;
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
    private List<RvvdCatCanal> catCanales = new ArrayList<RvvdCatCanal>();

    private RvvdCatSubCanal subCanalNuevo = new RvvdCatSubCanal();
    private RvvdCatSubCanal subCanalSelected;
    private List<RvvdCatSubCanal> catSubCanalesAll = new ArrayList<RvvdCatSubCanal>();

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

    private RvvdCatZona zonaNueva = new RvvdCatZona();
    private RvvdCatZona zonaSelected;
    private List<RvvdCatZona> catZonasAll = new ArrayList<RvvdCatZona>();

    private RvvdCatRetornabilidad retornabilidadNueva = new RvvdCatRetornabilidad();
    private RvvdCatRetornabilidad retornabilidadSelected;
    private List<RvvdCatRetornabilidad> catRetornabilidadAll = new ArrayList<RvvdCatRetornabilidad>();

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
        catCanales = canalDAO.getCanales();

        CatSubCanalDAO subCanalDAO = new CatSubCanalDAO();
        catSubCanalesAll = subCanalDAO.getSubCanalesAll();

        CatCategoriaDAO categoriaDAO = new CatCategoriaDAO();
        catCategoriaAll = categoriaDAO.getCategoriasAll();

        CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
        catCategoriaOficialAll = categoriaOficialDAO.getCategoriasOficialesAll();

        CatZonaDAO zonaDAO = new CatZonaDAO();
        catZonasAll = zonaDAO.getZonasAll();

        CatRetornabilidadDAO catRetornabilidadDAO = new CatRetornabilidadDAO();
        catRetornabilidadAll = catRetornabilidadDAO.getRetornabilidadesAll();
    }

    public RvvdCatRetornabilidad getRetornabilidadNueva() {
        return retornabilidadNueva;
    }

    public void setRetornabilidadNueva(RvvdCatRetornabilidad retornabilidadNueva) {
        this.retornabilidadNueva = retornabilidadNueva;
    }

    public RvvdCatRetornabilidad getRetornabilidadSelected() {
        return retornabilidadSelected;
    }

    public void setRetornabilidadSelected(RvvdCatRetornabilidad retornabilidadSelected) {
        this.retornabilidadSelected = retornabilidadSelected;
    }

    public List<RvvdCatRetornabilidad> getCatRetornabilidadAll() {
        return catRetornabilidadAll;
    }

    public void setCatRetornabilidadAll(List<RvvdCatRetornabilidad> catRetornabilidadAll) {
        this.catRetornabilidadAll = catRetornabilidadAll;
    }

    public RvvdCatZona getZonaNueva() {
        return zonaNueva;
    }

    public void setZonaNueva(RvvdCatZona zonaNueva) {
        this.zonaNueva = zonaNueva;
    }

    public RvvdCatZona getZonaSelected() {
        return zonaSelected;
    }

    public void setZonaSelected(RvvdCatZona zonaSelected) {
        this.zonaSelected = zonaSelected;
    }

    public List<RvvdCatZona> getCatZonasAll() {
        return catZonasAll;
    }

    public void setCatZonasAll(List<RvvdCatZona> catZonasAll) {
        this.catZonasAll = catZonasAll;
    }

    public List<RvvdCatCanal> getCatCanales() {
        return catCanales;
    }

    public void setCatCanales(List<RvvdCatCanal> catCanales) {
        this.catCanales = catCanales;
    }

    public RvvdCatSubCanal getSubCanalNuevo() {
        return subCanalNuevo;
    }

    public void setSubCanalNuevo(RvvdCatSubCanal subCanalNuevo) {
        this.subCanalNuevo = subCanalNuevo;
    }

    public RvvdCatSubCanal getSubCanalSelected() {
        return subCanalSelected;
    }

    public void setSubCanalSelected(RvvdCatSubCanal subCanalSelected) {
        this.subCanalSelected = subCanalSelected;
    }

    public List<RvvdCatSubCanal> getCatSubCanalesAll() {
        return catSubCanalesAll;
    }

    public void setCatSubCanalesAll(List<RvvdCatSubCanal> catSubCanalesAll) {
        this.catSubCanalesAll = catSubCanalesAll;
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
            if (categoriaOficialSelected != null) {
                selectOfficialCategory();
            } else {
                categoriaOficialNueva.setIdCategoriaOficial(null);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Elimina la categoria oficial seleccionada en la vista
     */
    public void deleteOfficialCategory() {
        FacesMessage message;
        CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
        if (categoriaOficialDAO.deleteCategoriaOficial(categoriaOficialNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("categoriaOficial");
            categoriaOficialNueva = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Official category deleted");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while deleting the Official category, " + categoriaOficialDAO.getError());
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
            if (categoriaSelected != null) {
                selectCategory();
            } else {
                categoriaNueva.setIdCategoria(null);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Elimina la categoria seleccionada en la vista
     */
    public void deleteCategory() {
        FacesMessage message;
        CatCategoriaDAO categoriaDAO = new CatCategoriaDAO();
        if (categoriaDAO.deleteCategoria(categoriaNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("categoria");
            categoriaNueva = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Category deleted");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while deleting the category, " + categoriaDAO.getError());
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
        canalNuevo.setSubCanalesList(canalSelected.getSubCanalesList());
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
            if (canalSelected != null) {
                selectChannel();
            } else {
                canalNuevo.setIdCanal(null);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Elimina el canal seleccionado en la vista
     */
    public void deleteChannel() {
        FacesMessage message;
        CatCanalDAO canalDAO = new CatCanalDAO();
        if (canalDAO.deleteCanal(canalNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("canal");
            canalNuevo = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Channel deleted");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while deleting the channel, " + canalDAO.getError());
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
            if (tipoConsumoSelected != null) {
                selectConsumption();
            } else {
                tipoConsumoNuevo.setIdTipoConsumo(null);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Elimina el tipo de consumo seleccionado en la vista
     */
    public void deleteConsumption() {
        FacesMessage message;
        CatTipoConsumoDAO catTipoConsumoDAO = new CatTipoConsumoDAO();
        if (catTipoConsumoDAO.deleteTipoConsumo(tipoConsumoNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("tipoConsumo");
            tipoConsumoNuevo = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Type of Consumption deleted");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while deleting the Type of Consumption, " + catTipoConsumoDAO.getError());
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
            if (empaqueSelected != null) {
                selectPacking();
            } else {
                empaqueNuevo.setIdEmpaque(null);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Elimina el empaque seleccionado en la vista
     */
    public void deletePacking() {
        FacesMessage message;
        CatEmpaqueDAO catEmpaqueDAO = new CatEmpaqueDAO();
        if (catEmpaqueDAO.deleteEmpaque(empaqueNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("empaque");
            empaqueNuevo = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Packing deleted");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while deleting the Packing, " + catEmpaqueDAO.getError());
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
            if (gecSelected != null) {
                selectGec();
            } else {
                gecNuevo.setIdGec(null);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Elimina el tipo de cliente seleccionado en la vista
     */
    public void deleteGec() {
        FacesMessage message;
        CatGecDAO catGecDAO = new CatGecDAO();
        if (catGecDAO.deleteGec(gecNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("gec");
            gecNuevo = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Client type deleted");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while deleting the Client type, " + catGecDAO.getError());
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
            if (unidadSelected != null) {
                selectBussinessUnit();
            } else {
                unidadNueva.setIdUnidadNegocio(null);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Elimina la unidad de negocio seleccionada en la vista
     */
    public void deleteBussinessUnit() {
        FacesMessage message;
        CatUnidadNegocioDAO catUnidadNegocioDAO = new CatUnidadNegocioDAO();
        if (catUnidadNegocioDAO.deleteUnidadNegocio(unidadNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("unidad");
            unidadNueva = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Bussiness unit deleted");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while deleting the Bussiness unit, " + catUnidadNegocioDAO.getError());
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
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Brand saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Brand, " + marcaDAO.getError());
            if (marcaSelected != null) {
                selectTrademark();
            } else {
                marcaNueva.setIdMarca(null);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Elimina la marca seleccionada en la vista
     */
    public void deleteTrademark() {
        FacesMessage message;
        CatMarcaDAO catMarcaDAO = new CatMarcaDAO();
        if (catMarcaDAO.deleteMarca(marcaNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("marca");
            marcaNueva = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Brand deleted");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while deleting the Brand, " + catMarcaDAO.getError());
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
            if (contenidoSelected != null) {
                selectCalorie();
            } else {
                contenidoNueva.setIdContenidoCalorico(null);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Elimina el contenido calórico seleccionado en la vista
     */
    public void deleteCalorie() {
        FacesMessage message;
        CatContCaloricoDAO catContCaloricoDAO = new CatContCaloricoDAO();
        if (catContCaloricoDAO.deleteContenidoCalorico(contenidoNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("calorico");
            contenidoNueva = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Caloric content deleted");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while deleting the Caloric content, " + catContCaloricoDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void newSubCanal() {
        subCanalNuevo = new RvvdCatSubCanal();
        subCanalSelected = null;
    }

    /**
     *
     */
    public void selectSubCanal() {
        subCanalNuevo.setIdSubCanal(subCanalSelected.getIdSubCanal());
        subCanalNuevo.setCanal(subCanalSelected.getCanal());
        subCanalNuevo.setStatus(subCanalSelected.getStatus());
        subCanalNuevo.setSubCanalEn(subCanalSelected.getSubCanalEn());
        subCanalNuevo.setSubCanalR(subCanalSelected.getSubCanalR());
    }

    /**
     *
     */
    public void saveSubCanal() {
        FacesMessage message;
        CatSubCanalDAO catSubCanalDAO = new CatSubCanalDAO();
        if (catSubCanalDAO.saveSubCanal(subCanalNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("subCanal");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Subchannel saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the subchannel, " + catSubCanalDAO.getError());
            if (subCanalSelected != null) {
                selectSubCanal();
            } else {
                subCanalNuevo.setIdSubCanal(null);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Elimina el subcanal seleccionado en la vista
     */
    public void deleteSubCanal() {
        FacesMessage message;
        CatSubCanalDAO catSubCanalDAO = new CatSubCanalDAO();
        if (catSubCanalDAO.deleteSubCanal(subCanalNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("subCanal");
            subCanalNuevo = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Subchannel deleted");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while deleting the Subchannel, " + catSubCanalDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void newZona() {
        zonaNueva = new RvvdCatZona();
        zonaSelected = null;
    }

    /**
     *
     */
    public void selectZona() {
        zonaNueva.setIdZona(zonaSelected.getIdZona());
        zonaNueva.setStatus(zonaSelected.getStatus());
        zonaNueva.setZonaEn(zonaSelected.getZonaEn());
        zonaNueva.setZonaR(zonaSelected.getZonaR());
    }

    /**
     *
     */
    public void saveZona() {
        FacesMessage message;
        CatZonaDAO zonaDAO = new CatZonaDAO();
        if (zonaDAO.saveZona(zonaNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("zona");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Zone saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the zone, " + zonaDAO.getError());
            if (zonaSelected != null) {
                selectZona();
            } else {
                zonaNueva.setIdZona(null);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Elimina la zona seleccionada en la vista
     */
    public void deleteZona() {
        FacesMessage message;
        CatZonaDAO catZonaDAO = new CatZonaDAO();
        if (catZonaDAO.deleteZona(zonaNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("zona");
            zonaNueva = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Zone deleted");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while deleting the Zone, " + catZonaDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void newRetornabilidad() {
        retornabilidadNueva = new RvvdCatRetornabilidad();
        retornabilidadSelected = null;
    }

    /**
     *
     */
    public void selectRetornabilidad() {
        retornabilidadNueva.setIdRetornabilidad(retornabilidadSelected.getIdRetornabilidad());
        retornabilidadNueva.setRetornabilidadR(retornabilidadSelected.getRetornabilidadR());
        retornabilidadNueva.setStatus(retornabilidadSelected.getStatus());
    }

    /**
     *
     */
    public void saveRetornabilidad() {
        FacesMessage message;
        CatRetornabilidadDAO catRetornabilidadDAO = new CatRetornabilidadDAO();
        if (catRetornabilidadDAO.saveRetornabilidad(retornabilidadNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("retornabilidad");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Returnability saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the returnability, " + catRetornabilidadDAO.getError());
            if (retornabilidadSelected != null) {
                selectRetornabilidad();
            } else {
                retornabilidadNueva.setIdRetornabilidad(null);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Elimina la la retornabilidad seleccionada en la vista
     */
    public void deleteRetornabilidad() {
        FacesMessage message;
        CatRetornabilidadDAO catRetornabilidadDAO = new CatRetornabilidadDAO();
        if (catRetornabilidadDAO.deleteRetornabilidad(retornabilidadNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("retornabilidad");
            retornabilidadNueva = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Returnability deleted");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while deleting the returnability, " + catRetornabilidadDAO.getError());
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
            catCanales = canalDAO.getCanales();
        } else if ("categoria".equalsIgnoreCase(catalog)) {
            CatCategoriaDAO categoriaDAO = new CatCategoriaDAO();
            catCategoriaAll = categoriaDAO.getCategoriasAll();
        } else if ("categoriaOficial".equalsIgnoreCase(catalog)) {
            CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
            catCategoriaOficialAll = categoriaOficialDAO.getCategoriasOficialesAll();
        } else if ("subCanal".equalsIgnoreCase(catalog)) {
            CatSubCanalDAO catSubCanalDAO = new CatSubCanalDAO();
            catSubCanalesAll = catSubCanalDAO.getSubCanalesAll();
        } else if ("zona".equalsIgnoreCase(catalog)) {
            CatZonaDAO zonaDAO = new CatZonaDAO();
            catZonasAll = zonaDAO.getZonasAll();
        } else if ("retornabilidad".equalsIgnoreCase(catalog)) {
            CatRetornabilidadDAO catRetornabilidadDAO = new CatRetornabilidadDAO();
            catRetornabilidadAll = catRetornabilidadDAO.getRetornabilidadesAll();
        }
    }
}
