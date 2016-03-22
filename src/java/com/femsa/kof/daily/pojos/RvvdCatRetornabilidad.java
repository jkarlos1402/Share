/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "RVVD_CAT_RETORNABILIDAD")
@NamedQueries({
    @NamedQuery(name = "RvvdCatRetornabilidad.findAll", query = "SELECT r FROM RvvdCatRetornabilidad r")})
public class RvvdCatRetornabilidad implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_CAT_RETORNABILIDAD")
    @SequenceGenerator(name = "RVVD_SEQ_CAT_RETORNABILIDAD", sequenceName = "RVVD_SEQ_CAT_RETORNABILIDAD", allocationSize = 1)
    @Column(name = "ID_RETORNABILIDAD")
    private Integer idRetornabilidad;
    @Column(name = "RETORNABILIDAD_R")
    private String retornabilidadR;
    @Column(name = "STATUS")
    private boolean status;

    public Integer getIdRetornabilidad() {
        return idRetornabilidad;
    }

    public void setIdRetornabilidad(Integer idRetornabilidad) {
        this.idRetornabilidad = idRetornabilidad;
    }

    public String getRetornabilidadR() {
        return retornabilidadR;
    }

    public void setRetornabilidadR(String retornabilidadR) {
        this.retornabilidadR = retornabilidadR != null ? retornabilidadR.toUpperCase() : retornabilidadR;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRetornabilidad != null ? idRetornabilidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdCatRetornabilidad)) {
            return false;
        }
        RvvdCatRetornabilidad other = (RvvdCatRetornabilidad) object;
        if ((this.idRetornabilidad == null && other.idRetornabilidad != null) || (this.idRetornabilidad != null && !this.idRetornabilidad.equals(other.idRetornabilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return retornabilidadR;
    }

}
