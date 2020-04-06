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
import javax.xml.bind.annotation.XmlRootElement;

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
    @Column(name = "test_id")
    private Integer testId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time")
    private int time;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    @ManyToOne(optional = false)
    private Player playerId;

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

    public Player getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Player playerId) {
        this.playerId = playerId;
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
