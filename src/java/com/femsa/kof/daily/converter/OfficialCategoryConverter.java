package com.femsa.kof.daily.converter;

import com.femsa.kof.daily.dao.CatCategoriaOficialDAO;
import com.femsa.kof.daily.pojos.RvvdCatCategoriaOficial;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("officialCategoryConverter")
public class OfficialCategoryConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                CatCategoriaOficialDAO categoriaOficialDAO = new CatCategoriaOficialDAO();
                RvvdCatCategoriaOficial categoriaOficial = categoriaOficialDAO.getCategoriaOficial(new Integer(value));
                if (categoriaOficial != null) {
                    return categoriaOficial;
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
            if (value instanceof RvvdCatCategoriaOficial) {
                RvvdCatCategoriaOficial categoriaOficial = (RvvdCatCategoriaOficial) value;
                return categoriaOficial.getIdCategoriaOficial() + "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
