/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlreader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author hp pc
 */
public class Reader {

    ArrayList<FoodItem> lstFoodItems = new ArrayList<FoodItem>();
    HashMap<String, Integer> lastIds = new HashMap<String,Integer>();
    
    HashMap<Integer, FoodItem> tableOfItems = new HashMap<Integer,FoodItem>();
    
    public void ReadXML() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File dataDir = new File("src\\xmlreader\\FoodItemData(1).xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(dataDir);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("FoodItem");
            lastIds.put("GB", 100);
            lastIds.put("US", 200);
            lastIds.put("IN", 300);
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    FoodItem foodItem = new FoodItem();
                    Element eElement = (Element) nNode;
                    int id = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
                    String country = eElement.getAttribute("country");
                    if(id > lastIds.get(country)){
                        lastIds.put(country, id);
                    }
                    foodItem.setCountry(eElement.getAttribute("country"));
                    foodItem.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                    foodItem.setId(Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
                    foodItem.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
                    foodItem.setCategory(eElement.getElementsByTagName("category").item(0).getTextContent());
                    foodItem.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
                    
                    lstFoodItems.add(foodItem);
                    tableOfItems.put(id,foodItem);
                }
            }
            //System.out.println(lstFoodItems.size());
            //System.out.println(lastIds);
            //System.out.println(tableOfItems);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
