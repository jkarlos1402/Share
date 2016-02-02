package com.femsa.kof.share.converter;

import com.femsa.kof.share.pojos.ShareCatPais;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;

/**
 *
 * @author TMXIDSJPINAM
 */
@FacesConverter("countryConverter")
public class CountryConverter implements Converter {

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
                ShareCatPais countryT = new ShareCatPais();                
                countryT.setPkPais(new Integer(value));                
                ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                List<ShareCatPais> countries = (List<ShareCatPais>) sc.getAttribute("countries_catalog");                
                for (ShareCatPais country : countries) {
                    if (country.equals(countryT)) {
                        return country;
                    }
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
            if (value instanceof ShareCatPais) {
                ShareCatPais country = (ShareCatPais) value;
                return country.getPkPais() + "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
