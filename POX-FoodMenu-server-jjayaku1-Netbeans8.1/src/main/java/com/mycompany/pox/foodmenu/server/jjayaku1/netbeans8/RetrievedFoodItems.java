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
@XmlRootElement(name="RetrievedFoodItems")
public class RetrievedFoodItems {
    private ArrayList<FoodItem> result = new ArrayList<FoodItem>();

    public RetrievedFoodItems() {
    }

    public ArrayList<FoodItem> getResult() {
        return result;
    }
    
    @XmlElement
    public void setResult(ArrayList<FoodItem> result) {
        this.result = result;
    }
    
    
}
