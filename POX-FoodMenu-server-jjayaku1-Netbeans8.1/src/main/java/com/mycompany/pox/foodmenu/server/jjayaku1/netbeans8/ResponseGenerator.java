/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pox.foodmenu.server.jjayaku1.netbeans8;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 *
 * @author hp pc
 */
public class ResponseGenerator {

    public static String buildFoodITem(int code, FoodItem message) {
        String result = "";
        try {
            JAXBContext jc = JAXBContext.newInstance(FoodItem.class);
            StringWriter writerTo = new StringWriter();
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(message, writerTo);

            result = writerTo.toString();
            return result;
        } catch (Exception ex) {
            result = "<InvalidMessage />";
            return result;
        }
    }

    public static String buildInvalidForGet(int code, InvalidFoodItem message) {
        String result = "";
        try {
            JAXBContext jc = JAXBContext.newInstance(InvalidFoodItem.class);
            StringWriter writerTo = new StringWriter();
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(message, writerTo);

            result = writerTo.toString();
            return result;
        } catch (Exception ex) {
            result = "<InvalidMessage />";
            return result;
        }
    }

    public static String buildInvalidMessage(int code, String message) {
        String result = "";
        result = "<InvalidMessage />";
        return result;
    }

    public static String buildAddExisting(int code, FoodItemExists message) {
        String result = "";
        try {
            JAXBContext jc = JAXBContext.newInstance(FoodItemExists.class);
            StringWriter writerTo = new StringWriter();
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(message, writerTo);

            result = writerTo.toString();
            return result;
        } catch (Exception ex) {
            result = "<InvalidMessage />";
            return result;
        }
    }

    public static String buildAddFoodItem(int code, FoodItemAdded message) {
        String result = "";
        try {
            JAXBContext jc = JAXBContext.newInstance(FoodItemAdded.class);
            StringWriter writerTo = new StringWriter();
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(message, writerTo);

            result = writerTo.toString();
            return result;
        } catch (Exception ex) {
            result = "<InvalidMessage />";
            return result;
        }
    }

}
