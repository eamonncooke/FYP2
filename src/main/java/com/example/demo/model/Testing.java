/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author cooke
 */
@Entity
@Table(name = "testing")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Testing.findAll", query = "SELECT t FROM Testing t"),
    @NamedQuery(name = "Testing.findByTestId", query = "SELECT t FROM Testing t WHERE t.testId = :testId"),
    @NamedQuery(name = "Testing.findByTime", query = "SELECT t FROM Testing t WHERE t.time = :time"),
    @NamedQuery(name = "Testing.findByDate", query = "SELECT t FROM Testing t WHERE t.date = :date"),
    @NamedQuery(name = "Testing.findLastTestNumber", query = "SELECT t FROM Testing t ORDER BY t.testId DESC")})
public class Testing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "testId")
    private Integer testId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time")
    private int time;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "playerID", referencedColumnName = "playerId")
    @ManyToOne(optional = false)
    private Player playerID;
    
    
    private String trainingAttributeJSON;
 
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> trainingAttributes;

    public Testing() {
    }

    public Testing(Integer testId) {
        this.testId = testId;
    }

    public Testing(Integer testId, int time, Date date) {
        this.testId = testId;
        this.time = time;
        this.date = date;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Player getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Player playerID) {
        this.playerID = playerID;
    }

    public String getTrainingAttributeJSON() {
        return trainingAttributeJSON;
    }

    public void setTrainingAttributeJSON(String trainingAttributeJSON) {
        this.trainingAttributeJSON = trainingAttributeJSON;
    }

    public Map<String, Object> getTrainingAttributes() {
        return trainingAttributes;
    }

    public void setTrainingAttributes(Map<String, Object> trainingAttributes) {
        this.trainingAttributes = trainingAttributes;
    }
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void serializeCustomerAttributes() throws JsonProcessingException {
        this.trainingAttributeJSON = objectMapper.writeValueAsString(trainingAttributes);
    }
    
    public void deserializeCustomerAttributes() throws IOException {
        this.trainingAttributes = objectMapper.readValue(trainingAttributeJSON, Map.class);
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (testId != null ? testId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Testing)) {
            return false;
        }
        Testing other = (Testing) object;
        if ((this.testId == null && other.testId != null) || (this.testId != null && !this.testId.equals(other.testId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Testing[ testId=" + testId + " ]";
    }
    
}
