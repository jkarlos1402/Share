package com.femsa.kof.daily.converter;

import com.femsa.kof.daily.dao.CatUnidadNegocioDAO;
import com.femsa.kof.daily.pojos.RvvdCatUnidadNegocio;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author TMXIDSJPINAM
 */
@FacesConverter("unidadNegocioConverter")
public class UnidadNegocioConverter implements Converter{

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
                CatUnidadNegocioDAO unidadNegocioDAO = new CatUnidadNegocioDAO();
                RvvdCatUnidadNegocio unidad = unidadNegocioDAO.getUnidadNeg(new Integer(value));
                if (unidad != null) {
                    return unidad;
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
            if (value instanceof RvvdCatUnidadNegocio) {
                RvvdCatUnidadNegocio unidad = (RvvdCatUnidadNegocio) value;
                return unidad.getIdUnidadNegocio()+ "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
