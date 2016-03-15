/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "RVVD_DISTRIBUCION_MX_TMP")
@NamedQueries({
    @NamedQuery(name = "RvvdDistribucionMxTmp.findAll", query = "SELECT r FROM RvvdDistribucionMxTmp r")})
public class RvvdDistribucionMxTmp implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_DISTRIBUCION_MX_TMP")
    @SequenceGenerator(name = "RVVD_SEQ_DISTRIBUCION_MX_TMP", sequenceName = "RVVD_SEQ_DISTRIBUCION_MX_TMP", allocationSize = 1)
    @Column(name = "ID_DISTRIBUCION_MX")
    private Integer idDistribucionMx;
    @Column(name = "PAIS")
    private String pais;
    @Column(name = "FECHA_ORIGEN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOrigen;
    @Column(name = "ID_TIEMPO_FECHA_ORIGEN")
    private Integer idTiempoFechaOrigen;
    @Column(name = "FECHA_DESTINO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDestino;
    @Column(name = "ID_TIEMPO_FECHA_DESTINO")
    private Integer idTiempoFechaDestino;
    @Column(name = "PORCENTAJE")
    private float porcentaje;

    public Integer getIdDistribucionMx() {
        return idDistribucionMx;
    }

    public void setIdDistribucionMx(Integer idDistribucionMx) {
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

    public Integer getIdTiempoFechaOrigen() {
        return idTiempoFechaOrigen;
    }

    public void setIdTiempoFechaOrigen(Integer idTiempoFechaOrigen) {
        this.idTiempoFechaOrigen = idTiempoFechaOrigen;
    }

    public Date getFechaDestino() {
        return fechaDestino;
    }

    public void setFechaDestino(Date fechaDestino) {
        this.fechaDestino = fechaDestino;
    }

    public Integer getIdTiempoFechaDestino() {
        return idTiempoFechaDestino;
    }

    public void setIdTiempoFechaDestino(Integer idTiempoFechaDestino) {
        this.idTiempoFechaDestino = idTiempoFechaDestino;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
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
        return "com.femsa.kof.daily.pojos.RvvdDistribucionMxTmp[ idDistribucionMx=" + idDistribucionMx + " ]";
    }

}
