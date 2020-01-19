/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assetmanagement.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Acer
 */
@Entity
@Table(name = "reserve")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserve.findAll", query = "SELECT r FROM Reserve r")})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reserve implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "resfrom")
    private LocalDate resfrom;
    @Basic(optional = false)
    @Column(name = "resupto")
    private LocalDate resupto;
    @Lob
    @Column(name = "note")
    @Pattern(regexp = "^.*$", message = "Invalid Note")
    private String note;
    @JoinColumn(name = "asset_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Asset assetId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;

    public Reserve() {
    }

    public Reserve(Integer id) {
        this.id = id;
    }

    public Reserve(Integer id, LocalDate resfrom, LocalDate resupto) {
        this.id = id;
        this.resfrom = resfrom;
        this.resupto = resupto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getResfrom() {
        return resfrom;
    }

    public void setResfrom(LocalDate resfrom) {
        this.resfrom = resfrom;
    }

    public LocalDate getResupto() {
        return resupto;
    }

    public void setResupto(LocalDate resupto) {
        this.resupto = resupto;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Asset getAssetId() {
        return assetId;
    }

    public void setAssetId(Asset assetId) {
        this.assetId = assetId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserve)) {
            return false;
        }
        Reserve other = (Reserve) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.assetmanagement.entity.Reserve[ id=" + id + " ]";
    }
    
}
