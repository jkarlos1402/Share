package com.femsa.kof.pojos;

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
@Table(name = "SHARE_CAT_CATEGORIAS")
public class ShareCatCategorias implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SHARE_SEQ_CAT_CATEGORIAS")
    @SequenceGenerator(name = "SHARE_SEQ_CAT_CATEGORIAS",sequenceName = "SHARE_SEQ_CAT_CATEGORIAS", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PK_CATEGORIA")
    private Integer pkCategoria;
    
    @JoinColumn(name = "FK_GRUPO_CATEGORIA", referencedColumnName = "PK_GRUPO_CATEGORIA")
    @ManyToOne(optional = false)    
    private ShareCatGrupoCategorias fkGrupoCategoria;
    
    @Column(name = "CATEGORIA")
    private String categoria;
    
    @Column(name = "CATEGORIA_ESP")
    private String categoriaEsp;
    
    @Column(name = "ID_STATUS")
    private boolean status;

    public ShareCatCategorias() {
    }

    public ShareCatCategorias(Integer pkCategoria) {
        this.pkCategoria = pkCategoria;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCategoriaEsp() {
        return categoriaEsp;
    }

    public void setCategoriaEsp(String categoriaEsp) {
        this.categoriaEsp = categoriaEsp.toUpperCase();
    }

    public Integer getPkCategoria() {
        return pkCategoria;
    }

    public void setPkCategoria(Integer pkCategoria) {
        this.pkCategoria = pkCategoria;
    }

    public ShareCatGrupoCategorias getFkGrupoCategoria() {
        return fkGrupoCategoria;
    }

    public void setFkGrupoCategoria(ShareCatGrupoCategorias fkGrupoCategoria) {
        this.fkGrupoCategoria = fkGrupoCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria.toUpperCase();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkCategoria != null ? pkCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShareCatCategorias)) {
            return false;
        }
        ShareCatCategorias other = (ShareCatCategorias) object;
        if ((this.categoria == null && other.categoria != null) || (this.categoria != null && !this.categoria.equalsIgnoreCase(other.categoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return pkCategoria+"";
    }

}
