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

@Entity
@Table(name = "RVVD_ST_ROLLING")
public class RvvdStRolling implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "ANIO")
    private Short anio;
    
    @Column(name = "MES")
    private Short mes;
    
    @Column(name = "DIA")
    private Short dia;
    
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @Column(name = "PAIS")
    private String pais;
    
    @Column(name = "DESC_PAIS")
    private String descPais;
    
    @Column(name = "ZONA")
    private String zona;
    
    @Column(name = "CATEGORIA_OFICIAL_R")
    private String categoriaOficialR;
    
    @Column(name = "CATEGORIA_OFICIAL_EN")
    private String categoriaOficialEn;
    
    @Column(name = "GEC")
    private String gec;
    
    @Column(name = "ROLLING_CU")
    private BigInteger rollingCu;
    
    @Column(name = "ROLLING_INGRESO")
    private BigInteger rollingIngreso;
    
    @Column(name = "ROLLING_TA")
    private BigInteger rollingTa;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_ST_ROLLING")
    @SequenceGenerator(name = "RVVD_SEQ_ST_ROLLING", sequenceName = "RVVD_SEQ_ST_ROLLING", allocationSize = 1)
    @Column(name = "ID_ST_ROLLING")
    private BigDecimal idStRolling;

    public RvvdStRolling() {
    }

    public RvvdStRolling(BigDecimal idStRolling) {
        this.idStRolling = idStRolling;
    }

    public Short getAnio() {
        return anio;
    }

    public void setAnio(Short anio) {
        this.anio = anio;
    }

    public Short getMes() {
        return mes;
    }

    public void setMes(Short mes) {
        this.mes = mes;
    }

    public Short getDia() {
        return dia;
    }

    public void setDia(Short dia) {
        this.dia = dia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDescPais() {
        return descPais;
    }

    public void setDescPais(String descPais) {
        this.descPais = descPais;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getCategoriaOficialR() {
        return categoriaOficialR;
    }

    public void setCategoriaOficialR(String categoriaOficialR) {
        this.categoriaOficialR = categoriaOficialR;
    }

    public String getCategoriaOficialEn() {
        return categoriaOficialEn;
    }

    public void setCategoriaOficialEn(String categoriaOficialEn) {
        this.categoriaOficialEn = categoriaOficialEn;
    }

    public String getGec() {
        return gec;
    }

    public void setGec(String gec) {
        this.gec = gec;
    }

    public BigInteger getRollingCu() {
        return rollingCu;
    }

    public void setRollingCu(BigInteger rollingCu) {
        this.rollingCu = rollingCu;
    }

    public BigInteger getRollingIngreso() {
        return rollingIngreso;
    }

    public void setRollingIngreso(BigInteger rollingIngreso) {
        this.rollingIngreso = rollingIngreso;
    }

    public BigInteger getRollingTa() {
        return rollingTa;
    }

    public void setRollingTa(BigInteger rollingTa) {
        this.rollingTa = rollingTa;
    }

    public BigDecimal getIdStRolling() {
        return idStRolling;
    }

    public void setIdStRolling(BigDecimal idStRolling) {
        this.idStRolling = idStRolling;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStRolling != null ? idStRolling.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdStRolling)) {
            return false;
        }
        RvvdStRolling other = (RvvdStRolling) object;
        if ((this.idStRolling == null && other.idStRolling != null) || (this.idStRolling != null && !this.idStRolling.equals(other.idStRolling))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.daily.pojos.RvvdStRolling[ idStRolling=" + idStRolling + " ]";
    }
    
}
