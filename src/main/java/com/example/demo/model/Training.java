/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cooke
 */
@Entity
@Table(name = "training")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Training.findAll", query = "SELECT t FROM Training t"),
    @NamedQuery(name = "Training.findByTrainingId", query = "SELECT t FROM Training t WHERE t.trainingId = :trainingId"),
    @NamedQuery(name = "Training.findByGeoLocation", query = "SELECT t FROM Training t WHERE t.geoLocation = :geoLocation"),
    @NamedQuery(name = "Training.findByDate", query = "SELECT t FROM Training t WHERE t.date = :date")})
public class Training implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trainingId")
    private Integer trainingId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "geoLocation")
    private String geoLocation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "playerID", referencedColumnName = "playerId")
    @ManyToOne(optional = false)
    private Player playerID;

    public Training() {
    }

    public Training(Integer trainingId) {
        this.trainingId = trainingId;
    }

    public Training(Integer trainingId, String geoLocation, Date date) {
        this.trainingId = trainingId;
        this.geoLocation = geoLocation;
        this.date = date;
    }

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trainingId != null ? trainingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Training)) {
            return false;
        }
        Training other = (Training) object;
        if ((this.trainingId == null && other.trainingId != null) || (this.trainingId != null && !this.trainingId.equals(other.trainingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Training[ trainingId=" + trainingId + " ]";
    }
    
}
