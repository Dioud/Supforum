package com.supinfo.rmt.service;

import com.supinfo.rmt.entity.Board;
import com.supinfo.rmt.entity.Message;
import com.supinfo.rmt.entity.Topic;
import com.supinfo.rmt.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TopicService {

    @PersistenceContext
    private EntityManager em;

    public Topic save(Topic topic) {
        em.persist(topic);
        return topic;
    }

    public List<Topic> getAll() {
        return em.createQuery("SELECT c FROM Topic c").getResultList();
    }

    public Topic findById(Long id) {
        return em.find(Topic.class, id);
    }

    public void remove(Topic topic) {
        em.remove(em.merge(topic));
    }

    public Topic update(Topic topic) {
        em.merge(topic);
        return topic;
    }

    public List<Topic> getTopicByBoard(Board board) {

        return em.createQuery("SELECT w FROM Topic w WHERE w.board=:board")
                .setParameter("board", board)
                .getResultList();
    }

    public List<Topic> getTopicByUser(User user) {

        return em.createQuery("SELECT w FROM Topic w WHERE w.user=:user")
                .setParameter("user", user)
                .getResultList();
    }

}
