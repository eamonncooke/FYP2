/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cooke
 */
@Entity
@Table(name = "coach")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coach.findAll", query = "SELECT c FROM Coach c"),
    @NamedQuery(name = "Coach.findByCoachId", query = "SELECT c FROM Coach c WHERE c.coachId = :coachId"),
    @NamedQuery(name = "Coach.findByRole", query = "SELECT c FROM Coach c WHERE c.role = :role")})
public class Coach implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "coach_id")
    private Integer coachId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "role")
    private String role;
    @JoinColumn(name = "auth_user_id", referencedColumnName = "auth_user_id")
    @OneToOne(optional = false)
    private AuthUser authUserId;

    public Coach() {
    }

    public Coach(Integer coachId) {
        this.coachId = coachId;
    }

    public Coach(Integer coachId, String role) {
        this.coachId = coachId;
        this.role = role;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AuthUser getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(AuthUser authUserId) {
        this.authUserId = authUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coachId != null ? coachId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coach)) {
            return false;
        }
        Coach other = (Coach) object;
        if ((this.coachId == null && other.coachId != null) || (this.coachId != null && !this.coachId.equals(other.coachId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Coach[ coachId=" + coachId + " ]";
    }
    
}
