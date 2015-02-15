package com.supinfo.rmt.service;

import com.supinfo.rmt.entity.Topic;
import com.supinfo.rmt.entity.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager em;

    public User save(User user) {

        try {

            Query q = em.createQuery("SELECT u FROM User u WHERE u.username=:username");
            q.setParameter("username", user.getUsername());
            q.getSingleResult();

            return null;

        } catch (NoResultException e) {

        }
        try {
            user.setPassword(encode(user.getPassword()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(user.getRole());
        em.persist(user);
        return user;
    }

    public User login(String username, String password) {

        User user = null;

        try {
            Query q = em.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:password");
            q.setParameter("username", username);
            q.setParameter("password", encode(password));

            user = (User) q.getSingleResult();

        } finally {
            return user;
        }
    }

    private static String encode(String password)
            throws NoSuchAlgorithmException {
        byte[] hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            hash = md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hash.length; ++i) {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                sb.append(0);
                sb.append(hex.charAt(hex.length() - 1));
            } else {
                sb.append(hex.substring(hex.length() - 2));
            }
        }
        return sb.toString();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public User update(User user) {
        em.merge(user);
        return user;
    }

    public List<User> getAll() {
        return em.createQuery("SELECT c FROM User c").getResultList();
    }
}
