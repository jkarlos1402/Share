package com.femsa.kof.share.pojos;

import com.femsa.kof.util.EncrypterKOF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author TMXIDSJPINAM
 */
@Entity
@Table(name = "SHARE_USUARIO")
public class ShareUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SHARE_SEQ_USER")
    @SequenceGenerator(name = "SHARE_SEQ_USER",sequenceName = "SHARE_SEQ_USER", allocationSize = 1)
    @Column(name = "PK_USUARIO")
    private Integer pkUsuario;

    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;

    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "PAIS")
    private String pais;

    @Column(name = "MAIL")
    private String mail;

    @Column(name = "ESTATUS")
    private boolean estatus;

    @JoinColumn(name = "FK_ID_ROL")
    @OneToOne(optional = false, cascade = CascadeType.MERGE)
    private ShareCatRol rol;
    
    @Column(name = "INTENTOS")
    private Integer intentos;
    
    @Column(name = "LASTLOGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    
    @Column(name = "PASSRESET")
    private boolean passReset;

    @ManyToMany(fetch = FetchType.EAGER)    
    @JoinTable(name = "SHARE_USUARIO_PAIS", joinColumns = {
        @JoinColumn(name = "FK_USUARIO")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_PAIS")})
    private List<ShareCatPais> paises = new ArrayList<ShareCatPais>();
    
    @ManyToMany(fetch = FetchType.EAGER)    
    @JoinTable(name = "SHARE_USUARIO_PROYECTO", joinColumns = {
        @JoinColumn(name = "ID_USER")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_PROYECTO")})
    private List<ShareCatProyecto> proyectos = new ArrayList<ShareCatProyecto>();    

    /**
     *
     * @return
     */
    public boolean isPassReset() {
        return passReset;
    }

    /**
     *
     * @param passReset
     */
    public void setPassReset(boolean passReset) {
        this.passReset = passReset;
    }

    /**
     *
     * @return
     */
    public Integer getIntentos() {
        return intentos;
    }

    /**
     *
     * @param intentos
     */
    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    /**
     *
     * @return
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     *
     * @param lastLogin
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     *
     * @return
     */
    public List<ShareCatProyecto> getProyectos() {
        return proyectos;
    }

    /**
     *
     * @param proyectos
     */
    public void setProyectos(List<ShareCatProyecto> proyectos) {
        this.proyectos = proyectos;
    }

    /**
     *
     * @return
     */
    public ShareCatRol getRol() {
        return rol;
    }

    /**
     *
     * @param rol
     */
    public void setRol(ShareCatRol rol) {
        this.rol = rol;
    }

    /**
     *
     * @return
     */
    public List<ShareCatPais> getPaises() {
        return paises;
    }

    /**
     *
     * @param paises
     */
    public void setPaises(List<ShareCatPais> paises) {
        this.paises = paises;
    }

    /**
     *
     * @return
     */
    public Integer getPkUsuario() {
        return pkUsuario;
    }

    /**
     *
     * @param pkUsuario
     */
    public void setPkUsuario(Integer pkUsuario) {
        this.pkUsuario = pkUsuario;
    }

    /**
     *
     * @return
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario.toUpperCase();
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        EncrypterKOF encrypterKOF = new EncrypterKOF();
        return password != null ? encrypterKOF.decrypt(password) : password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        EncrypterKOF encrypterKOF = new EncrypterKOF();
        this.password = encrypterKOF.encrypt(password);
    }

    /**
     *
     * @return
     */
    public String getNombre() {        
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    /**
     *
     * @return
     */
    public String getPais() {
        return pais;
    }

    /**
     *
     * @param pais
     */
    public void setPais(String pais) {
        this.pais = pais.toUpperCase();
    }

    /**
     *
     * @return
     */
    public String getMail() {
        return mail;
    }

    /**
     *
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     *
     * @return
     */
    public boolean getEstatus() {
        return estatus;
    }

    /**
     *
     * @param estatus
     */
    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkUsuario != null ? pkUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShareUsuario)) {
            return false;
        }
        ShareUsuario other = (ShareUsuario) object;
        if ((this.pkUsuario == null && other.pkUsuario != null) || (this.pkUsuario != null && !this.pkUsuario.equals(other.pkUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
   
    /**
     *
     * @param shortNameCountry
     * @return
     */
    public boolean haveCountry(String shortNameCountry){
        if(paises != null){
            for (ShareCatPais pais : paises) {
                if(pais.getClaveCorta().equalsIgnoreCase(shortNameCountry)){
                    return true;
                }
            }            
        }
        return false;
    }
}
