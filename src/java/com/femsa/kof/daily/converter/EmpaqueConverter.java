package com.femsa.kof.daily.converter;

import com.femsa.kof.daily.dao.CatEmpaqueDAO;
import com.femsa.kof.daily.pojos.RvvdCatEmpaque;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("packingConverter")
public class EmpaqueConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                CatEmpaqueDAO empaqueDAO = new CatEmpaqueDAO();
                RvvdCatEmpaque empaque = empaqueDAO.getEmpaque(new Integer(value));
                if (empaque != null) {
                    return empaque;
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
            if (value instanceof RvvdCatEmpaque) {
                RvvdCatEmpaque empaque = (RvvdCatEmpaque) value;
                return empaque.getIdEmpaque()+ "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
