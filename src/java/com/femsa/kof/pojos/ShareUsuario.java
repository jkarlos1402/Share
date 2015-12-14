package com.femsa.kof.pojos;

import com.femsa.kof.util.EncrypterKOF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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

    @ManyToMany()    
    @JoinTable(name = "SHARE_USUARIO_PAIS", joinColumns = {
        @JoinColumn(name = "FK_USUARIO")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_PAIS")})
    private List<ShareCatPais> paises = new ArrayList<ShareCatPais>();

    public ShareUsuario() {
    }

    public ShareUsuario(Integer pkUsuario) {
        this.pkUsuario = pkUsuario;
    }

    public ShareUsuario(Integer pkUsuario, String usuario, String password) {
        this.pkUsuario = pkUsuario;
        this.usuario = usuario;
        this.password = password;
    }

    public ShareCatRol getRol() {
        return rol;
    }

    public void setRol(ShareCatRol rol) {
        this.rol = rol;
    }

    public List<ShareCatPais> getPaises() {
        return paises;
    }

    public void setPaises(List<ShareCatPais> paises) {
        this.paises = paises;
    }

    public Integer getPkUsuario() {
        return pkUsuario;
    }

    public void setPkUsuario(Integer pkUsuario) {
        this.pkUsuario = pkUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario.toUpperCase();
    }

    public String getPassword() {
        EncrypterKOF encrypterKOF = new EncrypterKOF();
        return encrypterKOF.decrypt(password);
    }

    public void setPassword(String password) {
        EncrypterKOF encrypterKOF = new EncrypterKOF();
        this.password = encrypterKOF.encrypt(password);
    }

    public String getNombre() {        
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais.toUpperCase();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean getEstatus() {
        return estatus;
    }

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
        return "ShareUsuario{" + "pkUsuario=" + pkUsuario + ", usuario=" + usuario + ", password=" + password + ", nombre=" + nombre + ", pais=" + pais + ", mail=" + mail + ", estatus=" + estatus + ", rol=" + rol + ", paises=" + paises + '}';
    }
   
}
