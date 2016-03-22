package com.femsa.kof.daily.converter;

import com.femsa.kof.daily.dao.CatEmpaqueDAO;
import com.femsa.kof.daily.dao.CatRetornabilidadDAO;
import com.femsa.kof.daily.pojos.RvvdCatEmpaque;
import com.femsa.kof.daily.pojos.RvvdCatRetornabilidad;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author TMXIDSJPINAM
 */
@FacesConverter("retornabilidadConverter")
public class RetornabilidadConverter implements Converter {

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
                CatRetornabilidadDAO catRetornabilidadDAO = new CatRetornabilidadDAO();
                RvvdCatRetornabilidad retornabilidad = catRetornabilidadDAO.getRetornabilidad(new Integer(value));
                if (retornabilidad != null) {
                    return retornabilidad;
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
            if (value instanceof RvvdCatRetornabilidad) {
                RvvdCatRetornabilidad retornabilidad = (RvvdCatRetornabilidad) value;
                return retornabilidad.getIdRetornabilidad() + "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
