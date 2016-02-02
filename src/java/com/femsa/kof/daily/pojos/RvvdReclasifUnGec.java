package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "RVVD_RECLASIF_UN_GEC")
public class RvvdReclasifUnGec implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "PAIS")
    private String pais;
    
    @Column(name = "GEC")
    private String gec;
    
    @Column(name = "GEC_R")
    private String gecR;
    
    @Column(name = "GEC_EN")
    private String gecEn;
    
    @Column(name = "UNIDAD_NEGOCIO")
    private String unidadNegocio;
    
    @Column(name = "UNIDAD_NEGOCIO_R")
    private String unidadNegocioR;
    
    @Column(name = "UNIDAD_NEGOCIO_EN")
    private String unidadNegocioEn;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RECLASIF_UN_GEC")
    private BigDecimal idReclasifUnGec;

    /**
     *
     */
    public RvvdReclasifUnGec() {
    }

    /**
     *
     * @param idReclasifUnGec
     */
    public RvvdReclasifUnGec(BigDecimal idReclasifUnGec) {
        this.idReclasifUnGec = idReclasifUnGec;
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
    public String getGec() {
        return gec;
    }

    /**
     *
     * @param gec
     */
    public void setGec(String gec) {
        this.gec = gec;
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
        this.gecR = gecR;
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
        this.gecEn = gecEn;
    }

    /**
     *
     * @return
     */
    public String getUnidadNegocio() {
        return unidadNegocio;
    }

    /**
     *
     * @param unidadNegocio
     */
    public void setUnidadNegocio(String unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    /**
     *
     * @return
     */
    public String getUnidadNegocioR() {
        return unidadNegocioR;
    }

    /**
     *
     * @param unidadNegocioR
     */
    public void setUnidadNegocioR(String unidadNegocioR) {
        this.unidadNegocioR = unidadNegocioR;
    }

    /**
     *
     * @return
     */
    public String getUnidadNegocioEn() {
        return unidadNegocioEn;
    }

    /**
     *
     * @param unidadNegocioEn
     */
    public void setUnidadNegocioEn(String unidadNegocioEn) {
        this.unidadNegocioEn = unidadNegocioEn;
    }

    /**
     *
     * @return
     */
    public BigDecimal getIdReclasifUnGec() {
        return idReclasifUnGec;
    }

    /**
     *
     * @param idReclasifUnGec
     */
    public void setIdReclasifUnGec(BigDecimal idReclasifUnGec) {
        this.idReclasifUnGec = idReclasifUnGec;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReclasifUnGec != null ? idReclasifUnGec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdReclasifUnGec)) {
            return false;
        }
        RvvdReclasifUnGec other = (RvvdReclasifUnGec) object;
        if ((this.idReclasifUnGec == null && other.idReclasifUnGec != null) || (this.idReclasifUnGec != null && !this.idReclasifUnGec.equals(other.idReclasifUnGec))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return gecR;
    }
    
}
