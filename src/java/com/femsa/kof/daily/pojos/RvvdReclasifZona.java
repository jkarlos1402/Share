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
 * Mapea la tabla RVVD_RECLASIF_ZONA
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "RVVD_RECLASIF_ZONA")
public class RvvdReclasifZona implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_RECLASIF_ZONA")
    @SequenceGenerator(name = "RVVD_SEQ_RECLASIF_ZONA", sequenceName = "RVVD_SEQ_RECLASIF_ZONA", allocationSize = 1)
    @Column(name = "ID_RECLASIF_ZONA")
    private Integer idReclasifZona;

    @Column(name = "PAIS")
    private String pais;

    @Column(name = "ZONA")
    private String zona;

    @Column(name = "ZONA_R")
    private String zonaR;

    @Column(name = "ZONA_EN")
    private String zonaEn;

    public Integer getIdReclasifZona() {
        return idReclasifZona;
    }

    public void setIdReclasifZona(Integer idReclasifZona) {
        this.idReclasifZona = idReclasifZona;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getZonaR() {
        return zonaR;
    }

    public void setZonaR(String zonaR) {
        this.zonaR = zonaR;
    }

    public String getZonaEn() {
        return zonaEn;
    }

    public void setZonaEn(String zonaEn) {
        this.zonaEn = zonaEn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReclasifZona != null ? idReclasifZona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdReclasifZona)) {
            return false;
        }
        RvvdReclasifZona other = (RvvdReclasifZona) object;
        if ((this.idReclasifZona == null && other.idReclasifZona != null) || (this.idReclasifZona != null && !this.idReclasifZona.equals(other.idReclasifZona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RvvdReclasifZona{" + "idReclasifZona=" + idReclasifZona + ", pais=" + pais + ", zona=" + zona + ", zonaR=" + zonaR + ", zonaEn=" + zonaEn + '}';
    }

}
