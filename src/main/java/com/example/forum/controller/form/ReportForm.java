package com.example.forum.controller.form;

import java.util.Date;

import com.example.forum.validation.NotOnlyWhitespace;

// Viewへの入出力に使用
public class ReportForm {

    private int id;

    @NotOnlyWhitespace(message = "投稿内容を入力してください")
    private String content;

    private Date createdDate;
    private Date updatedDate;

    //---------
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //---------
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    //---------
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    //---------
    public Date getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
