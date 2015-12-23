package com.femsa.kof.daily.converter;

import com.femsa.kof.daily.dao.CatTipoConsumoDAO;
import com.femsa.kof.daily.pojos.RvvdCatTipoConsumo;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("consumptionConverter")
public class TipoConsumoConverterDaily implements Converter{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                CatTipoConsumoDAO tipoConsumoDAO = new CatTipoConsumoDAO();
                RvvdCatTipoConsumo tipoConsumo = tipoConsumoDAO.getTipoConsumo(new Integer(value));
                if (tipoConsumo != null) {
                    return tipoConsumo;
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
            if (value instanceof RvvdCatTipoConsumo) {
                RvvdCatTipoConsumo tipoConsumo = (RvvdCatTipoConsumo) value;
                return tipoConsumo.getIdTipoConsumo()+ "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
