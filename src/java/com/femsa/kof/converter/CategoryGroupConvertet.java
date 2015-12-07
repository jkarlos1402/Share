package com.femsa.kof.converter;

import com.femsa.kof.dao.ShareCatGrupoCategoriasDAO;
import com.femsa.kof.pojos.ShareCatGrupoCategorias;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("groupCategoryConverter")
public class CategoryGroupConvertet implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                ShareCatGrupoCategorias groupT = new ShareCatGrupoCategorias();
                groupT.setPkGrupoCategoria(new Integer(value));
                ShareCatGrupoCategoriasDAO grupoCategoriasDAO = new ShareCatGrupoCategoriasDAO();
                List<ShareCatGrupoCategorias> groups = grupoCategoriasDAO.getCategoryGroups();
                for (ShareCatGrupoCategorias group : groups) {
                    if (group.equals(groupT)) {
                        return group;
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

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            if (value instanceof ShareCatGrupoCategorias) {
                ShareCatGrupoCategorias group = (ShareCatGrupoCategorias) value;
                return group.getPkGrupoCategoria() + "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
