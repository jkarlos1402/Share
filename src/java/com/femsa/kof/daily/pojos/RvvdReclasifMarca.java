package com.femsa.kof.daily.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RVVD_RECLASIF_MARCA")
public class RvvdReclasifMarca implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "PAIS")
    private String pais;
    
    @Column(name = "MARCA")
    private String marca;
    
    @Column(name = "MARCA_R")
    private String marcaR;
    
    @Column(name = "MARCA_EN")
    private String marcaEn;
    
    @Column(name = "CONTENIDO_CALORICO_R")
    private String contenidoCaloricoR;
    
    @Column(name = "CONTENIDO_CALORICO_EN")
    private String contenidoCaloricoEn;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVVD_SEQ_RECLASIF_MARCA")
    @SequenceGenerator(name = "RVVD_SEQ_RECLASIF_MARCA", sequenceName = "RVVD_SEQ_RECLASIF_MARCA", allocationSize = 1)
    @Column(name = "ID_RECLASIF_MARCA")
    private BigDecimal idReclasifMarca;

    public RvvdReclasifMarca() {
    }

    public RvvdReclasifMarca(BigDecimal idReclasifMarca) {
        this.idReclasifMarca = idReclasifMarca;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMarcaR() {
        return marcaR;
    }

    public void setMarcaR(String marcaR) {
        this.marcaR = marcaR;
    }

    public String getMarcaEn() {
        return marcaEn;
    }

    public void setMarcaEn(String marcaEn) {
        this.marcaEn = marcaEn;
    }

    public String getContenidoCaloricoR() {
        return contenidoCaloricoR;
    }

    public void setContenidoCaloricoR(String contenidoCaloricoR) {
        this.contenidoCaloricoR = contenidoCaloricoR;
    }

    public String getContenidoCaloricoEn() {
        return contenidoCaloricoEn;
    }

    public void setContenidoCaloricoEn(String contenidoCaloricoEn) {
        this.contenidoCaloricoEn = contenidoCaloricoEn;
    }

    public BigDecimal getIdReclasifMarca() {
        return idReclasifMarca;
    }

    public void setIdReclasifMarca(BigDecimal idReclasifMarca) {
        this.idReclasifMarca = idReclasifMarca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReclasifMarca != null ? idReclasifMarca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RvvdReclasifMarca)) {
            return false;
        }
        RvvdReclasifMarca other = (RvvdReclasifMarca) object;
        if ((this.idReclasifMarca == null && other.idReclasifMarca != null) || (this.idReclasifMarca != null && !this.idReclasifMarca.equals(other.idReclasifMarca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.daily.pojos.RvvdReclasifMarca[ idReclasifMarca=" + idReclasifMarca + " ]";
    }
    
}
