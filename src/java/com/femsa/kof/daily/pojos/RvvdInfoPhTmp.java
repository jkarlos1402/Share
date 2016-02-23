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
//@Table(name = "XTMPINDICOMER_PHI_DIA_CARGA")
@Table(name = "RVVD_INFO_PH_TMP")
public class RvvdInfoPhTmp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @Column(name = "ZONA")
    private String zona;
    
    @Column(name = "CATEGORIA")
    private String categoria;
    
    @Column(name = "UNIDAD_DE_NEGOCIO")
    private String unidadDeNegocio;
    
    @Column(name = "GEC")
    private String gec;
    
    @Column(name = "CANAL")
    private String canal;
    
    @Column(name = "MARCA")
    private String marca;
    
    @Column(name = "EMPAQUE")
    private String empaque;
    
    @Column(name = "RETORNABILIDAD")
    private String retornabilidad;
    
    @Column(name = "TIPO_DE_CONSUMO")
    private String tipoDeConsumo;
    
    @Column(name = "VENTA_CU")
    private double ventaCu;
    
    @Column(name = "INGRESO_NETO")
    private double ingresoNeto;
    
    @Column(name = "CUOTA")
    private double cuota;
    
    @Column(name = "VENTA_TA")
    private double ventaTa;
    
    @Column(name = "ID_TIEMPO")
    private BigInteger idTiempo;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_INFO_PH_TMP")
    @SequenceGenerator(name = "RVVD_SEQ_INFO_PH_TMP", sequenceName = "RVVD_SEQ_INFO_PH_TMP", allocationSize = 1)
    @Column(name = "ID_INFO_PH")
    private BigDecimal idInfoPh;    

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
    public String getUnidadDeNegocio() {
        return unidadDeNegocio;
    }

    /**
     *
     * @param unidadDeNegocio
     */
    public void setUnidadDeNegocio(String unidadDeNegocio) {
        this.unidadDeNegocio = unidadDeNegocio;
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
    public String getCanal() {
        return canal;
    }

    /**
     *
     * @param canal
     */
    public void setCanal(String canal) {
        this.canal = canal;
    }

    /**
     *
     * @return
     */
    public String getMarca() {
        return marca;
    }

    /**
     *
     * @param marca
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     *
     * @return
     */
    public String getEmpaque() {
        return empaque;
    }

    /**
     *
     * @param empaque
     */
    public void setEmpaque(String empaque) {
        this.empaque = empaque;
    }

    /**
     *
     * @return
     */
    public String getRetornabilidad() {
        return retornabilidad;
    }

    /**
     *
     * @param retornabilidad
     */
    public void setRetornabilidad(String retornabilidad) {
        this.retornabilidad = retornabilidad;
    }

    /**
     *
     * @return
     */
    public String getTipoDeConsumo() {
        return tipoDeConsumo;
    }

    /**
     *
     * @param tipoDeConsumo
     */
    public void setTipoDeConsumo(String tipoDeConsumo) {
        this.tipoDeConsumo = tipoDeConsumo;
    }

    /**
     *
     * @return
     */
    public double getVentaCu() {
        return ventaCu;
    }

    /**
     *
     * @param ventaCu
     */
    public void setVentaCu(double ventaCu) {
        this.ventaCu = ventaCu;
    }

    /**
     *
     * @return
     */
    public double getIngresoNeto() {
        return ingresoNeto;
    }

    /**
     *
     * @param ingresoNeto
     */
    public void setIngresoNeto(double ingresoNeto) {
        this.ingresoNeto = ingresoNeto;
    }

    /**
     *
     * @return
     */
    public double getCuota() {
        return cuota;
    }

    /**
     *
     * @param cuota
     */
    public void setCuota(double cuota) {
        this.cuota = cuota;
    }

    /**
     *
     * @return
     */
    public double getVentaTa() {
        return ventaTa;
    }

    /**
     *
     * @param ventaTa
     */
    public void setVentaTa(double ventaTa) {
        this.ventaTa = ventaTa;
    }

    /**
     *
     * @return
     */
    public BigInteger getIdTiempo() {
        return idTiempo;
    }

    /**
     *
     * @param idTiempo
     */
    public void setIdTiempo(BigInteger idTiempo) {
        this.idTiempo = idTiempo;
    }

    /**
     *
     * @return
     */
    public BigDecimal getIdInfoPh() {
        return idInfoPh;
    }

    /**
     *
     * @param idInfoPh
     */
    public void setIdInfoPh(BigDecimal idInfoPh) {
        this.idInfoPh = idInfoPh;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInfoPh != null ? idInfoPh.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdInfoPhTmp)) {
            return false;
        }
        RvvdInfoPhTmp other = (RvvdInfoPhTmp) object;
        if ((this.idInfoPh == null && other.idInfoPh != null) || (this.idInfoPh != null && !this.idInfoPh.equals(other.idInfoPh))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.daily.pojos.RvvdInfoPh[ idInfoPh=" + idInfoPh + " ]";
    }
    
}
