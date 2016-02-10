package com.femsa.kof.daily.pojos;

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
@Table(name = "RVVD_RECLASIF_CATEGORIA")
public class RvvdReclasifCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "PAIS")
    private String pais;
    @Column(name = "CATEGORIA")
    private String categoria;
    @Column(name = "CATEGORIA_R")
    private String categoriaR;
    @Column(name = "CATEGORIA_EN")
    private String categoriaEn;
    @Column(name = "CATEGORIA_OFICIAL_R")
    private String categoriaOficialR;
    @Column(name = "CATEGORIA_OFICIAL_EN")
    private String categoriaOficialEn;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_RECLASIF_CATEGORIA")
    @SequenceGenerator(name = "RVVD_SEQ_RECLASIF_CATEGORIA", sequenceName = "RVVD_SEQ_RECLASIF_CATEGORIA", allocationSize = 1)
    @Column(name = "ID_RECLASIF_CATEGORIA")
    private Integer idReclasifCategoria;    

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
        this.pais = pais.toUpperCase();
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
        this.categoria = categoria.toUpperCase();
    }

    /**
     *
     * @return
     */
    public String getCategoriaR() {
        return categoriaR;
    }

    /**
     *
     * @param categoriaR
     */
    public void setCategoriaR(String categoriaR) {
        this.categoriaR = categoriaR.toUpperCase();
    }

    /**
     *
     * @return
     */
    public String getCategoriaEn() {
        return categoriaEn;
    }

    /**
     *
     * @param categoriaEn
     */
    public void setCategoriaEn(String categoriaEn) {
        this.categoriaEn = categoriaEn.toUpperCase();
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
        this.categoriaOficialR = categoriaOficialR.toUpperCase();
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
        this.categoriaOficialEn = categoriaOficialEn.toUpperCase();
    }

    /**
     *
     * @return
     */
    public Integer getIdReclasifCategoria() {
        return idReclasifCategoria;
    }

    /**
     *
     * @param idReclasifCategoria
     */
    public void setIdReclasifCategoria(Integer idReclasifCategoria) {
        this.idReclasifCategoria = idReclasifCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReclasifCategoria != null ? idReclasifCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdReclasifCategoria)) {
            return false;
        }
        RvvdReclasifCategoria other = (RvvdReclasifCategoria) object;
        if ((this.idReclasifCategoria == null && other.idReclasifCategoria != null) || (this.idReclasifCategoria != null && !this.idReclasifCategoria.equals(other.idReclasifCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.daily.pojos.RvvdReclasifCategoria[ idReclasifCategoria=" + idReclasifCategoria + " ]";
    }
    
}
