/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pox.foodmenu.client.jjayaku1.netbeans8;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author hp pc
 */

@XmlRootElement(name="FoodItem")
public class FoodItem {

    public String getCountry() {
        return country;
    }

    @XmlAttribute(name="country")
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getName() {
        return name;
    }
    
    @XmlElement(name="name")
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @XmlElement(name="id")
    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement(name="description")
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    @XmlElement(name="category")
    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    @XmlElement(name="price")
    public void setPrice(double price) {
        this.price = price;
    }

    public FoodItem() {
    }

    public FoodItem(String country, String name, int id, String description, String category, double price) {
        this.country = country;
        this.name = name;
        this.id = id;
        this.description = description;
        this.category = category;
        this.price = price;
    }
    
    private String country;
    private String name;
    private int id;
    private String description;
    private String category;
    private double price;
    
}
