/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femsa.kof.share.pojos;

import com.femsa.kof.daily.pojos.RvvdCatCanal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mapea el registro de cargas
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "SHARE_LOAD_LOG")
public class ShareLoadLog implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SHARE_SEQ_LOAD_LOG")
    @SequenceGenerator(name = "SHARE_SEQ_LOAD_LOG",sequenceName = "SHARE_SEQ_LOAD_LOG", allocationSize = 1)
    @Column(name = "ID_LOG")
    private BigDecimal idLog;
    
    @Column(name = "NOMBRE_PROCESO")
    private String nombreProceso;
    
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
    
    @Column(name = "NOMBRE_PROYECTO")
    private String nombreProyecto;
    
    @Column(name = "FECHA_EJECUCION")
    @Temporal(TemporalType.DATE)
    private Date fechaEjecucion;
    
    @Column(name = "INICIO_EJECUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicioEjecucion;
    
    @Column(name = "FIN_EJECUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finEjecucion;
    
    @Column(name = "REGISTROS_PROCESADOS")
    private long registrosProcesados;
    
    @Column(name = "PAIS")
    private String pais;
    
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "PK_USUARIO")
    @ManyToOne(fetch = FetchType.EAGER)    
    private ShareUsuario usuario;    

    public ShareLoadLog() {
    }

    public ShareLoadLog(BigDecimal idLog) {
        this.idLog = idLog;
    }

    public BigDecimal getIdLog() {
        return idLog;
    }

    public void setIdLog(BigDecimal idLog) {
        this.idLog = idLog;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public Date getInicioEjecucion() {
        return inicioEjecucion;
    }

    public void setInicioEjecucion(Date inicioEjecucion) {
        this.inicioEjecucion = inicioEjecucion;
    }

    public Date getFinEjecucion() {
        return finEjecucion;
    }

    public void setFinEjecucion(Date finEjecucion) {
        this.finEjecucion = finEjecucion;
    }

    public long getRegistrosProcesados() {
        return registrosProcesados;
    }

    public void setRegistrosProcesados(long registrosProcesados) {
        this.registrosProcesados = registrosProcesados;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public ShareUsuario getUsuario() {
        return usuario;
    }

    public void setIdUsuario(ShareUsuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLog != null ? idLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShareLoadLog)) {
            return false;
        }
        ShareLoadLog other = (ShareLoadLog) object;
        if ((this.idLog == null && other.idLog != null) || (this.idLog != null && !this.idLog.equals(other.idLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.femsa.kof.share.pojos.ShareLoadLog[ idLog=" + idLog + " ]";
    }

}
