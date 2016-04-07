/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pox.foodmenu.server.jjayaku1.netbeans8;

import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author hp pc
 */
public class ServletContextClass implements ServletContextListener {
    
    public static HashMap<Integer, FoodItem> tableOfItems = new HashMap<Integer, FoodItem>();
    public static HashMap<String,Integer> tableOfFoodNames = new HashMap<String, Integer>();
    public static HashMap<String, Integer> lastIds = new HashMap<String, Integer>();
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        Reader read = new Reader();
        read.ReadXML(tableOfItems, lastIds, tableOfFoodNames);
        
        ServletContext context = arg0.getServletContext();
        context.setAttribute("tableOfItems", tableOfItems);
        context.setAttribute("tableOfFoodNames", tableOfFoodNames);
        context.setAttribute("lastIds", lastIds);
        
    }//end contextInitialized method

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        ServletContext context = arg0.getServletContext();
        context.removeAttribute("tableOfItems");
        context.removeAttribute("tableOfFoodNames");
        context.removeAttribute("lastIds");

    }//end constextDestroyed method

}
