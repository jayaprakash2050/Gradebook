/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jjayaku1.crud.jaxb.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author hp pc
 */
@XmlRootElement
@XmlType(propOrder={
    "studentId",
    "gradeItemName",
    "appealComments",
    "instructorComments"
    })
public class GradeAppeal {
    private String appealComments;
    private long studentId;
    private String gradeItemName;
    private String instructorComments;
    
    public GradeAppeal() {
    }

    public GradeAppeal(String appealComments, long studentId, String gradeItemName, String instructorComments) {
        this.appealComments = appealComments;
        this.studentId = studentId;
        this.gradeItemName = gradeItemName;
        this.instructorComments = instructorComments;
        
    }

    public String getAppealComments() {
        return appealComments;
    }

    @XmlElement
    public void setAppealComments(String appealComments) {
        this.appealComments = appealComments;
    }

    public long getStudentId() {
        return studentId;
    }

    @XmlElement
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }
    
    public String getGradeItemName() {
        return gradeItemName;
    }

    @XmlElement
    public void setGradeItemName(String gradeItemName) {
        this.gradeItemName = gradeItemName;
    }
    
    public String getInstructorComments() {
        return instructorComments;
    }

    @XmlElement
    public void setInstructorComments(String instructorComments) {
        this.instructorComments = instructorComments;
    }
       
}
