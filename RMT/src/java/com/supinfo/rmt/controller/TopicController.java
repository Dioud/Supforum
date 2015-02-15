package com.supinfo.rmt.controller;

import com.supinfo.rmt.entity.Topic;
import com.supinfo.rmt.entity.Board;
import com.supinfo.rmt.entity.User;
import com.supinfo.rmt.service.BoardService;
import com.supinfo.rmt.service.TopicService;
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
public class TopicController {

    @EJB
    private TopicService topicService;

    @EJB
    private BoardService boardService;

    private Topic topic = new Topic();

    private DataModel<Topic> dataModel;
    private String boardid;
    private String topicid;
    private Board board;

    private Integer countall;

    public void setCountall(Integer countall) {
        this.countall = countall;
    }

    public Integer getCountall() {
        List<Topic> topics = topicService.getAll();
        countall = topics.size();
        return countall;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        boardid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("boardid");
        FacesContext context = FacesContext.getCurrentInstance();
        if (boardid == null) {
            board = boardService.findById(Long.valueOf(context.getExternalContext().getSessionMap().get("board").toString()));
        } else {
            board = boardService.findById(Long.valueOf(boardid));
            context.getExternalContext().getSessionMap().put("board", board.getId());
        }
        return board;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    private void redirection() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect("board.xhtml?boardid=" + topic.getBoard().getId());
        } catch (IOException ex) {

        }
    }

    public void create() {
        FacesContext context = FacesContext.getCurrentInstance();

        User user = (User) context.getExternalContext().getSessionMap().get("user");

        Date d = new Date();
        topic.setCreationDate(d);
        topic.setBoard(board);
        topic.setUser(user);
        topicService.save(topic);
        redirection();

    }

    public void remove() {
        topic = dataModel.getRowData();
        topicService.remove(dataModel.getRowData());
        System.out.println(topic.getName());
        redirection();
    }

    public Topic getTopic() {
        topicid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("topicid");
        if (topicid != null) {
            topic = topicService.findById(Long.valueOf(topicid));
        }

        return topic;
    }

    public void setTopic(Topic topic) {

        this.topic = topic;
    }

    public DataModel<Topic> getDataModel() {
        if (dataModel == null) {

            List<Topic> topics = topicService.getTopicByBoard(board);
            System.out.println("Topics : " + topics.size());
            dataModel = new ListDataModel<Topic>(topics);
        }

        return dataModel;
    }

    public void edit() {
        topicService.update(topic);
        redirection();
    }
}
