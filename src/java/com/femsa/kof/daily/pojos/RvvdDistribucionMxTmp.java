package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "RVVD_DISTRIBUCION_MX_TMP")
public class RvvdDistribucionMxTmp implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "PAIS")
    private String pais;
    
    @Column(name = "FECHA_ORIGEN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOrigen;
    
    @Column(name = "FECHA_DESTINO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDestino;
    
    @Column(name = "PORCENTAJE")
    private double porcentaje;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_DISTRIBUCION_MX")
    @SequenceGenerator(name = "RVVD_SEQ_DISTRIBUCION_MX", sequenceName = "RVVD_SEQ_DISTRIBUCION_MX", allocationSize = 1)
    @Column(name = "ID_DISTRIBUCION_MX")
    private BigDecimal idDistribucionMx;

    /**
     *
     */
    public RvvdDistribucionMxTmp() {
    }

    /**
     *
     * @param idDistribucionMx
     */
    public RvvdDistribucionMxTmp(BigDecimal idDistribucionMx) {
        this.idDistribucionMx = idDistribucionMx;
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
    public Date getFechaOrigen() {
        return fechaOrigen;
    }

    /**
     *
     * @param fechaOrigen
     */
    public void setFechaOrigen(Date fechaOrigen) {
        this.fechaOrigen = fechaOrigen;
    }

    /**
     *
     * @return
     */
    public Date getFechaDestino() {
        return fechaDestino;
    }

    /**
     *
     * @param fechaDestino
     */
    public void setFechaDestino(Date fechaDestino) {
        this.fechaDestino = fechaDestino;
    }

    /**
     *
     * @return
     */
    public double getPorcentaje() {
        return porcentaje;
    }

    /**
     *
     * @param porcentaje
     */
    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     *
     * @return
     */
    public BigDecimal getIdDistribucionMx() {
        return idDistribucionMx;
    }

    /**
     *
     * @param idDistribucionMx
     */
    public void setIdDistribucionMx(BigDecimal idDistribucionMx) {
        this.idDistribucionMx = idDistribucionMx;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDistribucionMx != null ? idDistribucionMx.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdDistribucionMxTmp)) {
            return false;
        }
        RvvdDistribucionMxTmp other = (RvvdDistribucionMxTmp) object;
        if ((this.idDistribucionMx == null && other.idDistribucionMx != null) || (this.idDistribucionMx != null && !this.idDistribucionMx.equals(other.idDistribucionMx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.daily.pojos.RvvdDistribucionMx[ idDistribucionMx=" + idDistribucionMx + " ]";
    }
    
}
