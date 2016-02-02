package com.femsa.kof.share.converter;

import com.femsa.kof.share.dao.ShareCatProyectoDAO;
import com.femsa.kof.share.pojos.ShareCatProyecto;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author TMXIDSJPINAM
 */
@FacesConverter("proyectoConverter")
public class ProyectoConverter implements Converter {

    /**
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                ShareCatProyectoDAO proyectoDAO = new ShareCatProyectoDAO();
                ShareCatProyecto proyecto = proyectoDAO.getProyecto(new Integer(value));
                if (proyecto != null) {
                    return proyecto;
                }
            } catch (NumberFormatException e) {
                return null;
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            if (value instanceof ShareCatProyecto) {
                ShareCatProyecto proyecto = (ShareCatProyecto) value;
                return proyecto.getIdProyecto() + "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
