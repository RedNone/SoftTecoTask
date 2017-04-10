package com.example.rednone.softteco;

import com.orm.SugarRecord;



public class DbModel extends SugarRecord {

    private String name;
    private String nickName;
    private String email;
    private String webSite;
    private String phone;
    private String city;

    public DbModel() {
    }

    public DbModel(String name, String nickName, String email, String webSite, String phone, String city) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.webSite = webSite;
        this.phone = phone;
        this.city = city;
    }

    @Override
    public String toString() {
        return "DbModel{" +
                "name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", webSite='" + webSite + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
