/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto")
    , @NamedQuery(name = "Producto.findByCodigoProducto", query = "SELECT p FROM Producto p WHERE p.codigoProducto = :codigoProducto")
    , @NamedQuery(name = "Producto.findByNombreProducto", query = "SELECT p FROM Producto p WHERE p.nombreProducto = :nombreProducto")
    , @NamedQuery(name = "Producto.findByPrecioCostoProducto", query = "SELECT p FROM Producto p WHERE p.precioCostoProducto = :precioCostoProducto")
    , @NamedQuery(name = "Producto.findByPorcentajeProducto", query = "SELECT p FROM Producto p WHERE p.porcentajeProducto = :porcentajeProducto")
    , @NamedQuery(name = "Producto.findByPrecioVentaProducto", query = "SELECT p FROM Producto p WHERE p.precioVentaProducto = :precioVentaProducto")
    , @NamedQuery(name = "Producto.findByUnidsProducto", query = "SELECT p FROM Producto p WHERE p.unidsProducto = :unidsProducto")
    , @NamedQuery(name = "Producto.findByEstanteria", query = "SELECT p FROM Producto p WHERE p.estanteria = :estanteria")
    , @NamedQuery(name = "Producto.findByNivel", query = "SELECT p FROM Producto p WHERE p.nivel = :nivel")
    , @NamedQuery(name = "Producto.findByUltimoCodigo", query = "SELECT p FROM Producto p WHERE p.ultimoCodigo = :ultimoCodigo")
    , @NamedQuery(name = "Producto.findByObservaciones", query = "SELECT p FROM Producto p WHERE p.observaciones = :observaciones")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_producto")
    private Integer idProducto;
    @Basic(optional = false)
    @Column(name = "codigo_producto")
    private String codigoProducto;
    @Basic(optional = false)
    @Column(name = "nombre_producto")
    private String nombreProducto;
    @Column(name = "precio_costo_producto")
    private Integer precioCostoProducto;
    @Basic(optional = false)
    @Column(name = "porcentaje_producto")
    private double porcentajeProducto;
    @Column(name = "precio_venta_producto")
    private Integer precioVentaProducto;
    @Basic(optional = false)
    @Column(name = "unids_producto")
    private int unidsProducto;
    @Column(name = "estanteria")
    private String estanteria;
    @Column(name = "nivel")
    private String nivel;
    @Column(name = "ultimo_codigo")
    private Integer ultimoCodigo;
    @Column(name = "observaciones")
    private String observaciones;
    @JoinTable(name = "proveedores_producto", joinColumns = {
        @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")}, inverseJoinColumns = {
        @JoinColumn(name = "codigo_proveedor", referencedColumnName = "codigo_proveedor")})
    @ManyToMany
    private List<Proveedor> proveedorList;
    @OneToMany(mappedBy = "idProducto")
    private List<DetalleVenta> detalleVentaList;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne
    private Categoria idCategoria;

    public Producto() {
    }

    public Producto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(Integer idProducto, String codigoProducto, String nombreProducto, double porcentajeProducto, int unidsProducto) {
        this.idProducto = idProducto;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.porcentajeProducto = porcentajeProducto;
        this.unidsProducto = unidsProducto;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getPrecioCostoProducto() {
        return precioCostoProducto;
    }

    public void setPrecioCostoProducto(Integer precioCostoProducto) {
        this.precioCostoProducto = precioCostoProducto;
    }

    public double getPorcentajeProducto() {
        return porcentajeProducto;
    }

    public void setPorcentajeProducto(double porcentajeProducto) {
        this.porcentajeProducto = porcentajeProducto;
    }

    public Integer getPrecioVentaProducto() {
        return precioVentaProducto;
    }

    public void setPrecioVentaProducto(Integer precioVentaProducto) {
        this.precioVentaProducto = precioVentaProducto;
    }

    public int getUnidsProducto() {
        return unidsProducto;
    }

    public void setUnidsProducto(int unidsProducto) {
        this.unidsProducto = unidsProducto;
    }

    public String getEstanteria() {
        return estanteria;
    }

    public void setEstanteria(String estanteria) {
        this.estanteria = estanteria;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Integer getUltimoCodigo() {
        return ultimoCodigo;
    }

    public void setUltimoCodigo(Integer ultimoCodigo) {
        this.ultimoCodigo = ultimoCodigo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public List<Proveedor> getProveedorList() {
        return proveedorList;
    }

    public void setProveedorList(List<Proveedor> proveedorList) {
        this.proveedorList = proveedorList;
    }

    @XmlTransient
    public List<DetalleVenta> getDetalleVentaList() {
        return detalleVentaList;
    }

    public void setDetalleVentaList(List<DetalleVenta> detalleVentaList) {
        this.detalleVentaList = detalleVentaList;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Producto[ idProducto=" + idProducto + " ]";
    }
    
}
