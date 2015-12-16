package com.femsa.kof.share.pojos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SHARE_CAT_GRUPO_CATEGORIAS")
public class ShareCatGrupoCategorias implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SHARE_SEQ_CAT_GRUPO_CATEGORIAS")
    @SequenceGenerator(name = "SHARE_SEQ_CAT_GRUPO_CATEGORIAS",sequenceName = "SHARE_SEQ_CAT_GRUPO_CATEGORIAS", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PK_GRUPO_CATEGORIA")
    private Integer pkGrupoCategoria;

    @Column(name = "GRUPO_CATEGORIA")
    private String grupoCategoria;
    
    @Column(name = "ID_STATUS")
    private boolean status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkGrupoCategoria")
    private List<ShareCatCategorias> categoriasList;

    public ShareCatGrupoCategorias() {
    }

    public ShareCatGrupoCategorias(Integer pkGrupoCategoria) {
        this.pkGrupoCategoria = pkGrupoCategoria;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ShareCatCategorias> getCategoriasList() {
        return categoriasList;
    }

    public void setCategoriasList(List<ShareCatCategorias> categoriasList) {
        this.categoriasList = categoriasList;
    }

    public Integer getPkGrupoCategoria() {
        return pkGrupoCategoria;
    }

    public void setPkGrupoCategoria(Integer pkGrupoCategoria) {
        this.pkGrupoCategoria = pkGrupoCategoria;
    }

    public String getGrupoCategoria() {
        return grupoCategoria;
    }

    public void setGrupoCategoria(String grupoCategoria) {
        this.grupoCategoria = grupoCategoria.toUpperCase();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkGrupoCategoria != null ? pkGrupoCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShareCatGrupoCategorias)) {
            return false;
        }
        ShareCatGrupoCategorias other = (ShareCatGrupoCategorias) object;
        if ((this.pkGrupoCategoria == null && other.pkGrupoCategoria != null) || (this.pkGrupoCategoria != null && !this.pkGrupoCategoria.equals(other.pkGrupoCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return grupoCategoria;
    }

}
