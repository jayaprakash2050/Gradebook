/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jjayaku1.crud.restws;
import com.jjayaku1.crud.jaxb.models.GradeAppeal;
import com.jjayaku1.crud.jaxb.models.GradeBookEntry;
import com.jjayaku1.crud.jaxb.models.GradeItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 *
 * @author jjayaku1
 */
public class ServletContextClass implements ServletContextListener {
    private static List<GradeItem> gradingItems = new ArrayList<GradeItem>();
    private static HashMap<Long, List<GradeBookEntry>> gradeBook = new HashMap<Long, List<GradeBookEntry>>();
    private static int newGradeItemId = 0;
    private static HashMap<Long, List<GradeAppeal>> appeals = new HashMap<Long, List<GradeAppeal>>();
    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        System.out.println("Creating servlet context");
        ServletContext context = arg0.getServletContext();
        context.setAttribute("gradingItems", gradingItems);
        context.setAttribute("gradeBook", gradeBook);
        context.setAttribute("newGradeItemId", newGradeItemId);
        context.setAttribute("appeals", appeals);
        
    }//end contextInitialized method

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        ServletContext context = arg0.getServletContext();
        context.removeAttribute("gradingItems");
        context.removeAttribute("gradeBook");
        context.removeAttribute("newGradeItemId");
        context.removeAttribute("appeals");

    }//end constextDestroyed method
}
