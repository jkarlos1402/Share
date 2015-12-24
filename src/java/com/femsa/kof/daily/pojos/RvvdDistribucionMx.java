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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "RVVD_DISTRIBUCION_MX")
public class RvvdDistribucionMx implements Serializable {

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
    private BigInteger porcentaje;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_DISTRIBUCION_MX")
    @SequenceGenerator(name = "RVVD_SEQ_DISTRIBUCION_MX", sequenceName = "RVVD_SEQ_DISTRIBUCION_MX", allocationSize = 1)
    @Column(name = "ID_DISTRIBUCION_MX")
    private BigDecimal idDistribucionMx;

    public RvvdDistribucionMx() {
    }

    public RvvdDistribucionMx(BigDecimal idDistribucionMx) {
        this.idDistribucionMx = idDistribucionMx;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Date getFechaOrigen() {
        return fechaOrigen;
    }

    public void setFechaOrigen(Date fechaOrigen) {
        this.fechaOrigen = fechaOrigen;
    }

    public Date getFechaDestino() {
        return fechaDestino;
    }

    public void setFechaDestino(Date fechaDestino) {
        this.fechaDestino = fechaDestino;
    }

    public BigInteger getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigInteger porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getIdDistribucionMx() {
        return idDistribucionMx;
    }

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
        if (!(object instanceof RvvdDistribucionMx)) {
            return false;
        }
        RvvdDistribucionMx other = (RvvdDistribucionMx) object;
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
