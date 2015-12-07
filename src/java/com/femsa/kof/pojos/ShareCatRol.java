package com.femsa.kof.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SHARE_CAT_ROL")
public class ShareCatRol implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PK_ROL")
    private BigDecimal pkRol;

    @Column(name = "ROL")
    private String rol;

    public ShareCatRol() {
    }

    public ShareCatRol(BigDecimal pkRol) {
        this.pkRol = pkRol;
    }

    public BigDecimal getPkRol() {
        return pkRol;
    }

    public void setPkRol(BigDecimal pkRol) {
        this.pkRol = pkRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkRol != null ? pkRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShareCatRol)) {
            return false;
        }
        ShareCatRol other = (ShareCatRol) object;
        if ((this.pkRol == null && other.pkRol != null) || (this.pkRol != null && !this.pkRol.equals(other.pkRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return rol;
    }
    
}
