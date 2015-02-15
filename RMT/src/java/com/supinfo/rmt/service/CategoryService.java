package com.supinfo.rmt.service;

import com.supinfo.rmt.entity.Category;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CategoryService {
 
    @PersistenceContext
    private EntityManager em;
    
    public Category save(Category category) {
        em.persist(category);
        return category;
    }
    
        public Category update(Category category) {
        em.merge(category);
        return category;
    }
    public List<Category> getAll() {
        return em.createQuery("SELECT c FROM Category c").getResultList();
    }

    public Category findById(Long id) {
        return em.find(Category.class, id);
    }
    
    public void remove(Category category) {
        em.remove(em.merge(category));
    }
    
}
