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
@Table(name = "RVVD_ST_ROLLING_TMP")
public class RvvdStRollingTmp implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "ANIO")
    private Integer anio;
    
    @Column(name = "MES")
    private Integer mes;
    
    @Column(name = "DIA")
    private Integer dia;
    
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @Column(name = "PAIS")
    private String pais;
    
    @Column(name = "DESC_PAIS")
    private String descPais;
    
    @Column(name = "ZONA")
    private String zona;
    
    @Column(name = "CATEGORIA")
    private String categoria;
    
    @Column(name = "CATEGORIA_OFICIAL_R")
    private String categoriaOficialR;
    
    @Column(name = "CATEGORIA_OFICIAL_EN")
    private String categoriaOficialEn;
    
    @Column(name = "GEC")
    private String gec;
    
    @Column(name = "ROLLING_CU")
    private double rollingCu;
    
    @Column(name = "ROLLING_INGRESO")
    private Double rollingIngreso;
    
    @Column(name = "ROLLING_TA")
    private Double rollingTa;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_ST_ROLLING_TMP")
    @SequenceGenerator(name = "RVVD_SEQ_ST_ROLLING_TMP", sequenceName = "RVVD_SEQ_ST_ROLLING_TMP", allocationSize = 1)
    @Column(name = "ID_ST_ROLLING")
    private BigDecimal idStRolling;

    /**
     *
     */
    public RvvdStRollingTmp() {
    }

    /**
     *
     * @param idStRolling
     */
    public RvvdStRollingTmp(BigDecimal idStRolling) {
        this.idStRolling = idStRolling;
    }

    /**
     *
     * @return
     */
    public Integer getAnio() {
        return anio;
    }

    /**
     *
     * @param anio
     */
    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    /**
     *
     * @return
     */
    public Integer getMes() {
        return mes;
    }

    /**
     *
     * @param mes
     */
    public void setMes(Integer mes) {
        this.mes = mes;
    }

    /**
     *
     * @return
     */
    public Integer getDia() {
        return dia;
    }

    /**
     *
     * @param dia
     */
    public void setDia(Integer dia) {
        this.dia = dia;
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
    public String getDescPais() {
        return descPais;
    }

    /**
     *
     * @param descPais
     */
    public void setDescPais(String descPais) {
        this.descPais = descPais;
    }

    /**
     *
     * @return
     */
    public String getZona() {
        return zona;
    }

    /**
     *
     * @param zona
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     *
     * @return
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     *
     * @param categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     *
     * @return
     */
    public String getCategoriaOficialR() {
        return categoriaOficialR;
    }

    /**
     *
     * @param categoriaOficialR
     */
    public void setCategoriaOficialR(String categoriaOficialR) {
        this.categoriaOficialR = categoriaOficialR;
    }

    /**
     *
     * @return
     */
    public String getCategoriaOficialEn() {
        return categoriaOficialEn;
    }

    /**
     *
     * @param categoriaOficialEn
     */
    public void setCategoriaOficialEn(String categoriaOficialEn) {
        this.categoriaOficialEn = categoriaOficialEn;
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
    public double getRollingCu() {
        return rollingCu;
    }

    /**
     *
     * @param rollingCu
     */
    public void setRollingCu(double rollingCu) {
        this.rollingCu = rollingCu;
    }

    /**
     *
     * @return
     */
    public Double getRollingIngreso() {
        return rollingIngreso;
    }

    /**
     *
     * @param rollingIngreso
     */
    public void setRollingIngreso(Double rollingIngreso) {
        this.rollingIngreso = rollingIngreso;
    }

    /**
     *
     * @return
     */
    public Double getRollingTa() {
        return rollingTa;
    }

    /**
     *
     * @param rollingTa
     */
    public void setRollingTa(Double rollingTa) {
        this.rollingTa = rollingTa;
    }

    /**
     *
     * @return
     */
    public BigDecimal getIdStRolling() {
        return idStRolling;
    }

    /**
     *
     * @param idStRolling
     */
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
        if (!(object instanceof RvvdStRollingTmp)) {
            return false;
        }
        RvvdStRollingTmp other = (RvvdStRollingTmp) object;
        if ((this.idStRolling == null && other.idStRolling != null) || (this.idStRolling != null && !this.idStRolling.equals(other.idStRolling))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return rollingCu+"";
    }
    
}
