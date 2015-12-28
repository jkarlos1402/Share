package com.femsa.kof.util;

import com.femsa.kof.share.dao.ShareCatCategoriasDAO;
import com.femsa.kof.share.dao.ShareCatPaisDAO;
import com.femsa.kof.share.dao.ShareCatCanalesDAO;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class CatalogLoader {

    public static void loadCatalogs(String proyecto) {       
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (proyecto.equalsIgnoreCase("share")) {
            ShareCatCategoriasDAO catCategoriasDAO = new ShareCatCategoriasDAO();
            sc.setAttribute("categories_catalog", catCategoriasDAO.getCategorias());

            ShareCatCanalesDAO canalesDAO = new ShareCatCanalesDAO();
            sc.setAttribute("canales_catalog", canalesDAO.getCanales());

            ShareCatPaisDAO catPaisDAO = new ShareCatPaisDAO();
            sc.setAttribute("countries_catalog", catPaisDAO.getCatPais());
        }else if(proyecto.equalsIgnoreCase("daily")){
            
        }
    }

}
