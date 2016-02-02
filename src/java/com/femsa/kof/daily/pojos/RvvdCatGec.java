package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "RVVD_CAT_GEC")
public class RvvdCatGec implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_CAT_GEC")
    @SequenceGenerator(name = "RVVD_SEQ_CAT_GEC", sequenceName = "RVVD_SEQ_CAT_GEC", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID_GEC")
    private Integer idGec;
    
    @Column(name = "GEC_R")
    private String gecR;
    
    @Column(name = "GEC_EN")
    private String gecEn;
    
    @Column(name = "STATUS")
    private boolean status;

    /**
     *
     */
    public RvvdCatGec() {
    }

    /**
     *
     * @param idGec
     */
    public RvvdCatGec(Integer idGec) {
        this.idGec = idGec;
    }

    /**
     *
     * @return
     */
    public Integer getIdGec() {
        return idGec;
    }

    /**
     *
     * @param idGec
     */
    public void setIdGec(Integer idGec) {
        this.idGec = idGec;
    }

    /**
     *
     * @return
     */
    public String getGecR() {
        return gecR;
    }

    /**
     *
     * @param gecR
     */
    public void setGecR(String gecR) {
        this.gecR = gecR.toUpperCase();
    }

    /**
     *
     * @return
     */
    public String getGecEn() {
        return gecEn;
    }

    /**
     *
     * @param gecEn
     */
    public void setGecEn(String gecEn) {
        this.gecEn = gecEn.toUpperCase();
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
        hash += (idGec != null ? idGec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdCatGec)) {
            return false;
        }
        RvvdCatGec other = (RvvdCatGec) object;
        if ((this.idGec == null && other.idGec != null) || (this.idGec != null && !this.idGec.equals(other.idGec))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return gecR;
    }
    
}
