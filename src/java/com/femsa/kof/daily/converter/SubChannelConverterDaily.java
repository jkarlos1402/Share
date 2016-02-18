package com.femsa.kof.daily.converter;

import com.femsa.kof.daily.dao.CatCanalDAO;
import com.femsa.kof.daily.dao.CatSubCanalDAO;
import com.femsa.kof.daily.pojos.RvvdCatCanal;
import com.femsa.kof.daily.pojos.RvvdCatSubCanal;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author TMXIDSJPINAM
 */
@FacesConverter("subChannelConverterDaily")
public class SubChannelConverterDaily implements Converter {

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
                CatSubCanalDAO catSubCanalDAO = new CatSubCanalDAO();
                RvvdCatSubCanal subCanal = catSubCanalDAO.getSubCanal(new Integer(value));
                if (subCanal != null) {
                    return subCanal;
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
            if (value instanceof RvvdCatSubCanal) {
                RvvdCatSubCanal subCanal = (RvvdCatSubCanal) value;
                return subCanal.getIdSubCanal() + "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
