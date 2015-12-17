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

@Entity
@Table(name = "RVVD_RECLASIF_EMPAQUE")
public class RvvdReclasifEmpaque implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "PAIS")
    private String pais;

    @Column(name = "EMPAQUE")
    private String empaque;

    @Column(name = "RETORNABILIDAD")
    private String retornabilidad;

    @Column(name = "TIPO_CONSUMO")
    private String tipoConsumo;

    @Column(name = "TAMANO")
    private String tamano;

    @Column(name = "CATEGORIA")
    private String categoria;

    @Column(name = "TIPO_CONSUMO_R")
    private String tipoConsumoR;

    @Column(name = "TIPO_CONSUMO_EN")
    private String tipoConsumoEn;

    @Column(name = "EMPAQUE_R")
    private String empaqueR;

    @Column(name = "EMPAQUE_EN")
    private String empaqueEn;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "RVVD_SEQ_RECLASIF_EMPAQUE")
    @SequenceGenerator(name = "RVVD_SEQ_RECLASIF_EMPAQUE",sequenceName = "RVVD_SEQ_RECLASIF_EMPAQUE", allocationSize = 1)
    @Column(name = "ID_RECLASIF_EMPAQUE")
    private BigDecimal idReclasifEmpaque;

    public RvvdReclasifEmpaque() {
    }

    public RvvdReclasifEmpaque(BigDecimal idReclasifEmpaque) {
        this.idReclasifEmpaque = idReclasifEmpaque;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEmpaque() {
        return empaque;
    }

    public void setEmpaque(String empaque) {
        this.empaque = empaque;
    }

    public String getRetornabilidad() {
        return retornabilidad;
    }

    public void setRetornabilidad(String retornabilidad) {
        this.retornabilidad = retornabilidad;
    }

    public String getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(String tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoConsumoR() {
        return tipoConsumoR;
    }

    public void setTipoConsumoR(String tipoConsumoR) {
        this.tipoConsumoR = tipoConsumoR;
    }

    public String getTipoConsumoEn() {
        return tipoConsumoEn;
    }

    public void setTipoConsumoEn(String tipoConsumoEn) {
        this.tipoConsumoEn = tipoConsumoEn;
    }

    public String getEmpaqueR() {
        return empaqueR;
    }

    public void setEmpaqueR(String empaqueR) {
        this.empaqueR = empaqueR;
    }

    public String getEmpaqueEn() {
        return empaqueEn;
    }

    public void setEmpaqueEn(String empaqueEn) {
        this.empaqueEn = empaqueEn;
    }

    public BigDecimal getIdReclasifEmpaque() {
        return idReclasifEmpaque;
    }

    public void setIdReclasifEmpaque(BigDecimal idReclasifEmpaque) {
        this.idReclasifEmpaque = idReclasifEmpaque;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReclasifEmpaque != null ? idReclasifEmpaque.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdReclasifEmpaque)) {
            return false;
        }
        RvvdReclasifEmpaque other = (RvvdReclasifEmpaque) object;
        if ((this.idReclasifEmpaque == null && other.idReclasifEmpaque != null) || (this.idReclasifEmpaque != null && !this.idReclasifEmpaque.equals(other.idReclasifEmpaque))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.daily.pojos.RvvdReclasifEmpaque[ idReclasifEmpaque=" + idReclasifEmpaque + " ]";
    }
    
}
