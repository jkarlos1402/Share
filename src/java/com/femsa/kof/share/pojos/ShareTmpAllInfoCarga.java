/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femsa.kof.share.pojos;

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
@Table(name = "SHARE_TMP_ALL_INFO_CARGA")
public class ShareTmpAllInfoCarga implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHARE_SEQ_ALL_INFO_CARGA")
    @SequenceGenerator(name = "SHARE_SEQ_ALL_INFO_CARGA", sequenceName = "SHARE_SEQ_ALL_INFO_CARGA", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PK_INFO_CARGA")
    private Integer pkInfoCarga;
    @Column(name = "FECHA")
    private String fecha;
    @Column(name = "ANIO")
    private Short anio;
    @Column(name = "MES")
    private String mes;
    @Column(name = "TIEMPO")
    private Integer tiempo;
    @Column(name = "PAIS")
    private String pais;
    @Column(name = "UNIDAD_NEGOCIO")
    private String unidadNegocio;
    @Column(name = "CANAL")
    private String canal;
    @Column(name = "SUBCANAL")
    private String subcanal;
    @Column(name = "UNIDAD_OPERATIVA")
    private String unidadOperativa;
    @Column(name = "REGION")
    private String region;
    @Column(name = "ZONA")
    private String zona;
    @Column(name = "GRUPO_CATEGORIA")
    private String grupoCategoria;
    @Column(name = "CATEGORIA")
    private String categoria;
    @Column(name = "FABRICANTE")
    private String fabricante;
    @Column(name = "MARCA")
    private String marca;
    @Column(name = "SABOR")
    private String sabor;
    @Column(name = "TAMANO")
    private String tamano;
    @Column(name = "EMPAQUE")
    private String empaque;
    @Column(name = "RETORNABILIDAD")
    private String retornabilidad;
    @Column(name = "VOLUMEN_MES")
    private Float volumenMes;
    @Column(name = "VENTA_MES")
    private Float ventaMes;        

    public Integer getPkInfoCarga() {
        return pkInfoCarga;
    }

    public void setPkInfoCarga(Integer pkInfoCarga) {
        this.pkInfoCarga = pkInfoCarga;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Short getAnio() {
        return anio;
    }

    public void setAnio(Short anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getUnidadNegocio() {
        return unidadNegocio;
    }

    public void setUnidadNegocio(String unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getSubcanal() {
        return subcanal;
    }

    public void setSubcanal(String subcanal) {
        this.subcanal = subcanal;
    }

    public String getUnidadOperativa() {
        return unidadOperativa;
    }

    public void setUnidadOperativa(String unidadOperativa) {
        this.unidadOperativa = unidadOperativa;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getGrupoCategoria() {
        return grupoCategoria;
    }

    public void setGrupoCategoria(String grupoCategoria) {
        this.grupoCategoria = grupoCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
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

    public Float getVolumenMes() {
        return volumenMes;
    }

    public void setVolumenMes(Float volumenMes) {
        this.volumenMes = volumenMes;
    }

    public Float getVentaMes() {
        return ventaMes;
    }

    public void setVentaMes(Float ventaMes) {
        this.ventaMes = ventaMes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkInfoCarga != null ? pkInfoCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShareTmpAllInfoCarga)) {
            return false;
        }
        ShareTmpAllInfoCarga other = (ShareTmpAllInfoCarga) object;
        if ((this.pkInfoCarga == null && other.pkInfoCarga != null) || (this.pkInfoCarga != null && !this.pkInfoCarga.equals(other.pkInfoCarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ShareTmpAllInfoCarga{" + "pkInfoCarga=" + pkInfoCarga + ", fecha=" + fecha + ", anio=" + anio + ", mes=" + mes + ", tiempo=" + tiempo + ", pais=" + pais + ", unidadNegocio=" + unidadNegocio + ", canal=" + canal + ", subcanal=" + subcanal + ", unidadOperativa=" + unidadOperativa + ", region=" + region + ", zona=" + zona + ", grupoCategoria=" + grupoCategoria + ", categoria=" + categoria + ", fabricante=" + fabricante + ", marca=" + marca + ", sabor=" + sabor + ", tamano=" + tamano + ", empaque=" + empaque + ", retornabilidad=" + retornabilidad + ", volumenMes=" + volumenMes + ", ventaMes=" + ventaMes + '}';
    }   

}
