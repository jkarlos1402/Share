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
@Table(name = "RVVD_RECLASIF_DIAS_OP_TMP")
public class RvvdReclasifDiasOpTmp implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "PAIS")
    private String pais;
    
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @Column(name = "FECHA_R")
    @Temporal(TemporalType.DATE)
    private Date fechaR;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_RECLASIF_DIAS_OP_TMP")
    @SequenceGenerator(name = "RVVD_SEQ_RECLASIF_DIAS_OP_TMP", sequenceName = "RVVD_SEQ_RECLASIF_DIAS_OP_TMP", allocationSize = 1)
    @Column(name = "ID_RECLASIF_DIAS_OP")
    private BigDecimal idReclasifDiasOp;

    /**
     *
     */
    public RvvdReclasifDiasOpTmp() {
    }

    /**
     *
     * @param idReclasifDiasOp
     */
    public RvvdReclasifDiasOpTmp(BigDecimal idReclasifDiasOp) {
        this.idReclasifDiasOp = idReclasifDiasOp;
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
    public Date getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @return
     */
    public Date getFechaR() {
        return fechaR;
    }

    /**
     *
     * @param fechaR
     */
    public void setFechaR(Date fechaR) {        
        this.fechaR = fechaR;
    }

    /**
     *
     * @return
     */
    public BigDecimal getIdReclasifDiasOp() {
        return idReclasifDiasOp;
    }

    /**
     *
     * @param idReclasifDiasOp
     */
    public void setIdReclasifDiasOp(BigDecimal idReclasifDiasOp) {
        this.idReclasifDiasOp = idReclasifDiasOp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReclasifDiasOp != null ? idReclasifDiasOp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdReclasifDiasOpTmp)) {
            return false;
        }
        RvvdReclasifDiasOpTmp other = (RvvdReclasifDiasOpTmp) object;
        if ((this.idReclasifDiasOp == null && other.idReclasifDiasOp != null) || (this.idReclasifDiasOp != null && !this.idReclasifDiasOp.equals(other.idReclasifDiasOp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.daily.pojos.RvvdReclasifDiasOp[ idReclasifDiasOp=" + idReclasifDiasOp + " ]";
    }
    
}
