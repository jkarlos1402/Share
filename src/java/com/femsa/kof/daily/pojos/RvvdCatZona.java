package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Mapea la tabla RVVD_CAT_ZONA
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "RVVD_CAT_ZONA")
public class RvvdCatZona implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_CAT_ZONA")
    @SequenceGenerator(name = "RVVD_SEQ_CAT_ZONA", sequenceName = "RVVD_SEQ_CAT_ZONA", allocationSize = 1)
    @Column(name = "ID_ZONA")
    private Integer idZona;
    
    @Column(name = "ZONA_R")
    private String zonaR;
    
    @Column(name = "ZONA_EN")
    private String zonaEn;
    
    @Column(name = "STATUS")
    private boolean status;    

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public String getZonaR() {
        return zonaR;
    }

    public void setZonaR(String zonaR) {
        this.zonaR = zonaR != null ? zonaR.toUpperCase() : null;
    }

    public String getZonaEn() {
        return zonaEn;
    }

    public void setZonaEn(String zonaEn) {
        this.zonaEn = zonaEn != null ? zonaEn.toUpperCase() : null;
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
        hash += (idZona != null ? idZona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdCatZona)) {
            return false;
        }
        RvvdCatZona other = (RvvdCatZona) object;
        if ((this.idZona == null && other.idZona != null) || (this.idZona != null && !this.idZona.equals(other.idZona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return zonaR;
    }

}
