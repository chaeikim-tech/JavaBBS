package com.board.entity;

import java.util.Date;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
public class Board extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "view", columnDefinition = "integer default 0", nullable = false)
    private Integer view;




    public Long getId() {
        return this.id;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    
    public Integer getView() {
        return this.view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Board() {
	}
    
    @Builder
    public Board(Long id, String name, String title, String content, Date createAt, Integer view) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.view = view;
    }


}