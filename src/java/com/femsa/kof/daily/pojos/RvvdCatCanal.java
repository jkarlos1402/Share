package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "RVVD_CAT_CANAL")
public class RvvdCatCanal implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_CAT_CANAL")
    @SequenceGenerator(name = "RVVD_SEQ_CAT_CANAL", sequenceName = "RVVD_SEQ_CAT_CANAL", allocationSize = 1)
    @Column(name = "ID_CANAL")
    private Integer idCanal;

    @Column(name = "CANAL_R")
    private String canalR;

    @Column(name = "CANAL_EN")
    private String canalEn;

    @Column(name = "STATUS")
    private boolean status;

    @OneToMany(mappedBy = "canal", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RvvdCatSubCanal> subCanalesList;

    public List<RvvdCatSubCanal> getSubCanalesList() {
        return subCanalesList;
    }

    public void setSubCanalesList(List<RvvdCatSubCanal> subCanalesList) {
        this.subCanalesList = subCanalesList;
    }

    /**
     *
     * @return
     */
    public Integer getIdCanal() {
        return idCanal;
    }

    /**
     *
     * @param idCanal
     */
    public void setIdCanal(Integer idCanal) {
        this.idCanal = idCanal;
    }

    /**
     *
     * @return
     */
    public String getCanalR() {
        return canalR;
    }

    /**
     *
     * @param canalR
     */
    public void setCanalR(String canalR) {
        this.canalR = canalR.toUpperCase();
    }

    /**
     *
     * @return
     */
    public String getCanalEn() {
        return canalEn;
    }

    /**
     *
     * @param canalEn
     */
    public void setCanalEn(String canalEn) {
        this.canalEn = canalEn.toUpperCase();
    }

    /**
     *
     * @return
     */
    public boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCanal != null ? idCanal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdCatCanal)) {
            return false;
        }
        RvvdCatCanal other = (RvvdCatCanal) object;
        if ((this.idCanal == null && other.idCanal != null) || (this.idCanal != null && !this.idCanal.equals(other.idCanal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return canalR;
    }

}
