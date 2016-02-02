package com.femsa.kof.share.converter;

import com.femsa.kof.share.dao.ShareCatRolDAO;
import com.femsa.kof.share.pojos.ShareCatRol;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author TMXIDSJPINAM
 */
@FacesConverter("rolConverter")
public class RolConverter implements Converter {

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
                ShareCatRolDAO rolDAO = new ShareCatRolDAO();
                ShareCatRol rolT = rolDAO.getRol(new Integer(value));
                return rolT;

            } catch (NumberFormatException e) {
                return null;
            }            
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
            if (value instanceof ShareCatRol) {
                ShareCatRol rol = (ShareCatRol) value;
                return rol.getPkRol()+ "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
