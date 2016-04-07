/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlreader;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author hp pc
 */
public class Writer {

    public void WriteIntoXML(FoodItem item) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File dataDir = new File("src\\xmlreader\\FoodItemData(1).xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(dataDir);

            doc.getDocumentElement().normalize();
            
            Element dataTag = doc.getDocumentElement();
            
            Element newFoodItem = doc.createElement("FoodItem");
            Element name = doc.createElement("name");
            name.setTextContent(item.getName());
            Element id = doc.createElement("id");
            id.setTextContent(String.valueOf(item.getId()));
            Element description = doc.createElement("description");
            description.setTextContent(item.getDescription());
            Element category = doc.createElement("category");
            category.setTextContent(item.getCategory());
            Element price = doc.createElement("price");
            price.setTextContent(String.valueOf(item.getPrice()));
            
            newFoodItem.appendChild(id).appendChild(name).appendChild(description).appendChild(category).appendChild(price);
            newFoodItem.setAttribute("country", item.getCountry());
            
            dataTag.appendChild(newFoodItem);
            
            
            // write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(dataDir);
		transformer.transform(source, result);
        } catch (Exception ex) {
            String message  = ex.getMessage();
        }
    }
}
