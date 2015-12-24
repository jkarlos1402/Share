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

@Entity
@Table(name = "RVVD_CAT_UNIDAD_NEGOCIO")
public class RvvdCatUnidadNegocio implements Serializable {

    private static final long serialVersionUID = 1L;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation    
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_UNIDAD_NEGOCIO")
    @SequenceGenerator(name = "RVVD_SEQ_UNIDAD_NEGOCIO", sequenceName = "RVVD_SEQ_UNIDAD_NEGOCIO", allocationSize = 1)
    @Column(name = "ID_UNIDAD_NEGOCIO")
    private Integer idUnidadNegocio;
    
    @Column(name = "UNIDAD_NEGOCIO_R")
    private String unidadNegocioR;
    
    @Column(name = "UNIDAD_NEGOCIO_EN")
    private String unidadNegocioEn;
    
    @Column(name = "STATUS")
    private boolean status;

    public RvvdCatUnidadNegocio() {
    }

    public RvvdCatUnidadNegocio(Integer idUnidadNegocio) {
        this.idUnidadNegocio = idUnidadNegocio;
    }

    public Integer getIdUnidadNegocio() {
        return idUnidadNegocio;
    }

    public void setIdUnidadNegocio(Integer idUnidadNegocio) {
        this.idUnidadNegocio = idUnidadNegocio;
    }

    public String getUnidadNegocioR() {
        return unidadNegocioR;
    }

    public void setUnidadNegocioR(String unidadNegocioR) {
        this.unidadNegocioR = unidadNegocioR.toUpperCase();
    }

    public String getUnidadNegocioEn() {
        return unidadNegocioEn;
    }

    public void setUnidadNegocioEn(String unidadNegocioEn) {
        this.unidadNegocioEn = unidadNegocioEn.toUpperCase();
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
        hash += (idUnidadNegocio != null ? idUnidadNegocio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdCatUnidadNegocio)) {
            return false;
        }
        RvvdCatUnidadNegocio other = (RvvdCatUnidadNegocio) object;
        if ((this.idUnidadNegocio == null && other.idUnidadNegocio != null) || (this.idUnidadNegocio != null && !this.idUnidadNegocio.equals(other.idUnidadNegocio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return unidadNegocioR;
    }

}
