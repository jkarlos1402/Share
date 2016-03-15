package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mapea la tabla RVVD_445_PH_TMP
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "RVVD_445_PH_TMP")
public class Rvvd445PhTmp implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_445_PH_TMP")
    @SequenceGenerator(name = "RVVD_SEQ_445_PH_TMP", sequenceName = "RVVD_SEQ_445_PH_TMP", allocationSize = 1)
    @Column(name = "ID_445_PH")
    private Long id445Ph;
    
    @Column(name = "GV_DESC_PAIS")
    private String gvDescPais;
    
    @Column(name = "GD_FECHA_AA")
    @Temporal(TemporalType.DATE)
    private Date gdFechaAa;
    
    @Column(name = "GV_DIA_OP_AA")
    private Integer gvDiaOpAa;
    
    @Column(name = "GD_FECHA_ACT")
    @Temporal(TemporalType.DATE)
    private Date gdFechaAct;
    
    @Column(name = "GV_DIA_OP_ACT")
    private Integer gvDiaOpAct;
    
    @Column(name = "GV_N_MES")
    private Integer gvNMes;
    
    @Column(name = "PK_TIEMPO")
    private Integer pkTiempo;
    
    @Column(name = "PK_TIEMPO_AA")
    private Integer pkTiempoAa;
    
    @Column(name = "PK_TIEMPO_ACT")
    private Integer pkTiempoAct;    

    public Long getId445Ph() {
        return id445Ph;
    }

    public void setId445Ph(Long id445Ph) {
        this.id445Ph = id445Ph;
    }

    public String getGvDescPais() {
        return gvDescPais;
    }

    public void setGvDescPais(String gvDescPais) {
        this.gvDescPais = gvDescPais;
    }

    public Date getGdFechaAa() {
        return gdFechaAa;
    }

    public void setGdFechaAa(Date gdFechaAa) {
        this.gdFechaAa = gdFechaAa;
    }

    public Integer getGvDiaOpAa() {
        return gvDiaOpAa;
    }

    public void setGvDiaOpAa(Integer gvDiaOpAa) {
        this.gvDiaOpAa = gvDiaOpAa;
    }

    public Date getGdFechaAct() {
        return gdFechaAct;
    }

    public void setGdFechaAct(Date gdFechaAct) {
        this.gdFechaAct = gdFechaAct;
    }

    public Integer getGvDiaOpAct() {
        return gvDiaOpAct;
    }

    public void setGvDiaOpAct(Integer gvDiaOpAct) {
        this.gvDiaOpAct = gvDiaOpAct;
    }

    public Integer getGvNMes() {
        return gvNMes;
    }

    public void setGvNMes(Integer gvNMes) {
        this.gvNMes = gvNMes;
    }

    public Integer getPkTiempo() {
        return pkTiempo;
    }

    public void setPkTiempo(Integer pkTiempo) {
        this.pkTiempo = pkTiempo;
    }

    public Integer getPkTiempoAa() {
        return pkTiempoAa;
    }

    public void setPkTiempoAa(Integer pkTiempoAa) {
        this.pkTiempoAa = pkTiempoAa;
    }

    public Integer getPkTiempoAct() {
        return pkTiempoAct;
    }

    public void setPkTiempoAct(Integer pkTiempoAct) {
        this.pkTiempoAct = pkTiempoAct;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id445Ph != null ? id445Ph.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rvvd445PhTmp)) {
            return false;
        }
        Rvvd445PhTmp other = (Rvvd445PhTmp) object;
        if ((this.id445Ph == null && other.id445Ph != null) || (this.id445Ph != null && !this.id445Ph.equals(other.id445Ph))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.daily.pojos.Rvvd445PhTmp[ id445Ph=" + id445Ph + " ]";
    }

}
