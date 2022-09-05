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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "gasto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gasto.findAll", query = "SELECT g FROM Gasto g")
    , @NamedQuery(name = "Gasto.findByCodigoGasto", query = "SELECT g FROM Gasto g WHERE g.codigoGasto = :codigoGasto")
    , @NamedQuery(name = "Gasto.findByFechaGasto", query = "SELECT g FROM Gasto g WHERE g.fechaGasto = :fechaGasto")
    , @NamedQuery(name = "Gasto.findByValorGasto", query = "SELECT g FROM Gasto g WHERE g.valorGasto = :valorGasto")
    , @NamedQuery(name = "Gasto.findByObservacionesGasto", query = "SELECT g FROM Gasto g WHERE g.observacionesGasto = :observacionesGasto")})
public class Gasto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_gasto")
    private Integer codigoGasto;
    @Basic(optional = false)
    @Column(name = "fecha_gasto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGasto;
    @Basic(optional = false)
    @Column(name = "valor_gasto")
    private int valorGasto;
    @Column(name = "observaciones_gasto")
    private String observacionesGasto;
    @JoinColumn(name = "codigo_tipo_gasto", referencedColumnName = "codigo_tipo_gasto")
    @ManyToOne(optional = false)
    private TipoGasto codigoTipoGasto;

    public Gasto() {
    }

    public Gasto(Integer codigoGasto) {
        this.codigoGasto = codigoGasto;
    }

    public Gasto(Integer codigoGasto, Date fechaGasto, int valorGasto) {
        this.codigoGasto = codigoGasto;
        this.fechaGasto = fechaGasto;
        this.valorGasto = valorGasto;
    }

    public Integer getCodigoGasto() {
        return codigoGasto;
    }

    public void setCodigoGasto(Integer codigoGasto) {
        this.codigoGasto = codigoGasto;
    }

    public Date getFechaGasto() {
        return fechaGasto;
    }

    public void setFechaGasto(Date fechaGasto) {
        this.fechaGasto = fechaGasto;
    }

    public int getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(int valorGasto) {
        this.valorGasto = valorGasto;
    }

    public String getObservacionesGasto() {
        return observacionesGasto;
    }

    public void setObservacionesGasto(String observacionesGasto) {
        this.observacionesGasto = observacionesGasto;
    }

    public TipoGasto getCodigoTipoGasto() {
        return codigoTipoGasto;
    }

    public void setCodigoTipoGasto(TipoGasto codigoTipoGasto) {
        this.codigoTipoGasto = codigoTipoGasto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoGasto != null ? codigoGasto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gasto)) {
            return false;
        }
        Gasto other = (Gasto) object;
        if ((this.codigoGasto == null && other.codigoGasto != null) || (this.codigoGasto != null && !this.codigoGasto.equals(other.codigoGasto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Gasto[ codigoGasto=" + codigoGasto + " ]";
    }
    
}
