/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femsa.kof.share.pojos;

import java.io.Serializable;
import java.math.BigInteger;
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
 * @author CND5067TDR
 */
@Entity
@Table(name = "SHARE_TMP_ALL_INFO_CARGA")
public class ShareTmpAllInfoCarga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "PAIS")
    private String pais;
    @Column(name = "CANAL")
    private String canal;
    @Column(name = "FECHA")
    private String fecha;
    @Column(name = "GRUPO_CATEGORIA")
    private String grupoCategoria;
    @Column(name = "CATEGORIA")
    private String categoria;
    @Column(name = "FABRICANTE")
    private String fabricante;
    @Column(name = "VOLUMEN_MES")
    private double volumenMes;
    @Column(name = "VENTA_MES")
    private double ventaMes;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHARE_SEQ_ALL_INFO_CARGA")
    @SequenceGenerator(name = "SHARE_SEQ_ALL_INFO_CARGA", sequenceName = "SHARE_SEQ_ALL_INFO_CARGA", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PK_INFO_CARGA")
    private BigInteger pkInfoCarga;
    
    @Column(name = "FK_USUARIO")
    private Integer fkUsuario;

    /**
     *
     */
    public ShareTmpAllInfoCarga() {
    }

    /**
     *
     * @param pkInfoCarga
     */
    public ShareTmpAllInfoCarga(BigInteger pkInfoCarga) {
        this.pkInfoCarga = pkInfoCarga;
    }

    /**
     *
     * @return
     */
    public double getFkUsuario() {
        return fkUsuario;
    }

    /**
     *
     * @param fkUsuario
     */
    public void setFkUsuario(Integer fkUsuario) {
        this.fkUsuario = fkUsuario;
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
    public String getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @return
     */
    public String getGrupoCategoria() {
        return grupoCategoria;
    }

    /**
     *
     * @param grupoCategoria
     */
    public void setGrupoCategoria(String grupoCategoria) {
        this.grupoCategoria = grupoCategoria;
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
    public String getFabricante() {
        return fabricante;
    }

    /**
     *
     * @param fabricante
     */
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    /**
     *
     * @return
     */
    public double getVolumenMes() {
        return volumenMes;
    }

    /**
     *
     * @param volumenMes
     */
    public void setVolumenMes(double volumenMes) {
        this.volumenMes = volumenMes;
    }

    /**
     *
     * @return
     */
    public double getVentaMes() {
        return ventaMes;
    }

    /**
     *
     * @param ventaMes
     */
    public void setVentaMes(double ventaMes) {
        this.ventaMes = ventaMes;
    }

    /**
     *
     * @return
     */
    public BigInteger getPkInfoCarga() {
        return pkInfoCarga;
    }

    /**
     *
     * @param pkInfoCarga
     */
    public void setPkInfoCarga(BigInteger pkInfoCarga) {
        this.pkInfoCarga = pkInfoCarga;
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
        return "com.femsa.kof.pojos.ShareTmpAllInfoCarga[ pkInfoCarga=" + pkInfoCarga + " ]";
    }
}
