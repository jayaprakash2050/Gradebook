/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pox.foodmenu.server.jjayaku1.netbeans8;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author hp pc
 */
@XmlRootElement(name = "FoodItemExists")
public class FoodItemExists {
    private int FoodItemId;

    public FoodItemExists() {
    }

    public int getFoodItemId() {
        return FoodItemId;
    }

    @XmlElement(name = "FoodItemId")
    public void setFoodItemId(int FoodItemId) {
        this.FoodItemId = FoodItemId;
    }
    
}
