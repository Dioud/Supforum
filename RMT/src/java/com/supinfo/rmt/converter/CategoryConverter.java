package com.supinfo.rmt.converter;

import com.supinfo.rmt.entity.Category;
import com.supinfo.rmt.service.CategoryService;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


@ManagedBean
public class CategoryConverter implements Converter {
    
    @EJB
    private CategoryService categoryService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return categoryService.findById(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((Category) value).getId());
    } 
    
}
