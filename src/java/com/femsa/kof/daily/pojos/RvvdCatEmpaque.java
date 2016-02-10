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
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "RVVD_CAT_EMPAQUE")
public class RvvdCatEmpaque implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_CAT_EMPAQUE")
    @SequenceGenerator(name = "RVVD_SEQ_CAT_EMPAQUE", sequenceName = "RVVD_SEQ_CAT_EMPAQUE", allocationSize = 1)
    @Column(name = "ID_EMPAQUE")
    private Integer idEmpaque;

    @Column(name = "EMPAQUE_R")
    private String empaqueR;
    
    @Column(name = "EMPAQUE_EN")
    private String empaqueEn;
    
    @Column(name = "STATUS")
    private boolean status;    

    /**
     *
     * @return
     */
    public Integer getIdEmpaque() {
        return idEmpaque;
    }

    /**
     *
     * @param idEmpaque
     */
    public void setIdEmpaque(Integer idEmpaque) {
        this.idEmpaque = idEmpaque;
    }

    /**
     *
     * @return
     */
    public String getEmpaqueR() {
        return empaqueR;
    }

    /**
     *
     * @param empaqueR
     */
    public void setEmpaqueR(String empaqueR) {
        this.empaqueR = empaqueR.toUpperCase();
    }

    /**
     *
     * @return
     */
    public String getEmpaqueEn() {
        return empaqueEn;
    }

    /**
     *
     * @param empaqueEn
     */
    public void setEmpaqueEn(String empaqueEn) {
        this.empaqueEn = empaqueEn.toUpperCase();
    }

    /**
     *
     * @return
     */
    public boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpaque != null ? idEmpaque.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdCatEmpaque)) {
            return false;
        }
        RvvdCatEmpaque other = (RvvdCatEmpaque) object;
        if ((this.idEmpaque == null && other.idEmpaque != null) || (this.idEmpaque != null && !this.idEmpaque.equals(other.idEmpaque))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return empaqueR;
    }
    
}
