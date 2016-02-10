package com.femsa.kof.daily.util;

import com.femsa.kof.daily.dao.ReclasifCanalDAO;
import com.femsa.kof.daily.dao.ReclasifCategoriaDAO;
import com.femsa.kof.daily.dao.ReclasifDiasOpDAO;
import com.femsa.kof.daily.dao.ReclasifEmpaqueDAO;
import com.femsa.kof.daily.dao.ReclasifGecDAO;
import com.femsa.kof.daily.dao.ReclasifMarcaDAO;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TMXIDSJPINAM
 */
public class CheckCatalogs {
    
    private CheckCatalogs(){
        //para que no instancien objetos de este tipo
    }

    /**
     *
     * @param usuario
     * @return
     */
    public static long checkCategoryDaily(ShareUsuario usuario) {
        ReclasifCategoriaDAO reclasifCategoriaDAO = new ReclasifCategoriaDAO();
        return reclasifCategoriaDAO.checkReclasifCategorias(usuario);
    }

    /**
     *
     * @param usuario
     * @return
     */
    public static long checkChannelDaily(ShareUsuario usuario) {
        ReclasifCanalDAO reclasifCanalDAO = new ReclasifCanalDAO();
        return reclasifCanalDAO.checkReclasifCanales(usuario);
    }
    
    /**
     *
     * @param usuario
     * @return
     */
    public static long checkTrademarkDaily(ShareUsuario usuario) {
        ReclasifMarcaDAO reclasifMarcaDAO = new ReclasifMarcaDAO();
        return reclasifMarcaDAO.checkReclasifMarcas(usuario);
    }
    
    /**
     *
     * @param usuario
     * @return
     */
    public static long checkGECDaily(ShareUsuario usuario) {
        ReclasifGecDAO reclasifGecDAO = new ReclasifGecDAO();
        return reclasifGecDAO.checkReclasifUnGec(usuario);
    }
    
    /**
     *
     * @param usuario
     * @return
     */
    public static long checkPackingDaily(ShareUsuario usuario) {
        ReclasifEmpaqueDAO reclasifEmpaqueDAO = new ReclasifEmpaqueDAO();
        return reclasifEmpaqueDAO.checkReclasifEmpaques(usuario);
    }
    
    /**
     *
     * @param usuario
     * @return
     */
    public static long checkOperativeDaysDaily(ShareUsuario usuario) {
        ReclasifDiasOpDAO reclasifDiasOpDAO = new ReclasifDiasOpDAO();
        return reclasifDiasOpDAO.checkReclasifDiasOp(usuario);
    }

    /**
     *
     */
    public static void checkAllCatalogs() {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ShareUsuario usuario = (ShareUsuario)session.getAttribute("session_user");
        List<String> notifications = new ArrayList<String>();
        long numNotReclassCateg = checkCategoryDaily(usuario);
        long numNotReclassCanal = checkChannelDaily(usuario);
        long numNotReclassMarca = checkTrademarkDaily(usuario);
        long numNotReclassGec = checkGECDaily(usuario);
        long numNotReclassEmpaque = checkPackingDaily(usuario);
        long numNotReclassDiasOp = checkOperativeDaysDaily(usuario);
        if (numNotReclassCateg > 0) {
            notifications.add("You have " + numNotReclassCateg + " categories without reclassifying.");
        }
        if (numNotReclassCanal > 0) {
            notifications.add("You have " + numNotReclassCanal + " channels without reclassifying.");
        }        
        if (numNotReclassMarca > 0) {
            notifications.add("You have " + numNotReclassMarca + " trademarks without reclassifying.");
        }        
        if (numNotReclassGec > 0) {
            notifications.add("You have " + numNotReclassGec + " client types without reclassifying.");
        }        
        if (numNotReclassEmpaque > 0) {
            notifications.add("You have " + numNotReclassEmpaque + " packings without reclassifying.");
        }        
        if (numNotReclassDiasOp > 0) {
            notifications.add("You have " + numNotReclassDiasOp + " operative days without reclassifying.");
        }        
        session.setAttribute("notifications_user", notifications);        
    }
}
