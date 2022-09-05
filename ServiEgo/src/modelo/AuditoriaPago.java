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
@Table(name = "auditoria_pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaPago.findAll", query = "SELECT a FROM AuditoriaPago a")
    , @NamedQuery(name = "AuditoriaPago.findByCodigoAuditoria", query = "SELECT a FROM AuditoriaPago a WHERE a.codigoAuditoria = :codigoAuditoria")
    , @NamedQuery(name = "AuditoriaPago.findByFechaAuditoria", query = "SELECT a FROM AuditoriaPago a WHERE a.fechaAuditoria = :fechaAuditoria")
    , @NamedQuery(name = "AuditoriaPago.findByValorPagoAnterior", query = "SELECT a FROM AuditoriaPago a WHERE a.valorPagoAnterior = :valorPagoAnterior")
    , @NamedQuery(name = "AuditoriaPago.findByValorPagoNuevo", query = "SELECT a FROM AuditoriaPago a WHERE a.valorPagoNuevo = :valorPagoNuevo")
    , @NamedQuery(name = "AuditoriaPago.findByCodigoPago", query = "SELECT a FROM AuditoriaPago a WHERE a.codigoPago = :codigoPago")
    , @NamedQuery(name = "AuditoriaPago.findByTipoOperacion", query = "SELECT a FROM AuditoriaPago a WHERE a.tipoOperacion = :tipoOperacion")
    , @NamedQuery(name = "AuditoriaPago.findByObservacionesPago", query = "SELECT a FROM AuditoriaPago a WHERE a.observacionesPago = :observacionesPago")})
public class AuditoriaPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_auditoria")
    private Integer codigoAuditoria;
    @Column(name = "fecha_auditoria")
    @Temporal(TemporalType.DATE)
    private Date fechaAuditoria;
    @Column(name = "valor_pago_anterior")
    private Integer valorPagoAnterior;
    @Column(name = "valor_pago_nuevo")
    private Integer valorPagoNuevo;
    @Column(name = "codigo_pago")
    private Integer codigoPago;
    @Column(name = "tipo_operacion")
    private String tipoOperacion;
    @Column(name = "observaciones_pago")
    private String observacionesPago;

    public AuditoriaPago() {
    }

    public AuditoriaPago(Integer codigoAuditoria) {
        this.codigoAuditoria = codigoAuditoria;
    }

    public Integer getCodigoAuditoria() {
        return codigoAuditoria;
    }

    public void setCodigoAuditoria(Integer codigoAuditoria) {
        this.codigoAuditoria = codigoAuditoria;
    }

    public Date getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(Date fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public Integer getValorPagoAnterior() {
        return valorPagoAnterior;
    }

    public void setValorPagoAnterior(Integer valorPagoAnterior) {
        this.valorPagoAnterior = valorPagoAnterior;
    }

    public Integer getValorPagoNuevo() {
        return valorPagoNuevo;
    }

    public void setValorPagoNuevo(Integer valorPagoNuevo) {
        this.valorPagoNuevo = valorPagoNuevo;
    }

    public Integer getCodigoPago() {
        return codigoPago;
    }

    public void setCodigoPago(Integer codigoPago) {
        this.codigoPago = codigoPago;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getObservacionesPago() {
        return observacionesPago;
    }

    public void setObservacionesPago(String observacionesPago) {
        this.observacionesPago = observacionesPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoAuditoria != null ? codigoAuditoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaPago)) {
            return false;
        }
        AuditoriaPago other = (AuditoriaPago) object;
        if ((this.codigoAuditoria == null && other.codigoAuditoria != null) || (this.codigoAuditoria != null && !this.codigoAuditoria.equals(other.codigoAuditoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AuditoriaPago[ codigoAuditoria=" + codigoAuditoria + " ]";
    }
    
}
