package com.femsa.kof.daily.util;

import com.femsa.kof.daily.dao.ReclasifCanalDAO;
import com.femsa.kof.daily.dao.ReclasifCategoriaDAO;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class CheckCatalogs {

    public static long checkCategoryDaily(ShareUsuario usuario) {
        ReclasifCategoriaDAO reclasifCategoriaDAO = new ReclasifCategoriaDAO();
        return reclasifCategoriaDAO.checkReclasifCategorias(usuario);
    }

    public static long checkChannelDaily(ShareUsuario usuario) {
        ReclasifCanalDAO reclasifCanalDAO = new ReclasifCanalDAO();
        return reclasifCanalDAO.checkReclasifCanales(usuario);
    }

    public static void checkAllCatalogs() {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        List<String> notifications = new ArrayList<String>();
        long numNotReclassCateg = checkCategoryDaily(usuario);
        long numNotReclassCanal = checkChannelDaily(usuario);
        if (numNotReclassCateg > 0) {
            notifications.add("You have " + numNotReclassCateg + " categories without reclassifying.");
        }
        if (numNotReclassCanal > 0) {
            notifications.add("You have " + numNotReclassCanal + " channels without reclassifying.");
        }
        session.setAttribute("notifications_user", notifications);
    }
}
