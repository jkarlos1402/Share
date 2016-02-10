package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "RVVD_445_PH_TMP")
public class Rvvd445PhTmp implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "PAIS")
    private String pais;
    
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @Column(name = "FECHA_REASIGNACION")
    private BigInteger fechaReasignacion;
    
    @Column(name = "FECHA_AA")
    @Temporal(TemporalType.DATE)
    private Date fechaAa;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_445_PH_TMP")
    @SequenceGenerator(name = "RVVD_SEQ_445_PH_TMP", sequenceName = "RVVD_SEQ_445_PH_TMP", allocationSize = 1)
    @Column(name = "ID_445_PH")
    private BigDecimal id445Ph;    

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
    public BigInteger getFechaReasignacion() {
        return fechaReasignacion;
    }

    /**
     *
     * @param fechaReasignacion
     */
    public void setFechaReasignacion(BigInteger fechaReasignacion) {
        this.fechaReasignacion = fechaReasignacion;
    }

    /**
     *
     * @return
     */
    public Date getFechaAa() {
        return fechaAa;
    }

    /**
     *
     * @param fechaAa
     */
    public void setFechaAa(Date fechaAa) {
        this.fechaAa = fechaAa;
    }

    /**
     *
     * @return
     */
    public BigDecimal getId445Ph() {
        return id445Ph;
    }

    /**
     *
     * @param id445Ph
     */
    public void setId445Ph(BigDecimal id445Ph) {
        this.id445Ph = id445Ph;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id445Ph != null ? id445Ph.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rvvd445PhTmp)) {
            return false;
        }
        Rvvd445PhTmp other = (Rvvd445PhTmp) object;
        if ((this.id445Ph == null && other.id445Ph != null) || (this.id445Ph != null && !this.id445Ph.equals(other.id445Ph))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.daily.pojos.Rvvd445Ph[ id445Ph=" + id445Ph + " ]";
    }
    
}
