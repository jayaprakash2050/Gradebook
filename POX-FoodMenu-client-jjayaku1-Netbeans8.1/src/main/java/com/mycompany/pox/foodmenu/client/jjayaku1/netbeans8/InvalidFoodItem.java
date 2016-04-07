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
@XmlRootElement(name="InvalidFoodItem")
public class InvalidFoodItem {
    private int id;

    public InvalidFoodItem() {
    }

    public int getId() {
        return id;
    }
    
    @XmlElement(name="FoodItemId")
    public void setId(int id) {
        this.id = id;
    }
    
}
