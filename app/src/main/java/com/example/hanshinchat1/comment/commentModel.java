package com.example.hanshinchat1.comment;

public class commentModel {

    private String commentTitle;
    private String commentCreatedtime;

    public commentModel() {}

    public commentModel(String commentTitle, String commentCreatedtime) {
        this.commentTitle = commentTitle;
        this.commentCreatedtime = commentCreatedtime;
    }

    public String getCommentTitle() { return commentTitle; }

    public void setCommentTitle(String commentTitle) { this.commentTitle = commentTitle; }

    public String getCommentCreatedtime() {
//        return FBAuth.getTime();
        return commentCreatedtime;
    }
    public void setCommentCreatedtime(String commentCreatedtime) {
        this.commentCreatedtime = commentCreatedtime;
    }

}
