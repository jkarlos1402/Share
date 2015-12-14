package com.femsa.kof.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SHARE_CAT_PAIS")
public class ShareCatPais implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SHARE_SEQ_CAT_PAIS")
    @SequenceGenerator(name = "SHARE_SEQ_CAT_PAIS",sequenceName = "SHARE_SEQ_CAT_PAIS", allocationSize = 1)
    @Column(name = "PK_PAIS")
    private Integer pkPais;

    @Column(name = "CLAVE_CORTA")
    private String claveCorta;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "IDSTATUS")
    private boolean idstatus;
    
    @Column(name = "NOMBRE_TABLA")
    private String nombreTabla;
    
    @ManyToMany(mappedBy = "paises",fetch = FetchType.LAZY)
    private List<ShareUsuario> usuarios = new ArrayList<ShareUsuario>();

    public ShareCatPais() {
    }

    public ShareCatPais(Integer pkPais) {
        this.pkPais = pkPais;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla.toUpperCase();
    }

    public List<ShareUsuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<ShareUsuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Integer getPkPais() {
        return pkPais;
    }

    public void setPkPais(Integer pkPais) {
        this.pkPais = pkPais;
    }

    public String getClaveCorta() {
        return claveCorta;
    }

    public void setClaveCorta(String claveCorta) {
        this.claveCorta = claveCorta.toUpperCase();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public boolean getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(boolean idstatus) {
        this.idstatus = idstatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkPais != null ? pkPais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {        
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShareCatPais)) {
            return false;
        }
        ShareCatPais other = (ShareCatPais) object;
        if ((this.pkPais == null && other.pkPais != null) || (this.pkPais != null && !this.pkPais.equals(other.pkPais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
