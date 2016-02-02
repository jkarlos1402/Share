package com.femsa.kof.share.managedbeans;

import com.femsa.kof.share.dao.ShareCatCategoriasDAO;
import com.femsa.kof.share.dao.ShareCatFabricanteDAO;
import com.femsa.kof.share.dao.ShareCatGrupoCategoriasDAO;
import com.femsa.kof.share.dao.ShareCatPaisDAO;
import com.femsa.kof.share.pojos.ShareCatCategorias;
import com.femsa.kof.share.pojos.ShareCatFabricante;
import com.femsa.kof.share.pojos.ShareCatGrupoCategorias;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.util.CatalogLoader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author TMXIDSJPINAM
 */
@ManagedBean
@SessionScoped
public class ShareCatalogsBean implements Serializable {

    private List<SelectItem> catGrupoCategorias;
    private ShareCatGrupoCategorias grupoCategoriaSelected;

    private List<ShareCatCategorias> catCategorias;
    private ShareCatCategorias categoriaSelected = new ShareCatCategorias();
    private ShareCatCategorias categoriaSelectedTable;

    private List<ShareCatPais> catPaises;
    private ShareCatPais paisSelected = new ShareCatPais();
    private ShareCatPais paisSelectedTable;

    private List<ShareCatGrupoCategorias> catGrupoCategoriasAll;
    private ShareCatGrupoCategorias grupoCategoriaNew = new ShareCatGrupoCategorias();
    private ShareCatGrupoCategorias grupoCategoriaSelectedTable;

    private List<ShareCatFabricante> catFabricanteAll;
    private ShareCatFabricante fabricanteNew = new ShareCatFabricante();
    private ShareCatFabricante fabricanteSelectedTable;

    /**
     *
     */
    public ShareCatalogsBean() {
        initBean();
    }

    /**
     *
     * @return
     */
    public List<ShareCatFabricante> getCatFabricanteAll() {
        return catFabricanteAll;
    }

    /**
     *
     * @param catFabricanteAll
     */
    public void setCatFabricanteAll(List<ShareCatFabricante> catFabricanteAll) {
        this.catFabricanteAll = catFabricanteAll;
    }

    /**
     *
     * @return
     */
    public ShareCatFabricante getFabricanteNew() {
        return fabricanteNew;
    }

    /**
     *
     * @param fabricanteNew
     */
    public void setFabricanteNew(ShareCatFabricante fabricanteNew) {
        this.fabricanteNew = fabricanteNew;
    }

    /**
     *
     * @return
     */
    public ShareCatFabricante getFabricanteSelectedTable() {
        return fabricanteSelectedTable;
    }

    /**
     *
     * @param fabricanteSelectedTable
     */
    public void setFabricanteSelectedTable(ShareCatFabricante fabricanteSelectedTable) {
        this.fabricanteSelectedTable = fabricanteSelectedTable;
    }

    /**
     *
     * @return
     */
    public List<ShareCatGrupoCategorias> getCatGrupoCategoriasAll() {
        return catGrupoCategoriasAll;
    }

    /**
     *
     * @param catGrupoCategoriasAll
     */
    public void setCatGrupoCategoriasAll(List<ShareCatGrupoCategorias> catGrupoCategoriasAll) {
        this.catGrupoCategoriasAll = catGrupoCategoriasAll;
    }

    /**
     *
     * @return
     */
    public ShareCatGrupoCategorias getGrupoCategoriaNew() {
        return grupoCategoriaNew;
    }

    /**
     *
     * @param grupoCategoriaNew
     */
    public void setGrupoCategoriaNew(ShareCatGrupoCategorias grupoCategoriaNew) {
        this.grupoCategoriaNew = grupoCategoriaNew;
    }

    /**
     *
     * @return
     */
    public ShareCatGrupoCategorias getGrupoCategoriaSelectedTable() {
        return grupoCategoriaSelectedTable;
    }

    /**
     *
     * @param grupoCategoriaSelectedTable
     */
    public void setGrupoCategoriaSelectedTable(ShareCatGrupoCategorias grupoCategoriaSelectedTable) {
        this.grupoCategoriaSelectedTable = grupoCategoriaSelectedTable;
    }

    /**
     *
     * @return
     */
    public List<ShareCatPais> getCatPaises() {
        return catPaises;
    }

    /**
     *
     * @param catPaises
     */
    public void setCatPaises(List<ShareCatPais> catPaises) {
        this.catPaises = catPaises;
    }

    /**
     *
     * @return
     */
    public ShareCatPais getPaisSelected() {
        return paisSelected;
    }

    /**
     *
     * @param paisSelected
     */
    public void setPaisSelected(ShareCatPais paisSelected) {
        this.paisSelected = paisSelected;
    }

    /**
     *
     * @return
     */
    public ShareCatPais getPaisSelectedTable() {
        return paisSelectedTable;
    }

    /**
     *
     * @param paisSelectedTable
     */
    public void setPaisSelectedTable(ShareCatPais paisSelectedTable) {
        this.paisSelectedTable = paisSelectedTable;
    }

    /**
     *
     * @return
     */
    public ShareCatCategorias getCategoriaSelected() {
        return categoriaSelected;
    }

    /**
     *
     * @param categoriaSelected
     */
    public void setCategoriaSelected(ShareCatCategorias categoriaSelected) {
        this.categoriaSelected = categoriaSelected;
    }

    /**
     *
     * @return
     */
    public ShareCatCategorias getCategoriaSelectedTable() {
        return categoriaSelectedTable;
    }

    /**
     *
     * @param categoriaSelectedTable
     */
    public void setCategoriaSelectedTable(ShareCatCategorias categoriaSelectedTable) {
        this.categoriaSelectedTable = categoriaSelectedTable;
    }

    /**
     *
     * @return
     */
    public List<ShareCatCategorias> getCatCategorias() {
        return catCategorias;
    }

    /**
     *
     * @param catCategorias
     */
    public void setCatCategorias(List<ShareCatCategorias> catCategorias) {
        this.catCategorias = catCategorias;
    }

    /**
     *
     * @return
     */
    public ShareCatGrupoCategorias getGrupoCategoriaSelected() {
        return grupoCategoriaSelected;
    }

    /**
     *
     * @param grupoCategoriaSelected
     */
    public void setGrupoCategoriaSelected(ShareCatGrupoCategorias grupoCategoriaSelected) {
        this.grupoCategoriaSelected = grupoCategoriaSelected;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getCatGrupoCategorias() {
        ShareCatGrupoCategoriasDAO grupoCategoriasDAO = new ShareCatGrupoCategoriasDAO();
        List<ShareCatGrupoCategorias> grupos = grupoCategoriasDAO.getCategoryGroups();
        if (catGrupoCategorias != null) {
            catGrupoCategorias.clear();
        } else {
            catGrupoCategorias = new ArrayList<SelectItem>();
        }
        for (ShareCatGrupoCategorias grupo : grupos) {
            catGrupoCategorias.add(new SelectItem(grupo, grupo.getGrupoCategoria()));
        }
        return catGrupoCategorias;
    }

    /**
     *
     * @param catGrupoCategorias
     */
    public void setCatGrupoCategorias(List<SelectItem> catGrupoCategorias) {
        this.catGrupoCategorias = catGrupoCategorias;
    }

    private void initBean() {
        FacesMessage message = null;
        grupoCategoriaSelected = new ShareCatGrupoCategorias();

        ShareCatCategoriasDAO categoriasDAO = new ShareCatCategoriasDAO();
        catCategorias = categoriasDAO.getCategoriasAll();

        if (categoriasDAO.getError() != null && !categoriasDAO.getError().equalsIgnoreCase("")) {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", categoriasDAO.getError());
        } else {
            ShareCatPaisDAO paisDAO = new ShareCatPaisDAO();
            catPaises = paisDAO.getCatPaisAll();

            ShareCatGrupoCategoriasDAO grupoCategoriasDAO = new ShareCatGrupoCategoriasDAO();
            catGrupoCategoriasAll = grupoCategoriasDAO.getCategoryGroupsAll();

            ShareCatFabricanteDAO fabricanteDAO = new ShareCatFabricanteDAO();
            catFabricanteAll = fabricanteDAO.getFabricantesAll();
        }
        if (message != null) {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    /**
     *
     */
    public void newCategory() {
        categoriaSelected = new ShareCatCategorias();
        categoriaSelectedTable = null;
    }

    /**
     *
     */
    public void newCountry() {
        paisSelected = new ShareCatPais();
        paisSelectedTable = null;
    }

    /**
     *
     */
    public void newGroupCategory() {
        grupoCategoriaNew = new ShareCatGrupoCategorias();
        grupoCategoriaSelectedTable = null;
    }

    /**
     *
     */
    public void newManufacturer() {
        fabricanteNew = new ShareCatFabricante();
        fabricanteSelectedTable = null;
    }

    /**
     *
     */
    public void saveCategory() {
        FacesMessage message = null;
        ShareCatCategoriasDAO categoriasDAO = new ShareCatCategoriasDAO();
        if (categoriasDAO.saveCategoria(categoriaSelected)) {
            CatalogLoader.loadCatalogs("share");
            refreshCatalog("categoria");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Category saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the category, " + categoriasDAO.getError());
            categoriaSelected.setPkCategoria(null);
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void saveCountry() {
        FacesMessage message = null;
        ShareCatPaisDAO paisDAO = new ShareCatPaisDAO();
        if (paisDAO.savePais(paisSelected)) {
            CatalogLoader.loadCatalogs("share");
            refreshCatalog("pais");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Country saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the country, " + paisDAO.getError());
            paisSelected.setPkPais(null);
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void saveGroupCategory() {
        FacesMessage message = null;
        ShareCatGrupoCategoriasDAO grupoCategoriasDAO = new ShareCatGrupoCategoriasDAO();
        if (grupoCategoriasDAO.saveGroupCategory(grupoCategoriaNew)) {
            CatalogLoader.loadCatalogs("share");
            refreshCatalog("grupoCategoria");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Group category saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the group category, " + grupoCategoriasDAO.getError());
            grupoCategoriaNew.setPkGrupoCategoria(null);
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void saveManufacturer() {
        FacesMessage message = null;
        ShareCatFabricanteDAO fabricanteDAO = new ShareCatFabricanteDAO();
        if (fabricanteDAO.saveFabricante(fabricanteNew)) {
            CatalogLoader.loadCatalogs("share");
            refreshCatalog("fabricante");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Manufacturer saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the manufacturer, " + fabricanteDAO.getError());
            fabricanteNew.setPkFabricante(null);
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void selectCategory() {
        categoriaSelected.setPkCategoria(categoriaSelectedTable.getPkCategoria());
        categoriaSelected.setCategoria(categoriaSelectedTable.getCategoria());
        categoriaSelected.setCategoriaEsp(categoriaSelectedTable.getCategoriaEsp());
        categoriaSelected.setFkGrupoCategoria(categoriaSelectedTable.getFkGrupoCategoria());
        categoriaSelected.setStatus(categoriaSelectedTable.getStatus());
    }

    /**
     *
     */
    public void selectCountry() {
        paisSelected.setPkPais(paisSelectedTable.getPkPais());
        paisSelected.setClaveCorta(paisSelectedTable.getClaveCorta());
        paisSelected.setIdstatus(paisSelectedTable.getIdstatus());
        paisSelected.setNombre(paisSelectedTable.getNombre());
        paisSelected.setNombreTabla(paisSelectedTable.getNombreTabla());
        paisSelected.setUsuarios(paisSelected.getUsuarios());
    }

    /**
     *
     */
    public void selectGroupCategory() {
        grupoCategoriaNew.setPkGrupoCategoria(grupoCategoriaSelectedTable.getPkGrupoCategoria());
        grupoCategoriaNew.setGrupoCategoria(grupoCategoriaSelectedTable.getGrupoCategoria());
        grupoCategoriaNew.setStatus(grupoCategoriaSelectedTable.isStatus());
        grupoCategoriaNew.setCategoriasList(grupoCategoriaSelectedTable.getCategoriasList());
    }

    /**
     *
     */
    public void selectManufacturer() {
        fabricanteNew.setPkFabricante(fabricanteSelectedTable.getPkFabricante());
        fabricanteNew.setFabricante(fabricanteSelectedTable.getFabricante());
        fabricanteNew.setStatus(fabricanteSelectedTable.isStatus());
    }

    /**
     *
     */
    public void genCountryTableName() {
        if (paisSelected.getPkPais() == null) {
            paisSelected.setNombreTabla("SHARE_TMP_" + paisSelected.getClaveCorta() + "_INFO_CARGA");
        }
    }

    private void refreshCatalog(String catalog) {
        if (catalog != null) {
            if (catalog.equalsIgnoreCase("pais")) {
                ShareCatPaisDAO paisDAO = new ShareCatPaisDAO();
                catPaises = paisDAO.getCatPaisAll();
            } else if (catalog.equalsIgnoreCase("categoria")) {
                ShareCatCategoriasDAO categoriasDAO = new ShareCatCategoriasDAO();
                catCategorias = categoriasDAO.getCategoriasAll();
            } else if (catalog.equalsIgnoreCase("grupoCategoria")) {
                ShareCatGrupoCategoriasDAO grupoCategoriasDAO = new ShareCatGrupoCategoriasDAO();
                catGrupoCategoriasAll = grupoCategoriasDAO.getCategoryGroupsAll();
            } else if (catalog.equalsIgnoreCase("fabricante")) {
                ShareCatFabricanteDAO fabricanteDAO = new ShareCatFabricanteDAO();
                catFabricanteAll = fabricanteDAO.getFabricantesAll();
            }
        }
    }

}
