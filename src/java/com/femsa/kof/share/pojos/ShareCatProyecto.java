package com.femsa.kof.share.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "SHARE_CAT_PROYECTO")
public class ShareCatProyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PROYECTO")
    private Integer idProyecto;
    
    @Column(name = "NOMBRE_PROYECTO")
    private String nombreProyecto;
    
    @Column(name = "DESCRIPCION_PROYECTO")
    private String descripcionProyecto;
    
    @Column(name = "STATUS")
    private boolean status;
    
    @ManyToMany(mappedBy = "paises",fetch = FetchType.EAGER)
    private List<ShareUsuario> usuarios = new ArrayList<ShareUsuario>();   

    /**
     *
     * @return
     */
    public List<ShareUsuario> getUsuarios() {
        return usuarios;
    }

    /**
     *
     * @param usuarios
     */
    public void setUsuarios(List<ShareUsuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     *
     * @return
     */
    public Integer getIdProyecto() {
        return idProyecto;
    }

    /**
     *
     * @param idProyecto
     */
    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    /**
     *
     * @return
     */
    public String getNombreProyecto() {
        return nombreProyecto;
    }

    /**
     *
     * @param nombreProyecto
     */
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    /**
     *
     * @return
     */
    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    /**
     *
     * @param descripcionProyecto
     */
    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
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
        hash += (idProyecto != null ? idProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShareCatProyecto)) {
            return false;
        }
        ShareCatProyecto other = (ShareCatProyecto) object;
        if ((this.idProyecto == null && other.idProyecto != null) || (this.idProyecto != null && !this.idProyecto.equals(other.idProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreProyecto;
    }
    
}
