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
import java.util.HashMap;

/**
 *
 * @author hp pc
 */
@XmlRootElement
@XmlType(propOrder={
    "studentId",
    "grades",
    })
public class GradeDetail {
    private int studentId;
    private HashMap<Integer, Double> grades;

    public GradeDetail() {
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    @XmlElement
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public HashMap<Integer, Double> getGrades() {
        return grades;
    }
    
    @XmlElement
    public void setGrades(int itemId, double grade) {
        this.grades.put(itemId, grade);
    }
    
    
    
}
