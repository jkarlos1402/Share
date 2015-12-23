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
@Table(name = "RVVD_CAT_MARCA")
public class RvvdCatMarca implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_CAT_MARCA")
    @SequenceGenerator(name = "RVVD_SEQ_CAT_MARCA", sequenceName = "RVVD_SEQ_CAT_MARCA", allocationSize = 1)
    @Column(name = "ID_MARCA")
    private Integer idMarca;
    
    @Column(name = "MARCA_R")
    private String marcaR;
    
    @Column(name = "MARCA_EN")
    private String marcaEn;
    
    @Column(name = "STATUS")
    private boolean status;

    public RvvdCatMarca() {
    }

    public RvvdCatMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public String getMarcaR() {
        return marcaR;
    }

    public void setMarcaR(String marcaR) {
        this.marcaR = marcaR.toUpperCase();
    }

    public String getMarcaEn() {
        return marcaEn;
    }

    public void setMarcaEn(String marcaEn) {
        this.marcaEn = marcaEn.toUpperCase();
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
        hash += (idMarca != null ? idMarca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdCatMarca)) {
            return false;
        }
        RvvdCatMarca other = (RvvdCatMarca) object;
        if ((this.idMarca == null && other.idMarca != null) || (this.idMarca != null && !this.idMarca.equals(other.idMarca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return marcaR;
    }
    
}
