/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pox.foodmenu.client.jjayaku1.netbeans8;

import com.sun.jndi.cosnaming.IiopUrl.Address;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 *
 * @author hp pc
 */
public class App {

    public static void main(String[] args) {
        try {
            System.out.println("Running GetFoodItems method");

            //Add the FoodItem ids that are required here.
            Requester req = new Requester();
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ids.add(100);
            ids.add(101);
            ids.add(300);
            ids.add(555);
            SelectedFoodItems reqObj = new SelectedFoodItems();
            reqObj.setFoodItemIDs(ids);
            req.GetFoodItem(reqObj);
            System.out.println();
            System.out.println("Running AddFoodItems method");
            //New Food Items that has to be added
            FoodItem item1 = new FoodItem("IN", "PaneerButterMasala", -1, "Indian item", "Dinner", 40.0);
            FoodItem item2 = new FoodItem("IN", "ChickenButterMasala", -1, "Indian item", "Dinner", 40.0);
            FoodItem item3 = new FoodItem("US", "KFCChicken", -1, "US item", "Dinner", 40.0);
            FoodItem item4 = new FoodItem("US", "WhooperBurger", -1, "US item", "Dinner", 40.0);
            FoodItem item5 = new FoodItem("GB", "BritishCoffee", -1, "GB item", "Dinner", 40.0);
            FoodItem item6 = new FoodItem("GB", "Steak and Kidney Pie", -1, "GB item", "Dinner", 40.0);
            FoodItem item7 = new FoodItem("IN", "FishCurry", -1, "Indian item", "Lunch", 40.0);
            FoodItem item8 = new FoodItem("IN", "Chettinad Chicken Briyani", -1, "Indian item", "Lunch", 40.0);
            NewFoodItems newitems = new NewFoodItems();
            ArrayList<FoodItem> lst = new ArrayList<FoodItem>();
            lst.add(item1);
            lst.add(item2);
            lst.add(item3);
            lst.add(item4);
            lst.add(item5);
            lst.add(item6);
            lst.add(item7);
            lst.add(item8);
            newitems.setLstFoodItems(lst);

            req.AddFoodItem(newitems);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
