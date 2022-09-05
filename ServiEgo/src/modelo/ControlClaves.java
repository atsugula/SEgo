/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "control_claves")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlClaves.findAll", query = "SELECT c FROM ControlClaves c")
    , @NamedQuery(name = "ControlClaves.findByCodigoControl", query = "SELECT c FROM ControlClaves c WHERE c.codigoControl = :codigoControl")
    , @NamedQuery(name = "ControlClaves.findByClaveControl", query = "SELECT c FROM ControlClaves c WHERE c.claveControl = :claveControl")
    , @NamedQuery(name = "ControlClaves.findByFechaControl", query = "SELECT c FROM ControlClaves c WHERE c.fechaControl = :fechaControl")
    , @NamedQuery(name = "ControlClaves.findByUsuarioControl", query = "SELECT c FROM ControlClaves c WHERE c.usuarioControl = :usuarioControl")})
public class ControlClaves implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_control")
    private Integer codigoControl;
    @Column(name = "clave_control")
    private String claveControl;
    @Column(name = "fecha_control")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaControl;
    @Column(name = "usuario_control")
    private Integer usuarioControl;

    public ControlClaves() {
    }

    public ControlClaves(Integer codigoControl) {
        this.codigoControl = codigoControl;
    }

    public Integer getCodigoControl() {
        return codigoControl;
    }

    public void setCodigoControl(Integer codigoControl) {
        this.codigoControl = codigoControl;
    }

    public String getClaveControl() {
        return claveControl;
    }

    public void setClaveControl(String claveControl) {
        this.claveControl = claveControl;
    }

    public Date getFechaControl() {
        return fechaControl;
    }

    public void setFechaControl(Date fechaControl) {
        this.fechaControl = fechaControl;
    }

    public Integer getUsuarioControl() {
        return usuarioControl;
    }

    public void setUsuarioControl(Integer usuarioControl) {
        this.usuarioControl = usuarioControl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoControl != null ? codigoControl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlClaves)) {
            return false;
        }
        ControlClaves other = (ControlClaves) object;
        if ((this.codigoControl == null && other.codigoControl != null) || (this.codigoControl != null && !this.codigoControl.equals(other.codigoControl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ControlClaves[ codigoControl=" + codigoControl + " ]";
    }
    
}
