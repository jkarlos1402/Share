package com.femsa.kof.daily.converter;

import com.femsa.kof.daily.dao.CatContCaloricoDAO;
import com.femsa.kof.daily.pojos.RvvdCatContenidoCalorico;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author TMXIDSJPINAM
 */
@FacesConverter("contenidoCaloricoConverter")
public class ContCaloricoConverter implements Converter {

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
                CatContCaloricoDAO contCaloricoDAO = new CatContCaloricoDAO();
                RvvdCatContenidoCalorico contenido = contCaloricoDAO.getContCal(new Integer(value));
                if (contenido != null) {
                    return contenido;
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
            if (value instanceof RvvdCatContenidoCalorico) {
                RvvdCatContenidoCalorico contenido = (RvvdCatContenidoCalorico) value;
                return contenido.getIdContenidoCalorico() + "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
