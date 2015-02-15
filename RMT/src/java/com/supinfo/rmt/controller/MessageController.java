package com.supinfo.rmt.controller;

import com.supinfo.rmt.entity.Message;
import com.supinfo.rmt.entity.Topic;
import com.supinfo.rmt.entity.User;
import com.supinfo.rmt.service.TopicService;
import com.supinfo.rmt.service.MessageService;
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


@ManagedBean
@ViewScoped
public class MessageController {

    @EJB
    private MessageService messageService;

    @EJB
    private TopicService topicService;



    private Message message = new Message();
    

    private DataModel<Message> dataModel;
    private String topicid;
    private String messageid;
    private Topic topic;
    
    private Integer countall;

    public void setCountall(Integer countall) {
        this.countall = countall;
    }

    public Integer getCountall() {
          List<Message> messages = messageService.getAll();
          countall = messages.size();
        return countall;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        topicid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("topicid");
        FacesContext context = FacesContext.getCurrentInstance();
        if (topicid == null) {
            topic = topicService.findById(Long.valueOf(context.getExternalContext().getSessionMap().get("topic").toString()));
        } else {
            topic = topicService.findById(Long.valueOf(topicid));
            context.getExternalContext().getSessionMap().put("topic", topic.getId());
        }
        return topic;
    }

    public String getTopicid() {
        return topicid;
    }

    public void setTopicid(String topicid) {
        this.topicid = topicid;
    }

    private void redirection() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect("topic.xhtml?topicid=" + message.getTopic().getId());
        } catch (IOException ex) {

        }
    }

    public void create() {
                FacesContext context = FacesContext.getCurrentInstance();

        User user = (User) context.getExternalContext().getSessionMap().get("user");

        Date d = new Date();
        message.setCreationDate(d);
         message.setTopic(topic);
        message.setUser(user);
        messageService.save(message);
        redirection();

    }

    public void remove() {
        message = dataModel.getRowData();
        messageService.remove(dataModel.getRowData());
        redirection();
    }

    public Message getMessage() {
            messageid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("messageid");
            if(messageid != null){
                message = messageService.findById(Long.valueOf(messageid));
            }
            
        return message;
    }

    public void setMessage(Message message) {
            
        this.message = message;
    }

    public DataModel<Message> getDataModel() {
        if (dataModel == null) {

            List<Message> messages = messageService.getMessageByTopic(topic);
            System.out.println("Messages : " + messages.size());
            dataModel = new ListDataModel<Message>(messages);
        }

        return dataModel;
    }




    public void edit() {
        messageService.update(message);
        redirection();
    }
}
