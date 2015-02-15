package com.supinfo.rmt.controller;

import com.supinfo.rmt.entity.User;
import com.supinfo.rmt.entity.Board;
import com.supinfo.rmt.entity.Message;
import com.supinfo.rmt.entity.Topic;
import com.supinfo.rmt.entity.User;
import com.supinfo.rmt.service.BoardService;
import com.supinfo.rmt.service.TopicService;
import com.supinfo.rmt.service.UserService;
import com.supinfo.rmt.service.UserService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.validator.constraints.NotEmpty;

@ManagedBean
@ViewScoped
public class ProfileController {

    @EJB
    private UserService userService;

    @EJB
    private TopicService topicService;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private User user = new User();
    private String userid;

    private Integer countalltopic;

    public Integer getCountalltopic() {
        List<Topic> topics = topicService.getTopicByUser(user);
        countalltopic = topics.size();
        return countalltopic;
    }

    public void setCountalltopic(Integer countalltopic) {
        this.countalltopic = countalltopic;
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

    public String getPassword() {
        return password;
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
        userid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("profileid");
        if (userid != null) {
            user = userService.findById(Long.valueOf(userid));
        }
        return user;
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
