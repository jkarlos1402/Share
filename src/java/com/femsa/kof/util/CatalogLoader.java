package com.femsa.kof.util;

import com.femsa.kof.dao.ShareCatCategoriasDAO;
import com.femsa.kof.dao.ShareCatPaisDAO;
import com.femsa.kof.dao.ShareCatCanalesDAO;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class CatalogLoader {    

    public static void loadCatalogs() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);     
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();     
        ShareCatCategoriasDAO catCategoriasDAO = new ShareCatCategoriasDAO();
        session.setAttribute("categories_catalog", catCategoriasDAO.getCategorias());

        ShareCatCanalesDAO canalesDAO = new ShareCatCanalesDAO();
        session.setAttribute("canales_catalog", canalesDAO.getCanales());

        ShareCatPaisDAO catPaisDAO = new ShareCatPaisDAO();
        sc.setAttribute("countries_catalog", catPaisDAO.getCatPais());
    }

}
