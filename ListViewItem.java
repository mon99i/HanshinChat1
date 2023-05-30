package com.example.hanshinchat1;

public class ListViewItem {
    private String contentArea;
    private String titleArea;

    public ListViewItem(String titleArea, String contentArea) {
        this.titleArea = titleArea;
        this.contentArea = contentArea;
    }

    public ListViewItem(String titleArea) {
    }

    public void setTitle(String title) {
        titleArea = title;
    }
    public void setContent(String content) {
        contentArea = content;
    }

    public String getTitle() {
        return this.titleArea;
    }
    public String getContent() {
        return this.contentArea;
    }
}
