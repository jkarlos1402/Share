/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femsa.kof.daily.converter;

import com.femsa.kof.daily.dao.CatZonaDAO;
import com.femsa.kof.daily.pojos.RvvdCatZona;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author TMXIDSJPINAM
 */
@FacesConverter("zonaConverter")
public class ZonaConverter implements Converter{
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
                CatZonaDAO zonaDAO = new CatZonaDAO();
                RvvdCatZona zona = zonaDAO.getZona(new Integer(value));
                if (zona != null) {
                    return zona;
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
            if (value instanceof RvvdCatZona) {
                RvvdCatZona zona = (RvvdCatZona) value;
                return zona.getIdZona()+ "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
