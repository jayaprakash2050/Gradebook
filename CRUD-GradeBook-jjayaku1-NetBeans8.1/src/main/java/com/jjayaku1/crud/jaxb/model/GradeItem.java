/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jjayaku1.crud.jaxb.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 *
 * @author hp pc
 */
@XmlRootElement
@XmlType(propOrder={
    "itemId",
    "itemName",
    "pointAllocated"})
public class GradeItem {
    private static final Logger LOG = LoggerFactory.getLogger(GradeItem.class);
    private int itemId;
    private String itemName;
    private int pointAllocated;

    public GradeItem() {
    }
    
    
    public GradeItem(int itemId, String itemName, int pointAllocated) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.pointAllocated = pointAllocated;
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

    public int getPointAllocated() {
        return pointAllocated;
    }
    
    @XmlElement
    public void setPointAllocated(int pointAllocated) {
        this.pointAllocated = pointAllocated;
    }
}
