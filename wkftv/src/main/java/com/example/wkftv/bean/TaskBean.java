package com.example.wkftv.bean;

public class TaskBean {
   private String id;

    public String getTextsize() {
        return textsize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextsize(String textsize) {
        this.textsize = textsize;
    }

    private String textsize;
   private  String background;
   private String text;
}
