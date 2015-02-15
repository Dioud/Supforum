package com.supinfo.rmt.service;

import com.supinfo.rmt.entity.Topic;
import com.supinfo.rmt.entity.Message;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class MessageService {

    @PersistenceContext
    private EntityManager em;

    public Message save(Message message) {
        em.persist(message);
        return message;
    }

    public List<Message> getAll() {
        return em.createQuery("SELECT c FROM Message c").getResultList();
    }

    public Message findById(Long id) {
        return em.find(Message.class, id);
    }

    public void remove(Message message) {
        em.remove(em.merge(message));
    }

    public Message update(Message message) {
        em.merge(message);
        return message;
    }

    public List<Message> getMessageByTopic(Topic topic) {

        return em.createQuery("SELECT w FROM Message w WHERE w.topic=:topic")
                .setParameter("topic", topic)
                .getResultList();
    }
    
 
}
