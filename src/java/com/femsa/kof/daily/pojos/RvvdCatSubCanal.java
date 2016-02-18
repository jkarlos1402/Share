package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Mapea la tabla RVVD_CAT_SUB_CANAL para el manejo de persistencia
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "RVVD_CAT_SUB_CANAL")
public class RvvdCatSubCanal implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_CAT_SUB_CANAL")
    @SequenceGenerator(name = "RVVD_SEQ_CAT_SUB_CANAL", sequenceName = "RVVD_SEQ_CAT_SUB_CANAL", allocationSize = 1)
    @Column(name = "ID_SUB_CANAL")
    private BigDecimal idSubCanal;
    
    @Column(name = "SUB_CANAL_R")
    private String subCanalR;
   
    @Column(name = "SUB_CANAL_EN")
    private String subCanalEn;
    
    @JoinColumn(name = "ID_CANAL", referencedColumnName = "ID_CANAL")
    @ManyToOne(fetch = FetchType.EAGER)    
    private RvvdCatCanal canal;
    
    @Column(name = "STATUS")
    private boolean status;

    public BigDecimal getIdSubCanal() {
        return idSubCanal;
    }

    public void setIdSubCanal(BigDecimal idSubCanal) {
        this.idSubCanal = idSubCanal;
    }

    public String getSubCanalR() {
        return subCanalR;
    }

    public void setSubCanalR(String subCanalR) {
        this.subCanalR = subCanalR.toUpperCase();
    }

    public String getSubCanalEn() {
        return subCanalEn;
    }

    public void setSubCanalEn(String subCanalEn) {
        this.subCanalEn = subCanalEn.toUpperCase();
    }

    public RvvdCatCanal getCanal() {
        return canal;
    }

    public void setCanal(RvvdCatCanal canal) {
        this.canal = canal;
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
        hash += (idSubCanal != null ? idSubCanal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdCatSubCanal)) {
            return false;
        }
        RvvdCatSubCanal other = (RvvdCatSubCanal) object;
        if ((this.idSubCanal == null && other.idSubCanal != null) || (this.idSubCanal != null && !this.idSubCanal.equals(other.idSubCanal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.daily.pojos.RvvdCatSubCanal[ idSubCanal=" + idSubCanal + " ]";
    }

}
