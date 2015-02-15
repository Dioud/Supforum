package com.supinfo.rmt.converter;

import com.supinfo.rmt.entity.Board;
import com.supinfo.rmt.service.BoardService;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


@ManagedBean
public class BoardConverter implements Converter {
    
    @EJB
    private BoardService boardService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return boardService.findById(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((Board) value).getId());
    } 
    
}
