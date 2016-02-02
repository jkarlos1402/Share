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
@Table(name = "RVVD_CAT_CONTENIDO_CALORICO")
public class RvvdCatContenidoCalorico implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_CAT_CONT_CALORICO")
    @SequenceGenerator(name = "RVVD_SEQ_CAT_CONT_CALORICO", sequenceName = "RVVD_SEQ_CAT_CONT_CALORICO", allocationSize = 1)
    @Column(name = "ID_CONTENIDO_CALORICO")
    private Integer idContenidoCalorico;
    
    @Column(name = "CONTENIDO_CALORICO_R")
    private String contenidoCaloricoR;
    
    @Column(name = "CONTENIDO_CALORICO_EN")
    private String contenidoCaloricoEn;
    
    @Column(name = "STATUS")
    private boolean status;

    /**
     *
     */
    public RvvdCatContenidoCalorico() {
    }

    /**
     *
     * @param idContenidoCalorico
     */
    public RvvdCatContenidoCalorico(Integer idContenidoCalorico) {
        this.idContenidoCalorico = idContenidoCalorico;
    }

    /**
     *
     * @return
     */
    public Integer getIdContenidoCalorico() {
        return idContenidoCalorico;
    }

    /**
     *
     * @param idContenidoCalorico
     */
    public void setIdContenidoCalorico(Integer idContenidoCalorico) {
        this.idContenidoCalorico = idContenidoCalorico;
    }

    /**
     *
     * @return
     */
    public String getContenidoCaloricoR() {
        return contenidoCaloricoR;
    }

    /**
     *
     * @param contenidoCaloricoR
     */
    public void setContenidoCaloricoR(String contenidoCaloricoR) {
        this.contenidoCaloricoR = contenidoCaloricoR.toUpperCase();
    }

    /**
     *
     * @return
     */
    public String getContenidoCaloricoEn() {
        return contenidoCaloricoEn;
    }

    /**
     *
     * @param contenidoCaloricoEn
     */
    public void setContenidoCaloricoEn(String contenidoCaloricoEn) {
        this.contenidoCaloricoEn = contenidoCaloricoEn.toUpperCase();
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
        hash += (idContenidoCalorico != null ? idContenidoCalorico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdCatContenidoCalorico)) {
            return false;
        }
        RvvdCatContenidoCalorico other = (RvvdCatContenidoCalorico) object;
        if ((this.idContenidoCalorico == null && other.idContenidoCalorico != null) || (this.idContenidoCalorico != null && !this.idContenidoCalorico.equals(other.idContenidoCalorico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return contenidoCaloricoR;
    }
    
}
