package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RVVD_CAT_CATEGORIA")
public class RvvdCatCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_CAT_CATEGORIA")
    @SequenceGenerator(name = "RVVD_SEQ_CAT_CATEGORIA", sequenceName = "RVVD_SEQ_CAT_CATEGORIA", allocationSize = 1)
    @Column(name = "ID_CATEGORIA")
    private Integer idCategoria;

    @Column(name = "CATEGORIA")
    private String categoria;

    @Column(name = "CATEGORIA_EN")
    private String categoriaEn;

    @JoinColumn(name = "ID_CATEGORIA_OFICIAL", referencedColumnName = "ID_CATEGORIA_OFICIAL")
    @ManyToOne
    private RvvdCatCategoriaOficial idCategoriaOficial;

    @Column(name = "STATUS")
    private boolean status;

    public RvvdCatCategoria() {
    }

    public RvvdCatCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria.toUpperCase();
    }

    public String getCategoriaEn() {
        return categoriaEn;
    }

    public void setCategoriaEn(String categoriaEn) {
        this.categoriaEn = categoriaEn.toUpperCase();
    }

    public RvvdCatCategoriaOficial getIdCategoriaOficial() {
        return idCategoriaOficial;
    }

    public void setIdCategoriaOficial(RvvdCatCategoriaOficial idCategoriaOficial) {
        this.idCategoriaOficial = idCategoriaOficial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoria != null ? idCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdCatCategoria)) {
            return false;
        }
        RvvdCatCategoria other = (RvvdCatCategoria) object;
        if ((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return categoriaEn;
    }

}
