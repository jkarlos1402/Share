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

@Entity
@Table(name = "RVVD_CAT_TIPO_CONSUMO")
public class RvvdCatTipoConsumo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_CAT_TIPO_CONSUMO")
    @SequenceGenerator(name = "RVVD_SEQ_CAT_TIPO_CONSUMO", sequenceName = "RVVD_SEQ_CAT_TIPO_CONSUMO", allocationSize = 1)
    @Column(name = "ID_TIPO_CONSUMO")
    private Integer idTipoConsumo;
    
    @Column(name = "TIPO_CONSUMO_R")
    private String tipoConsumoR;
    
    @Column(name = "TIPO_CONSUMO_EN")
    private String tipoConsumoEn;
    
    @Column(name = "STATUS")
    private boolean status;

    public RvvdCatTipoConsumo() {
    }

    public RvvdCatTipoConsumo(Integer idTipoConsumo) {
        this.idTipoConsumo = idTipoConsumo;
    }

    public Integer getIdTipoConsumo() {
        return idTipoConsumo;
    }

    public void setIdTipoConsumo(Integer idTipoConsumo) {
        this.idTipoConsumo = idTipoConsumo;
    }

    public String getTipoConsumoR() {
        return tipoConsumoR;
    }

    public void setTipoConsumoR(String tipoConsumoR) {
        this.tipoConsumoR = tipoConsumoR.toUpperCase();
    }

    public String getTipoConsumoEn() {
        return tipoConsumoEn;
    }

    public void setTipoConsumoEn(String tipoConsumoEn) {
        this.tipoConsumoEn = tipoConsumoEn.toUpperCase();
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoConsumo != null ? idTipoConsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdCatTipoConsumo)) {
            return false;
        }
        RvvdCatTipoConsumo other = (RvvdCatTipoConsumo) object;
        if ((this.idTipoConsumo == null && other.idTipoConsumo != null) || (this.idTipoConsumo != null && !this.idTipoConsumo.equals(other.idTipoConsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoConsumoR;
    }
    
}
