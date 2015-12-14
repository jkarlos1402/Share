package com.femsa.kof.converter;

import com.femsa.kof.dao.ShareCatRolDAO;
import com.femsa.kof.pojos.ShareCatRol;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("rolConverter")
public class RolConverter implements Converter {

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
