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
@Table(name = "RVVD_RECLASIF_CANAL")
public class RvvdReclasifCanal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "PAIS")
    private String pais;
    
    @Column(name = "CANAL")
    private String canal;
    
    @Column(name = "CANAL_R")
    private String canalR;
    
    @Column(name = "CANAL_EN")
    private String canalEn;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_RECLASIF_CANAL")
    @SequenceGenerator(name = "RVVD_SEQ_RECLASIF_CANAL", sequenceName = "RVVD_SEQ_RECLASIF_CANAL", allocationSize = 1)
    @Column(name = "ID_RECLASIF_CANAL")
    private BigDecimal idReclasifCanal;

    /**
     *
     */
    public RvvdReclasifCanal() {
    }

    /**
     *
     * @param idReclasifCanal
     */
    public RvvdReclasifCanal(BigDecimal idReclasifCanal) {
        this.idReclasifCanal = idReclasifCanal;
    }

    /**
     *
     * @return
     */
    public String getPais() {
        return pais;
    }

    /**
     *
     * @param pais
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     *
     * @return
     */
    public String getCanal() {
        return canal;
    }

    /**
     *
     * @param canal
     */
    public void setCanal(String canal) {
        this.canal = canal;
    }

    /**
     *
     * @return
     */
    public String getCanalR() {
        return canalR;
    }

    /**
     *
     * @param canalR
     */
    public void setCanalR(String canalR) {
        this.canalR = canalR;
    }

    /**
     *
     * @return
     */
    public String getCanalEn() {
        return canalEn;
    }

    /**
     *
     * @param canalEn
     */
    public void setCanalEn(String canalEn) {
        this.canalEn = canalEn;
    }

    /**
     *
     * @return
     */
    public BigDecimal getIdReclasifCanal() {
        return idReclasifCanal;
    }

    /**
     *
     * @param idReclasifCanal
     */
    public void setIdReclasifCanal(BigDecimal idReclasifCanal) {
        this.idReclasifCanal = idReclasifCanal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReclasifCanal != null ? idReclasifCanal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdReclasifCanal)) {
            return false;
        }
        RvvdReclasifCanal other = (RvvdReclasifCanal) object;
        if ((this.idReclasifCanal == null && other.idReclasifCanal != null) || (this.idReclasifCanal != null && !this.idReclasifCanal.equals(other.idReclasifCanal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.daily.pojos.RvvdReclasifCanal[ idReclasifCanal=" + idReclasifCanal + " ]";
    }
    
}
