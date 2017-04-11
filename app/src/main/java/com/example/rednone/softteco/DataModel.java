package com.example.rednone.softteco;

/**
 * Created by RedNone on 08.04.2017.
 */

public class DataModel {

   private String userId = "";
   private String id = "";
   private String title = "";

    public DataModel(String userId, String id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
