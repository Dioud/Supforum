package com.supinfo.rmt.controller;

import com.supinfo.rmt.entity.Category;
import com.supinfo.rmt.service.CategoryService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;


@ManagedBean
@ViewScoped
public class CategoryController {

    @EJB
    private CategoryService categoryService;

    private Category category;

    private List<SelectItem> selectItems;

    private DataModel<Category> dataModel;

    public String create() {
        categoryService.save(category);

        return "manage?faces-redirect=true";
    }

    public String edit() {
        categoryService.update(category);
        return "manage?faces-redirect=true";
    }

    public void prepareEdit() {
        category = (Category) categoryService.findById(dataModel.getRowData().getId());

    }

    public Category getCategory() {
        if (category == null) {
            category = new Category();
        }
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<SelectItem> getSelectItems() {
        if (selectItems == null) {
            selectItems = new ArrayList<SelectItem>();
            for (Category category : categoryService.getAll()) {
                selectItems.add(new SelectItem(category, category.getName()));
            }
        }

        return selectItems;
    }

    public DataModel<Category> getDataModel() {
        if (dataModel == null) {
            List<Category> categorys = categoryService.getAll();
            System.out.println("Categorys : " + categorys.size());
            dataModel = new ListDataModel<Category>(categorys);
        }
        return dataModel;
    }

    public String remove() {
        categoryService.remove(dataModel.getRowData());
        getSelectItems();
        return "manage?faces-redirect=true";
    }

}
