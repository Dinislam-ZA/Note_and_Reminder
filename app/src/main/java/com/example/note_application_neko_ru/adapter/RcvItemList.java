package com.example.note_application_neko_ru.adapter;

import java.io.Serializable;

public class RcvItemList implements Serializable {

    private int Id = 0;
    private String title;
    private String description;
    private String uri = "empty";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
