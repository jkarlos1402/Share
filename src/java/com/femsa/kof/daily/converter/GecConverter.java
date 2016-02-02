package com.femsa.kof.daily.converter;

import com.femsa.kof.daily.dao.CatGecDAO;
import com.femsa.kof.daily.pojos.RvvdCatGec;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author TMXIDSJPINAM
 */
@FacesConverter("gecConverter")
public class GecConverter implements Converter {

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
                CatGecDAO gecDAO = new CatGecDAO();
                RvvdCatGec gec = gecDAO.getGec(new Integer(value));
                if (gec != null) {
                    return gec;
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
            if (value instanceof RvvdCatGec) {
                RvvdCatGec gec = (RvvdCatGec) value;
                return gec.getIdGec() + "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
