package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RVVD_CAT_CATEGORIA_OFICIAL")
public class RvvdCatCategoriaOficial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "RVVD_SEQ_CAT_CATEGORIA_OFICIAL")
    @SequenceGenerator(name = "RVVD_SEQ_CAT_CATEGORIA_OFICIAL",sequenceName = "RVVD_SEQ_CAT_CATEGORIA_OFICIAL", allocationSize = 1)
    @Column(name = "ID_CATEGORIA_OFICIAL")
    private Integer idCategoriaOficial;
    
    @Column(name = "CATEGORIA_OFICIAL")
    private String categoriaOficial;
    
    @Column(name = "CATEGORIA_OFICIAL_EN")
    private String categoriaOficialEn;
    
    @OneToMany(mappedBy = "idCategoriaOficial")
    private List<RvvdCatCategoria> rvvdCatCategoriaList;
    
    @Column(name = "STATUS")
    private boolean status;

    public RvvdCatCategoriaOficial() {
    }

    public RvvdCatCategoriaOficial(Integer idCategoriaOficial) {
        this.idCategoriaOficial = idCategoriaOficial;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getIdCategoriaOficial() {
        return idCategoriaOficial;
    }

    public void setIdCategoriaOficial(Integer idCategoriaOficial) {
        this.idCategoriaOficial = idCategoriaOficial;
    }

    public String getCategoriaOficial() {
        return categoriaOficial;
    }

    public void setCategoriaOficial(String categoriaOficial) {
        this.categoriaOficial = categoriaOficial.toUpperCase();
    }

    public String getCategoriaOficialEn() {
        return categoriaOficialEn;
    }

    public void setCategoriaOficialEn(String categoriaOficialEn) {
        this.categoriaOficialEn = categoriaOficialEn.toUpperCase();
    }

    public List<RvvdCatCategoria> getRvvdCatCategoriaList() {
        return rvvdCatCategoriaList;
    }

    public void setRvvdCatCategoriaList(List<RvvdCatCategoria> rvvdCatCategoriaList) {
        this.rvvdCatCategoriaList = rvvdCatCategoriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoriaOficial != null ? idCategoriaOficial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdCatCategoriaOficial)) {
            return false;
        }
        RvvdCatCategoriaOficial other = (RvvdCatCategoriaOficial) object;
        if ((this.idCategoriaOficial == null && other.idCategoriaOficial != null) || (this.idCategoriaOficial != null && !this.idCategoriaOficial.equals(other.idCategoriaOficial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return categoriaOficial;
    }
    
}
