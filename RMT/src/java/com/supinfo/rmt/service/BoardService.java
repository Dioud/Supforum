package com.supinfo.rmt.service;

import com.supinfo.rmt.entity.Board;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class BoardService {

    @PersistenceContext
    private EntityManager em;

    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    public List<Board> getAll() {
        return em.createQuery("SELECT b FROM Board b ORDER BY b.category").getResultList();
      
    }

    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    public void remove(Board board) {
        em.remove(em.merge(board));
    }

    public Board update(Board board) {
        em.merge(board);
        return board;
    }
}
