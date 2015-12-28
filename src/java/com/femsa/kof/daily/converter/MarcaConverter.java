package com.femsa.kof.daily.converter;

import com.femsa.kof.daily.dao.CatMarcaDAO;
import com.femsa.kof.daily.pojos.RvvdCatMarca;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("marcaConverter")
public class MarcaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                CatMarcaDAO marcaDAO = new CatMarcaDAO();
                RvvdCatMarca marca = marcaDAO.getMarca(new Integer(value));
                if (marca != null) {
                    return marca;
                }
            } catch (NumberFormatException e) {
                return null;
            }
            return null;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            if (value instanceof RvvdCatMarca) {
                RvvdCatMarca marca = (RvvdCatMarca) value;
                return marca.getIdMarca() + "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
