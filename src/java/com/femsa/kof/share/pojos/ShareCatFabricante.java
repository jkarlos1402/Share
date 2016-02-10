package com.femsa.kof.share.pojos;

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
@Table(name = "SHARE_CAT_FABRICANTE")
public class ShareCatFabricante implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SHARE_SEQ_CAT_FABRICANTE")
    @SequenceGenerator(name = "SHARE_SEQ_CAT_FABRICANTE",sequenceName = "SHARE_SEQ_CAT_FABRICANTE", allocationSize = 1)
    @Column(name = "PK_FABRICANTE")
    private Integer pkFabricante;   
    
    @Column(name = "FABRICANTE")
    private String fabricante;
    
    @Column(name = "ID_STATUS")
    private boolean status;    

    /**
     *
     * @return
     */
    public boolean isStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public Integer getPkFabricante() {
        return pkFabricante;
    }

    /**
     *
     * @param pkFabricante
     */
    public void setPkFabricante(Integer pkFabricante) {
        this.pkFabricante = pkFabricante;
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
        this.fabricante = fabricante.toUpperCase();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkFabricante != null ? pkFabricante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {       
        if (!(object instanceof ShareCatFabricante)) {
            return false;
        }
        ShareCatFabricante other = (ShareCatFabricante) object;
        if ((this.pkFabricante == null && other.pkFabricante != null) || (this.pkFabricante != null && !this.pkFabricante.equals(other.pkFabricante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.pojos.ShareCatFabricante[ pkFabricante=" + pkFabricante + " ]";
    }
    
}
