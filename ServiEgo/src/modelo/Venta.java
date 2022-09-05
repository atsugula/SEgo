/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v")
    , @NamedQuery(name = "Venta.findByIdVenta", query = "SELECT v FROM Venta v WHERE v.idVenta = :idVenta")
    , @NamedQuery(name = "Venta.findByFechaVenta", query = "SELECT v FROM Venta v WHERE v.fechaVenta = :fechaVenta")
    , @NamedQuery(name = "Venta.findByTotalVenta", query = "SELECT v FROM Venta v WHERE v.totalVenta = :totalVenta")
    , @NamedQuery(name = "Venta.findBySaldoVenta", query = "SELECT v FROM Venta v WHERE v.saldoVenta = :saldoVenta")
    , @NamedQuery(name = "Venta.findByDescuentoVenta", query = "SELECT v FROM Venta v WHERE v.descuentoVenta = :descuentoVenta")
    , @NamedQuery(name = "Venta.findByIvaVenta", query = "SELECT v FROM Venta v WHERE v.ivaVenta = :ivaVenta")
    , @NamedQuery(name = "Venta.findByObservacionesVentas", query = "SELECT v FROM Venta v WHERE v.observacionesVentas = :observacionesVentas")
    , @NamedQuery(name = "Venta.findByTipoPagoVenta", query = "SELECT v FROM Venta v WHERE v.tipoPagoVenta = :tipoPagoVenta")})
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_venta")
    private Integer idVenta;
    @Basic(optional = false)
    @Column(name = "fecha_venta")
    @Temporal(TemporalType.DATE)
    private Date fechaVenta;
    @Basic(optional = false)
    @Column(name = "total_venta")
    private int totalVenta;
    @Basic(optional = false)
    @Column(name = "saldo_venta")
    private int saldoVenta;
    @Basic(optional = false)
    @Column(name = "descuento_venta")
    private int descuentoVenta;
    @Column(name = "iva_venta")
    private Integer ivaVenta;
    @Column(name = "observaciones_ventas")
    private String observacionesVentas;
    @Basic(optional = false)
    @Column(name = "tipo_pago_venta")
    private String tipoPagoVenta;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false)
    private Cliente idCliente;
    @JoinColumn(name = "codigo_usuario", referencedColumnName = "codigo_usuario")
    @ManyToOne(optional = false)
    private Usuario codigoUsuario;
    @OneToMany(mappedBy = "idVenta")
    private List<DetalleVenta> detalleVentaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVenta")
    private List<Pago> pagoList;

    public Venta() {
    }

    public Venta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Venta(Integer idVenta, Date fechaVenta, int totalVenta, int saldoVenta, int descuentoVenta, String tipoPagoVenta) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
        this.saldoVenta = saldoVenta;
        this.descuentoVenta = descuentoVenta;
        this.tipoPagoVenta = tipoPagoVenta;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(int totalVenta) {
        this.totalVenta = totalVenta;
    }

    public int getSaldoVenta() {
        return saldoVenta;
    }

    public void setSaldoVenta(int saldoVenta) {
        this.saldoVenta = saldoVenta;
    }

    public int getDescuentoVenta() {
        return descuentoVenta;
    }

    public void setDescuentoVenta(int descuentoVenta) {
        this.descuentoVenta = descuentoVenta;
    }

    public Integer getIvaVenta() {
        return ivaVenta;
    }

    public void setIvaVenta(Integer ivaVenta) {
        this.ivaVenta = ivaVenta;
    }

    public String getObservacionesVentas() {
        return observacionesVentas;
    }

    public void setObservacionesVentas(String observacionesVentas) {
        this.observacionesVentas = observacionesVentas;
    }

    public String getTipoPagoVenta() {
        return tipoPagoVenta;
    }

    public void setTipoPagoVenta(String tipoPagoVenta) {
        this.tipoPagoVenta = tipoPagoVenta;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Usuario getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Usuario codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    @XmlTransient
    public List<DetalleVenta> getDetalleVentaList() {
        return detalleVentaList;
    }

    public void setDetalleVentaList(List<DetalleVenta> detalleVentaList) {
        this.detalleVentaList = detalleVentaList;
    }

    @XmlTransient
    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenta != null ? idVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.idVenta == null && other.idVenta != null) || (this.idVenta != null && !this.idVenta.equals(other.idVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Venta[ idVenta=" + idVenta + " ]";
    }
    
}
