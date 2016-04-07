/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pox.foodmenu.server.jjayaku1.netbeans8;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Response;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hp pc
 */
@Path("/")
public class FoodMenu {

    private static final Logger LOG = LoggerFactory.getLogger(FoodMenu.class);
    private static HashMap<Integer, FoodItem> tableOfItems = new HashMap<Integer, FoodItem>();
    private static HashMap<String, Integer> tableOfFoodNames = new HashMap<String, Integer>();
    private static HashMap<String, Integer> lastIds = new HashMap<String, Integer>();

    @Context
    private ServletContext context;

    public FoodMenu() {
        LOG.info("Constructor is called");
        //Reader reader = new Reader();
        //reader.ReadXML(tableOfItems, lastIds,tableOfFoodNames);

    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("AddFoodItem/")
    public Response addFoodItem(NewFoodItems newItems) {
        tableOfItems = (HashMap<Integer, FoodItem>) context.getAttribute("tableOfItems");

        tableOfFoodNames = (HashMap<String, Integer>) context.getAttribute("tableOfFoodNames");

        lastIds = (HashMap<String, Integer>) context.getAttribute("lastIds");

        LOG.info("Entering the AddFoodItem method");
        LOG.debug("POST request");
        LOG.debug("Request Content = {}", newItems);
        ArrayList<FoodItem> items = newItems.getLstFoodItems();
        StringBuilder builder = new StringBuilder();
        builder.append("<AddedFoodItems xmlns=”PoxAssignment”>").append("\n");
        //Writer wr = new Writer();
        try {
            for (FoodItem item : items) {
                String name = item.getName().toLowerCase();
                boolean isExistAlready = false;
                int existingelementId= -1;
                if (tableOfFoodNames.containsKey(name)) {
                    /*int existingid = tableOfFoodNames.get(name);
                    if(tableOfItems.get(existingid).getCategory().equalsIgnoreCase(item.getCategory())){
                        isExistAlready = true;
                    }*/
                    for(FoodItem ele : tableOfItems.values()){
                        if(ele.getName().equalsIgnoreCase(name) && ele.getCategory().equalsIgnoreCase(item.getCategory())){
                            isExistAlready = true;
                            existingelementId = ele.getId();
                            break;
                        }
                    }
                }
                if (isExistAlready) {
                    //Item already present
                    FoodItemExists exists = new FoodItemExists();
                    exists.setFoodItemId(existingelementId);
                    builder.append(ResponseGenerator.buildAddExisting(200, exists)).append("\n");
                } else {
                    String country = item.getCountry().toLowerCase();
                    int id = lastIds.get(country);
                    id = id + 1;
                    item.setId(id);
                    tableOfItems.put(id, item);
                    tableOfFoodNames.put(name, id);
                    lastIds.put(country, id);

                    //item.setId(id);
                    //wr.WriteIntoXML(item);
                    FoodItemAdded added = new FoodItemAdded();
                    added.setFoodItemId(id);
                    builder.append(ResponseGenerator.buildAddFoodItem(200, added)).append("\n");
                }
            }

        } catch (Exception ex) {
            //builder = new StringBuilder();
            builder.append(ResponseGenerator.buildInvalidMessage(200, "<InvalidMessage />")).append("\n");
        }
        builder.append("</AddedFoodItems>");

        Response response;
        response = Response.ok(builder.toString()).build();
        context.setAttribute("tableOfItems", tableOfItems);
        context.setAttribute("tableOfFoodNames", tableOfFoodNames);
        context.setAttribute("lastIds", lastIds);
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("GetFoodItem/")
    public Response getFoodItem(SelectedFoodItems items) {
        LOG.info("Entering the getFoodItem method");
        LOG.debug("POST request");
        tableOfItems = (HashMap<Integer, FoodItem>) context.getAttribute("tableOfItems");

        tableOfFoodNames = (HashMap<String, Integer>) context.getAttribute("tableOfFoodNames");

        lastIds = (HashMap<String, Integer>) context.getAttribute("lastIds");
        Response response;
        StringBuilder builder = new StringBuilder();
        builder.append("<RetrievedFoodItems xmlns=”PoxAssignment”>").append("\n");

        //response = Response.status(Response.Status.OK).entity("SDSD").build();
        RetrievedFoodItems resultList = new RetrievedFoodItems();
        ArrayList<FoodItem> rl = new ArrayList<FoodItem>();
        try {
            for (int id : items.getFoodItemIDs()) {
                FoodItem item = new FoodItem();
                InvalidFoodItem invalid = new InvalidFoodItem();
                if (tableOfItems.containsKey(id)) {
                    item = tableOfItems.get(id);
                    rl.add(item);

                    builder.append(ResponseGenerator.buildFoodITem(200, item)).append("\n");
                } else {
                    invalid.setFoodItemId(id);
                    builder.append(ResponseGenerator.buildInvalidForGet(200, invalid)).append("\n");
                }
            }

        } catch (Exception ex) {
            //builder = new StringBuilder();
            builder.append(ResponseGenerator.buildInvalidMessage(200, "<InvalidMessage />")).append("\n");
        }
        builder.append("</RetrievedFoodItems>");

        response = Response.ok(builder.toString()).build();
        return response;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("test/")
    public Response test() {
        tableOfItems = (HashMap<Integer, FoodItem>) context.getAttribute("tableOfItems");

        tableOfFoodNames = (HashMap<String, Integer>) context.getAttribute("tableOfFoodNames");

        lastIds = (HashMap<String, Integer>) context.getAttribute("lastIds");
        Response response;
        response = Response.status(Response.Status.OK).entity("" + tableOfItems.size()).build();
        return response;

    }

}
