/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pox.foodmenu.server.jjayaku1.netbeans8;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author hp pc
 */

@XmlRootElement(name="SelectedFoodItems")
public class SelectedFoodItems {
    private ArrayList<Integer> FoodItemIDs = new ArrayList<Integer>();

    public SelectedFoodItems() {
    }

    public ArrayList<Integer> getFoodItemIDs() {
        return FoodItemIDs;
    }
    
    @XmlElement(name="FoodItemId")
    public void setFoodItemIDs(ArrayList<Integer> FoodItemIDs) {
        this.FoodItemIDs = FoodItemIDs;
    }
    
    
}
