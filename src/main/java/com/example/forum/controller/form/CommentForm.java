package com.example.forum.controller.form;

import com.example.forum.validation.NotOnlyWhitespace;

import java.util.Date;

// Viewへの入出力に使用
public class CommentForm {

    private int id;

    @NotOnlyWhitespace(message = "コメントを入力してください")
    private String comment;

    private int contentId;
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
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    //---------
    public int getContentId() {
        return contentId;
    }
    public void setContentId(int contentId) {
        this.contentId = contentId;
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
