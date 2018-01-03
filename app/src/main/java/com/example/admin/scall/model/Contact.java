package com.example.admin.scall.model;

import java.io.Serializable;

/**
 * Created by Admin on 11/21/2017.
 */

public class Contact implements Serializable {
    private String name;
    private String font;

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getColorName() {
        return colorName;
    }

    public void setColorName(int colorName) {
        this.colorName = colorName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int colorName;
    private int size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contact(int id, String phoneNumber, String name) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }
}
