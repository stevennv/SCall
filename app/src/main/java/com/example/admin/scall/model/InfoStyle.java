package com.example.admin.scall.model;

import java.io.Serializable;

/**
 * Created by Admin on 11/22/2017.
 */

public class InfoStyle implements Serializable {
    public InfoStyle(int id, String name, String phone, String font, String urlImage, int color, int size, int animation) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.font = font;
        this.color = color;
        this.size = size;
        this.urlImage = urlImage;
        this.animation = animation;
    }

    public InfoStyle(String name, String phone, String font, int color, int size) {
        this.phone = phone;
        this.name = name;
        this.font = font;
        this.color = color;
        this.size = size;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String font;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int color;
    private int size;


    public InfoStyle() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int animation;

    public int getAnimation() {
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    private String urlImage;

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
