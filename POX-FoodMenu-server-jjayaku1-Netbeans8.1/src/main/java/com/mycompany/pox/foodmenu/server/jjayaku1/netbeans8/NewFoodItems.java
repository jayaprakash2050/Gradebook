/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pox.foodmenu.server.jjayaku1.netbeans8;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author hp pc
 */
@XmlRootElement(name="NewFoodItems")
public class NewFoodItems {
    private ArrayList<FoodItem> lstFoodItems = new ArrayList<FoodItem>();

    public NewFoodItems() {
    }

    public ArrayList<FoodItem> getLstFoodItems() {
        return lstFoodItems;
    }
    
    @XmlElement
    public void setLstFoodItems(ArrayList<FoodItem> lstFoodItems) {
        this.lstFoodItems = lstFoodItems;
    }
    
    
    
}
