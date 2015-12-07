package com.femsa.kof.util;

import com.femsa.kof.dao.ShareCatCategoriasDAO;
import com.femsa.kof.dao.ShareCatPaisDAO;
import com.femsa.kof.dao.ShareCatCanalesDAO;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class CatalogLoader {
    public static void loadCatalogs(){
        ServletContext sc = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();        
        
        ShareCatCategoriasDAO catCategoriasDAO = new ShareCatCategoriasDAO();
        sc.setAttribute("categories_catalog", catCategoriasDAO.getCategorias());
        
        ShareCatCanalesDAO canalesDAO = new ShareCatCanalesDAO();
        sc.setAttribute("canales_catalog", canalesDAO.getCanales());
        
        ShareCatPaisDAO catPaisDAO = new ShareCatPaisDAO();
        sc.setAttribute("countries_catalog", catPaisDAO.getCatPais());
    }
}
