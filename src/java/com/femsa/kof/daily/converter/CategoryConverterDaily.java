package com.femsa.kof.daily.converter;

import com.femsa.kof.daily.dao.CatCategoriaDAO;
import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("categoryConverterDaily")
public class CategoryConverterDaily implements Converter{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                CatCategoriaDAO categoriaDAO = new CatCategoriaDAO();
                RvvdCatCategoria categoria = categoriaDAO.getCategoria(new Integer(value));
                if (categoria != null) {
                    return categoria;
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
            if (value instanceof RvvdCatCategoria) {
                RvvdCatCategoria categoria = (RvvdCatCategoria) value;
                return categoria.getIdCategoria() + "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
