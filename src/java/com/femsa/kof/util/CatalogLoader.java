package com.femsa.kof.util;

import com.femsa.kof.daily.dao.CatCanalDAO;
import com.femsa.kof.daily.dao.CatCategoriaDAO;
import com.femsa.kof.daily.dao.CatCategoriaOficialDAO;
import com.femsa.kof.daily.dao.CatContCaloricoDAO;
import com.femsa.kof.daily.dao.CatEmpaqueDAO;
import com.femsa.kof.daily.dao.CatGecDAO;
import com.femsa.kof.daily.dao.CatMarcaDAO;
import com.femsa.kof.daily.dao.CatSubCanalDAO;
import com.femsa.kof.daily.dao.CatTipoConsumoDAO;
import com.femsa.kof.daily.dao.CatUnidadNegocioDAO;
import com.femsa.kof.daily.dao.CatZonaDAO;
import com.femsa.kof.share.dao.ShareCatCategoriasDAO;
import com.femsa.kof.share.dao.ShareCatPaisDAO;
import com.femsa.kof.share.dao.ShareCatCanalesDAO;
import com.femsa.kof.share.pojos.ShareCatCategorias;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author TMXIDSJPINAM
 */
public class CatalogLoader {

    /**
     *
     */
    public static String error = "";

    /**
     *
     * @param proyecto
     * @return
     */
    public static boolean loadCatalogs(String proyecto) {
        boolean bndOk = true;
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (proyecto.equalsIgnoreCase("share")) {
            ShareCatCategoriasDAO catCategoriasDAO = new ShareCatCategoriasDAO();
            List<ShareCatCategorias> categorias = (List<ShareCatCategorias>) catCategoriasDAO.getCategorias();
            if (categorias != null && catCategoriasDAO.getError() == null) {
                sc.setAttribute("categories_catalog", categorias);

                ShareCatCanalesDAO canalesDAO = new ShareCatCanalesDAO();
                sc.setAttribute("canales_catalog", canalesDAO.getCanales());

                ShareCatPaisDAO catPaisDAO = new ShareCatPaisDAO();
                sc.setAttribute("countries_catalog", catPaisDAO.getCatPais());
                error = "";
            }else{
                error = catCategoriasDAO.getError();
                bndOk = false;
            }
        } else if (proyecto.equalsIgnoreCase("daily")) {
            CatCategoriaDAO categoriaDAO = new CatCategoriaDAO();
            sc.setAttribute("categoria_daily_catalog", categoriaDAO.getCategorias());

            CatMarcaDAO marcaDAO = new CatMarcaDAO();
            sc.setAttribute("marca_daily_catalog", marcaDAO.getMarcas());

            CatContCaloricoDAO contCaloricoDAO = new CatContCaloricoDAO();
            sc.setAttribute("contCalorico_daily_catalog", contCaloricoDAO.getContsCal());

            CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
            sc.setAttribute("categoria_oficial_catalog", categoriaOficialDAO.getCategoriasOficiales());

            CatGecDAO gecDAO = new CatGecDAO();
            sc.setAttribute("gec_daily_catalog", gecDAO.getGecs());

            CatUnidadNegocioDAO unidadNegocioDAO = new CatUnidadNegocioDAO();
            sc.setAttribute("unidadNegocio_daily_catalog", unidadNegocioDAO.getUnidadesNeg());

            CatTipoConsumoDAO tipoConsumoDAO = new CatTipoConsumoDAO();
            sc.setAttribute("tipoConsumo_daily_catalog", tipoConsumoDAO.getTiposConsumo());

            CatEmpaqueDAO empaqueDAO = new CatEmpaqueDAO();
            sc.setAttribute("empaque_daily_catalog", empaqueDAO.getEmpaques());

            CatCanalDAO canalDAO = new CatCanalDAO();
            sc.setAttribute("canal_daily_catalog", canalDAO.getCanales());
            
            CatSubCanalDAO subCanalDAO = new CatSubCanalDAO();
            sc.setAttribute("sub_canal_daily_catalog", subCanalDAO.getSubCanales());
            
            CatZonaDAO zonaDAO = new CatZonaDAO();
            sc.setAttribute("zona_daily_catalog", zonaDAO.getZonas());
        }
        return bndOk;
    }

}
