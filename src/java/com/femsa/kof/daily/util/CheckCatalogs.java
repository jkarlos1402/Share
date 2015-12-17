package com.femsa.kof.daily.util;

import com.femsa.kof.daily.dao.ReclasifCategoriaDAO;
import com.femsa.kof.share.pojos.ShareUsuario;

public class CheckCatalogs {
    public static long checkCategoryDaily(ShareUsuario usuario){
        ReclasifCategoriaDAO reclasifCategoriaDAO = new ReclasifCategoriaDAO();
        return reclasifCategoriaDAO.checkReclasifCategorias(usuario);
    }
}
