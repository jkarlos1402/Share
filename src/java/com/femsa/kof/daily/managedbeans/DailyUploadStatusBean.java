package com.femsa.kof.daily.managedbeans;

import com.femsa.kof.share.dao.ShareLoadLogDAO;
import com.femsa.kof.share.pojos.ShareLoadLog;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TMXIDSJPINAM
 */
@ManagedBean
@SessionScoped
public class DailyUploadStatusBean implements Serializable {

    private List<ShareLoadLog> cargasMes = new ArrayList<ShareLoadLog>();
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    private ShareUsuario usuario;

    /**
     * Se encarga de cargar la lista de logs de carga para el usuario que se
     * encuetra en sesión
     */
    public DailyUploadStatusBean() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario = (ShareUsuario) httpSession.getAttribute("session_user");
        ShareLoadLogDAO logDAO = new ShareLoadLogDAO();
        cargasMes = usuario.getRol().getPkRol().equals(2) ? logDAO.getLogByUser(usuario, "daily") : logDAO.getLogAll("daily");
    }

    public ShareUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(ShareUsuario usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return
     */
    public SimpleDateFormat getDateTimeFormat() {
        return dateTimeFormat;
    }

    /**
     *
     * @param dateTimeFormat
     */
    public void setDateTimeFormat(SimpleDateFormat dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    /**
     *
     * @return
     */
    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     *
     * @param dateFormat
     */
    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     *
     * @return
     */
    public List<ShareLoadLog> getCargasMes() {
        return cargasMes;
    }

    /**
     *
     * @param cargasMes
     */
    public void setCargasMes(List<ShareLoadLog> cargasMes) {
        this.cargasMes = cargasMes;
    }

    /**
     * Refresca la lista de logs de carga para el usuario en sesión
     *
     */
    public void refresh() {
        ShareLoadLogDAO logDAO = new ShareLoadLogDAO();
        cargasMes = usuario.getRol().getPkRol().equals(2) ? logDAO.getLogByUser(usuario, "daily") : logDAO.getLogAll("daily");
    }

}
