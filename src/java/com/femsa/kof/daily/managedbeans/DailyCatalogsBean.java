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

    private RvvdCatUnidadNegocio unidadNueva = new RvvdCatUnidadNegocio();
    private RvvdCatUnidadNegocio unidadSelected;
    private List<RvvdCatUnidadNegocio> catUnidadesAll = new ArrayList<RvvdCatUnidadNegocio>();

    private RvvdCatMarca marcaNueva = new RvvdCatMarca();
    private RvvdCatMarca marcaSelected;
    private List<RvvdCatMarca> catMarcasAll = new ArrayList<RvvdCatMarca>();

    private RvvdCatContenidoCalorico contenidoNueva = new RvvdCatContenidoCalorico();
    private RvvdCatContenidoCalorico contenidoSelected;
    private List<RvvdCatContenidoCalorico> catContenidosAll = new ArrayList<RvvdCatContenidoCalorico>();

    public DailyCatalogsBean() {
        CatContCaloricoDAO contCaloricoDAO = new CatContCaloricoDAO();
        catContenidosAll = contCaloricoDAO.getContsCalAll();

        CatMarcaDAO marcaDAO = new CatMarcaDAO();
        catMarcasAll = marcaDAO.getMarcasAll();

        CatGecDAO gecDAO = new CatGecDAO();
        catGecsAll = gecDAO.getGecAll();

        CatUnidadNegocioDAO unidadNegocioDAO = new CatUnidadNegocioDAO();
        catUnidadesAll = unidadNegocioDAO.getUnidadesNegAll();

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

    public RvvdCatContenidoCalorico getContenidoNueva() {
        return contenidoNueva;
    }

    public void setContenidoNueva(RvvdCatContenidoCalorico contenidoNueva) {
        this.contenidoNueva = contenidoNueva;
    }

    public RvvdCatContenidoCalorico getContenidoSelected() {
        return contenidoSelected;
    }

    public void setContenidoSelected(RvvdCatContenidoCalorico contenidoSelected) {
        this.contenidoSelected = contenidoSelected;
    }

    public List<RvvdCatContenidoCalorico> getCatContenidosAll() {
        return catContenidosAll;
    }

    public void setCatContenidosAll(List<RvvdCatContenidoCalorico> catContenidosAll) {
        this.catContenidosAll = catContenidosAll;
    }

    public RvvdCatMarca getMarcaNueva() {
        return marcaNueva;
    }

    public void setMarcaNueva(RvvdCatMarca marcaNueva) {
        this.marcaNueva = marcaNueva;
    }

    public RvvdCatMarca getMarcaSelected() {
        return marcaSelected;
    }

    public void setMarcaSelected(RvvdCatMarca marcaSelected) {
        this.marcaSelected = marcaSelected;
    }

    public List<RvvdCatMarca> getCatMarcasAll() {
        return catMarcasAll;
    }

    public void setCatMarcasAll(List<RvvdCatMarca> catMarcasAll) {
        this.catMarcasAll = catMarcasAll;
    }

    public RvvdCatGec getGecNuevo() {
        return gecNuevo;
    }

    public void setGecNuevo(RvvdCatGec gecNuevo) {
        this.gecNuevo = gecNuevo;
    }

    public RvvdCatGec getGecSelected() {
        return gecSelected;
    }

    public void setGecSelected(RvvdCatGec gecSelected) {
        this.gecSelected = gecSelected;
    }

    public List<RvvdCatGec> getCatGecsAll() {
        return catGecsAll;
    }

    public void setCatGecsAll(List<RvvdCatGec> catGecsAll) {
        this.catGecsAll = catGecsAll;
    }

    public RvvdCatUnidadNegocio getUnidadNueva() {
        return unidadNueva;
    }

    public void setUnidadNueva(RvvdCatUnidadNegocio unidadNueva) {
        this.unidadNueva = unidadNueva;
    }

    public RvvdCatUnidadNegocio getUnidadSelected() {
        return unidadSelected;
    }

    public void setUnidadSelected(RvvdCatUnidadNegocio unidadSelected) {
        this.unidadSelected = unidadSelected;
    }

    public List<RvvdCatUnidadNegocio> getCatUnidadesAll() {
        return catUnidadesAll;
    }

    public void setCatUnidadesAll(List<RvvdCatUnidadNegocio> catUnidadesAll) {
        this.catUnidadesAll = catUnidadesAll;
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
            refreshCatalog("categoriaOficial");
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
            refreshCatalog("categoria");
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
            refreshCatalog("canal");
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
            refreshCatalog("tipoConsumo");
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
            refreshCatalog("empaque");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Packing saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the packing, " + empaqueDAO.getError());
            empaqueNuevo.setIdEmpaque(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void newGec() {
        gecNuevo = new RvvdCatGec();
        gecSelected = null;
    }

    public void selectGec() {
        gecNuevo.setIdGec(gecSelected.getIdGec());
        gecNuevo.setGecEn(gecSelected.getGecEn());
        gecNuevo.setGecR(gecSelected.getGecR());
        gecNuevo.setStatus(gecSelected.getStatus());
    }

    public void saveGec() {
        FacesMessage message = null;
        CatGecDAO gecDAO = new CatGecDAO();
        if (gecDAO.saveGec(gecNuevo)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("gec");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Gec saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the gec, " + gecDAO.getError());
            gecNuevo.setIdGec(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void newBussinessUnit() {
        unidadNueva = new RvvdCatUnidadNegocio();
        unidadSelected = null;
    }

    public void selectBussinessUnit() {
        unidadNueva.setIdUnidadNegocio(unidadSelected.getIdUnidadNegocio());
        unidadNueva.setStatus(unidadSelected.getStatus());
        unidadNueva.setUnidadNegocioEn(unidadSelected.getUnidadNegocioEn());
        unidadNueva.setUnidadNegocioR(unidadSelected.getUnidadNegocioR());
    }

    public void saveBussinessUnit() {
        FacesMessage message = null;
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

    public void newTrademark() {
        marcaNueva = new RvvdCatMarca();
        marcaSelected = null;
    }

    public void selectTrademark() {
        marcaNueva.setIdMarca(marcaSelected.getIdMarca());
        marcaNueva.setMarcaEn(marcaSelected.getMarcaEn());
        marcaNueva.setMarcaR(marcaSelected.getMarcaR());
        marcaNueva.setStatus(marcaSelected.getStatus());
    }

    public void saveTrademark() {
        FacesMessage message = null;
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

    public void newCalorie() {
        contenidoNueva = new RvvdCatContenidoCalorico();
        contenidoSelected = null;
    }

    public void selectCalorie() {
        contenidoNueva.setIdContenidoCalorico(contenidoSelected.getIdContenidoCalorico());
        contenidoNueva.setContenidoCaloricoEn(contenidoSelected.getContenidoCaloricoEn());
        contenidoNueva.setContenidoCaloricoR(contenidoSelected.getContenidoCaloricoR());
        contenidoNueva.setStatus(contenidoSelected.getStatus());
    }

    public void saveCalorie() {
        FacesMessage message = null;
        CatContCaloricoDAO contCaloricoDAO = new CatContCaloricoDAO();
        if (contCaloricoDAO.saveContCal(contenidoNueva)) {
            CatalogLoader.loadCatalogs("daily");
            refreshCatalog("calorico");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Calorie saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the Calorie, " + contCaloricoDAO.getError());
            marcaNueva.setIdMarca(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void refreshCatalog(String catalog) {
        if (catalog != null) {
            if (catalog.equalsIgnoreCase("calorico")) {
                CatContCaloricoDAO contCaloricoDAO = new CatContCaloricoDAO();
                catContenidosAll = contCaloricoDAO.getContsCalAll();
            } else if (catalog.equalsIgnoreCase("marca")) {
                CatMarcaDAO marcaDAO = new CatMarcaDAO();
                catMarcasAll = marcaDAO.getMarcasAll();
            } else if (catalog.equalsIgnoreCase("gec")) {
                CatGecDAO gecDAO = new CatGecDAO();
                catGecsAll = gecDAO.getGecAll();
            } else if (catalog.equalsIgnoreCase("unidad")) {
                CatUnidadNegocioDAO unidadNegocioDAO = new CatUnidadNegocioDAO();
                catUnidadesAll = unidadNegocioDAO.getUnidadesNegAll();
            } else if (catalog.equalsIgnoreCase("tipoConsumo")) {
                CatTipoConsumoDAO tipoConsumoDAO = new CatTipoConsumoDAO();
                catTiposConsumoAll = tipoConsumoDAO.getTiposConsumoAll();
            } else if (catalog.equalsIgnoreCase("empaque")) {
                CatEmpaqueDAO empaqueDAO = new CatEmpaqueDAO();
                catEmpaquesAll = empaqueDAO.getEmpaquesAll();
            } else if (catalog.equalsIgnoreCase("canal")) {
                CatCanalDAO canalDAO = new CatCanalDAO();
                catCanalesAll = canalDAO.getCanalesAll();
            } else if (catalog.equalsIgnoreCase("categoria")) {
                CatCategoriaDAO categoriaDAO = new CatCategoriaDAO();
                catCategoriaAll = categoriaDAO.getCategoriasAll();
            } else if (catalog.equalsIgnoreCase("categoriaOficial")) {
                CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
                catCategoriaOficialAll = categoriaOficialDAO.getCategoriasOficialesAll();
            }
        }
    }
}
