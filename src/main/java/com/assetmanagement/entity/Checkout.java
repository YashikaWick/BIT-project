/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assetmanagement.entity;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Acer
 */
@Entity
@Table(name = "checkout")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Checkout.findAll", query = "SELECT c FROM Checkout c")})
public class Checkout implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "checkoutno")
    private String checkoutno;
    @Basic(optional = false)
    @Column(name = "choutdate")
    private LocalDate choutdate;
    @Column(name = "duedate")
    private LocalDate duedate;
    @Lob
    @Column(name = "notes")
    @Pattern(regexp = "^.*$", message = "Invalid Note")
    private String notes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checkoutId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Assetcheckout> assetcheckoutList;
    @JoinColumn(name = "assetstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Assetstatus assetstatusId;
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Branch branchId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "newholder_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee newholderId;


    public Checkout() {
    }

    public Checkout(Integer id) {
        this.id = id;
    }

    public Checkout(Integer id, String checkoutno) {
        this.id = id;
        this.checkoutno = checkoutno;
    }

    public Checkout(Integer id, String checkoutno, LocalDate choutdate) {
        this.id = id;
        this.checkoutno = checkoutno;
        this.choutdate = choutdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheckoutno() {
        return checkoutno;
    }

    public void setCheckoutno(String checkoutno) {
        this.checkoutno = checkoutno;
    }

    public LocalDate getChoutdate() {
        return choutdate;
    }

    public void setChoutdate(LocalDate choutdate) {
        this.choutdate = choutdate;
    }

    public LocalDate getDuedate() {
        return duedate;
    }

    public void setDuedate(LocalDate duedate) {
        this.duedate = duedate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @XmlTransient
    public List<Assetcheckout> getAssetcheckoutList() {
        return assetcheckoutList;
    }

    public void setAssetcheckoutList(List<Assetcheckout> assetcheckoutList) {
        this.assetcheckoutList = assetcheckoutList;
    }

    public Assetstatus getAssetstatusId() {
        return assetstatusId;
    }

    public void setAssetstatusId(Assetstatus assetstatusId) {
        this.assetstatusId = assetstatusId;
    }

    public Branch getBranchId() {
        return branchId;
    }

    public void setBranchId(Branch branchId) {
        this.branchId = branchId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getNewholderId() {
        return newholderId;
    }

    public void setNewholderId(Employee newholderId) {
        this.newholderId = newholderId;
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
        if (!(object instanceof Checkout)) {
            return false;
        }
        Checkout other = (Checkout) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.assetmanagement.entity.Checkout[ id=" + id + " ]";
    }



}
