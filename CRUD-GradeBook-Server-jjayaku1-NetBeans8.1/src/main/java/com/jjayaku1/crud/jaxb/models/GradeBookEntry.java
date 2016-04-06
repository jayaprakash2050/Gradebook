/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jjayaku1.crud.jaxb.models;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author jjayaku1
 */
@XmlRootElement
@XmlType(propOrder={
    "studentId",
    "itemId",
    "itemName",
    "grade"})
public class GradeBookEntry {
    private long studentId;
    private int itemId;
    private String itemName;
    private double grade;

    public GradeBookEntry() {
    }

    public GradeBookEntry(long studentId, int itemId, String itemName, double grade) {
        this.studentId = studentId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.grade = grade;
    }

    public long getStudentId() {
        return studentId;
    }
    @XmlAttribute
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getItemId() {
        return itemId;
    }

    @XmlElement
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

    public double getGrade() {
        return grade;
    }

    @XmlElement
    public void setGrade(double grade) {
        this.grade = grade;
    }
    
    @Override
    public String toString(){
        return "Grade book entry for "+this.studentId+", is, grade item name = "+this.itemName+", grade = "+ this.grade;
    }
    
    
    
    
}
