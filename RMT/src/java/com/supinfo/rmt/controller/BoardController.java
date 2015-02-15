package com.supinfo.rmt.controller;

import com.supinfo.rmt.entity.Board;
import com.supinfo.rmt.service.BoardService;
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
public class BoardController {

    @EJB
    private BoardService boardService;

   
    private List<SelectItem> selectItems;

    private Board board = new Board();
    
    private DataModel<Board> dataModel;

    public String create() {
        boardService.save(board);
        return "manage?faces-redirect=true";
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public DataModel<Board> getDataModel() {
        if (dataModel == null) {
            List<Board> boards = boardService.getAll();
            System.out.println("Boards : " + boards.size());
            dataModel = new ListDataModel<Board>(boards);
        }

        return dataModel;
    }

  

    public String edit() {
        boardService.update(board);
        return "manage?faces-redirect=true";
    }

    public void prepareEdit() {
        board = (Board) boardService.findById(dataModel.getRowData().getId());

    }

    public String remove() {
        boardService.remove(dataModel.getRowData());
        return "manage?faces-redirect=true";
    }

    public String showTopic() {
        board = (Board) boardService.findById(dataModel.getRowData().getId());
        
        return "topic?faces-redirect=true";
    }
    
        public List<SelectItem> getSelectItems() {
        if (selectItems == null) {
            selectItems = new ArrayList<SelectItem>();
            for (Board board : boardService.getAll()) {
                selectItems.add(new SelectItem(board, board.getName()));
            }
        }

        return selectItems;
    }

}
