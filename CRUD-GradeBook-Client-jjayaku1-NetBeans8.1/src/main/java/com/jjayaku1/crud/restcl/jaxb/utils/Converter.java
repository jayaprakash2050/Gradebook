/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jjayaku1.crud.restcl.jaxb.utils;

import com.jjayaku1.crud.jaxb.model.GradeBookEntry;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author jjayaku1
 */
public class Converter {

    private static final Logger LOG = LoggerFactory.getLogger(Converter.class);

    public static Object convertFromXmlToObject(Object xmlString, Class... type) throws JAXBException {
        LOG.info("Converting from XML to an Object");
        LOG.debug("Object xmlString = {}", xmlString);
        LOG.debug("Class... type = {}", (Object) type);

        Object result;

        StringReader sr = null;

        if (xmlString instanceof String) {
            sr = new StringReader((String) xmlString);
        }

        JAXBContext context = JAXBContext.newInstance(type);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        result = unmarshaller.unmarshal(sr);

        return result;
    }

    public static String convertFromObjectToXml(Object source, Class... type) {
        LOG.info("Converting from and Object to XML");
        LOG.debug("Object source = {}", source);
        LOG.debug("Class... type = {}", (Object) type);

        String result;
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(type);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(source, sw);
            result = sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static String buildGradeBookEntry(Object source, Class... type) {
        String result = "";
        try {
            JAXBContext jc = JAXBContext.newInstance(type);
            StringWriter writerTo = new StringWriter();
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(source, writerTo);

            result = writerTo.toString();
            return result;
        } catch (Exception ex) {
            result = "<InvalidMessage />";
            return result;
        }
    }

    public static List<GradeBookEntry> ReadXML(String xmlString) {
        List<GradeBookEntry> listOfItems = new ArrayList<GradeBookEntry>();
        try {

            //File dataDir = new File(classLoader.getResource("FoodItemData(1).xml").getFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlString));
            //Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(xmlString.getBytes("utf-8"))));
            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("gradeBookEntry");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    GradeBookEntry entryItem = new GradeBookEntry();
                    Element eElement = (Element) nNode;

                    String itemName = eElement.getElementsByTagName("itemName").item(0).getTextContent();

                    long studentId = Long.parseLong(eElement.getAttribute("studentId"));

                    double grade = Double.parseDouble(eElement.getElementsByTagName("grade").item(0).getTextContent());

                    String feedBack = eElement.getElementsByTagName("feedBack").item(0).getTextContent();

                    entryItem.setGrade(grade);
                    entryItem.setFeedBack(feedBack);
                    entryItem.setItemName(itemName);
                    entryItem.setStudentId(studentId);

                    listOfItems.add(entryItem);

                }
            }
            //System.out.println(lstFoodItems.size());
            //System.out.println(lastIds);
        } catch (Exception e) {
            System.out.println("Exception occured");
            System.out.println(e.getMessage());
        }
        return listOfItems;
    }
}
