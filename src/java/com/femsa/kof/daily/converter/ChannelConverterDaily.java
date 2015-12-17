package com.femsa.kof.daily.converter;

import com.femsa.kof.daily.dao.CatCanalDAO;
import com.femsa.kof.daily.dao.CatCategoriaDAO;
import com.femsa.kof.daily.pojos.RvvdCatCanal;
import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("channelConverterDaily")
public class ChannelConverterDaily implements Converter{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                CatCanalDAO catCanalDAO = new CatCanalDAO();
                RvvdCatCanal canal = catCanalDAO.getCanal(new Integer(value));
                if (canal != null) {
                    return canal;
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
            if (value instanceof RvvdCatCanal) {
                RvvdCatCanal canal = (RvvdCatCanal) value;
                return canal.getIdCanal()+ "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    
}
