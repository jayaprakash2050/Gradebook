/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jjayaku1.crud.jaxb.model;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author jjayaku1
 */
@XmlRootElement
@XmlType(propOrder={
    "itemId",
    "itemName",
    "itemWeight"})
public class GradeItem {
    private int itemId;
    private String itemName;
    private double itemWeight;

    public GradeItem() {
    }

    public GradeItem(int itemId, String itemName, double itemWeight) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemWeight = itemWeight;
    }

    public int getItemId() {
        return itemId;
    }

    @XmlAttribute
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    @XmlElement
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemWeight() {
        return itemWeight;
    }

    @XmlElement
    public void setItemWeight(double itemWeight) {
        this.itemWeight = itemWeight;
    }
    
    @Override
    public String toString() {
        return "Grade Item{" + "itemName=" + itemName + ", itemWeight=" + itemWeight + ", itemId=" + itemId + '}';
    }
    
}
