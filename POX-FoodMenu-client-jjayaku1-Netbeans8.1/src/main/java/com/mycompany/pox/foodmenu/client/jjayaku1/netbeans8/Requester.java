/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pox.foodmenu.client.jjayaku1.netbeans8;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author hp pc
 */
import javax.ws.rs.client.WebTarget;
public class Requester {
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/POX-FoodMenu-server-jjayaku1-Netbeans8.1/webresources";
    
    public void GetFoodItem(SelectedFoodItems reqObj)  throws javax.ws.rs.ClientErrorException
    {
        try
        {
        
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("GetFoodItem/");
        //System.out.println(webResource.getURI());
        
        
        ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).post(ClientResponse.class, reqObj);
        if(response.getStatus()!=200)
        {
            throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());

        }
        String output = response.getEntity(String.class);
        System.out.println("Server response : \n");

        System.out.println(output);
        }
        catch(Exception ex)
        {
            throw ex;
        }
        //System.out.print(response.getStatus());
        

        
        
    }
    
    public void AddFoodItem(NewFoodItems newitems)  throws javax.ws.rs.ClientErrorException
    {
        
        try
        {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("AddFoodItem/");
        //System.out.println(webResource.getURI());
       
        
        ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newitems);
        if(response.getStatus()!=200)
        {
            throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
        }
        String output = response.getEntity(String.class);
        System.out.println("Server response : \n");

        System.out.println(output);

        //System.out.print(response.getStatus());
        }
        catch(Exception ex)
        {
            throw ex;
        }
        
    }
    
    

}
