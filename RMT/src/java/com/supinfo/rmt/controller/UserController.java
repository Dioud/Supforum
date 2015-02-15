package com.supinfo.rmt.controller;

import com.supinfo.rmt.entity.Message;
import com.supinfo.rmt.entity.User;
import com.supinfo.rmt.service.UserService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.validator.constraints.NotEmpty;

@ManagedBean
@SessionScoped
public class UserController {

    @EJB
    private UserService userService;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private User user = new User();
    private String userid;

    public String create() {

        user.setRole("user");
        Date d = new Date();
        user.setCreationDate(d);
        userService.save(user);
        return "forum?faces-redirect=true";
    }

    public String login() {
        user = userService.login(username, password);

        if (user == null) {
            return null;
        }

        username = null;
        password = null;
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("user", user);
        context.getExternalContext().getSessionMap().put("userid", user.getId());

        return "forum?faces-redirect=true";
    }

    public String getPassword() {
        return password;
    }

        private Integer countall;

    public void setCountall(Integer countall) {
        this.countall = countall;
    }

    public Integer getCountall() {
          List<User> users = userService.getAll();
          countall = users.size();
        return countall;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        System.out.println(user.getUsername());
        return user;
    }

    public String logout() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        final HttpServletRequest r = (HttpServletRequest) ec.getRequest();
        r.getSession(false).invalidate();
        return "/login.xhtml";
    }

    public void edit() {
        userService.update(user);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect("profile.xhtml?profileid=" + getUser().getId());
        } catch (IOException ex) {

        }
    }
}
