package com.femsa.kof.share.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SHARE_CAT_CANALES")
public class ShareCatCanales implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PK_CANAL")
    private BigDecimal pkCanal;

    @Column(name = "GV_CANAL")
    private String gvCanal;

    public ShareCatCanales() {
    }

    public ShareCatCanales(BigDecimal pkCanal) {
        this.pkCanal = pkCanal;
    }

    public BigDecimal getPkCanal() {
        return pkCanal;
    }

    public void setPkCanal(BigDecimal pkCanal) {
        this.pkCanal = pkCanal;
    }

    public String getGvCanal() {
        return gvCanal;
    }

    public void setGvCanal(String gvCanal) {
        this.gvCanal = gvCanal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkCanal != null ? pkCanal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShareCatCanales)) {
            return false;
        }
        ShareCatCanales other = (ShareCatCanales) object;
        if ((this.gvCanal == null && other.gvCanal != null) || (this.gvCanal != null && !this.gvCanal.equalsIgnoreCase(other.gvCanal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return gvCanal;
    }
    
}
