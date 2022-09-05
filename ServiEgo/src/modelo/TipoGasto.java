/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "tipo_gasto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoGasto.findAll", query = "SELECT t FROM TipoGasto t")
    , @NamedQuery(name = "TipoGasto.findByCodigoTipoGasto", query = "SELECT t FROM TipoGasto t WHERE t.codigoTipoGasto = :codigoTipoGasto")
    , @NamedQuery(name = "TipoGasto.findByNombreTipoGasto", query = "SELECT t FROM TipoGasto t WHERE t.nombreTipoGasto = :nombreTipoGasto")})
public class TipoGasto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_tipo_gasto")
    private Integer codigoTipoGasto;
    @Basic(optional = false)
    @Column(name = "nombre_tipo_gasto")
    private String nombreTipoGasto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoTipoGasto")
    private List<Gasto> gastoList;

    public TipoGasto() {
    }

    public TipoGasto(Integer codigoTipoGasto) {
        this.codigoTipoGasto = codigoTipoGasto;
    }

    public TipoGasto(Integer codigoTipoGasto, String nombreTipoGasto) {
        this.codigoTipoGasto = codigoTipoGasto;
        this.nombreTipoGasto = nombreTipoGasto;
    }

    public Integer getCodigoTipoGasto() {
        return codigoTipoGasto;
    }

    public void setCodigoTipoGasto(Integer codigoTipoGasto) {
        this.codigoTipoGasto = codigoTipoGasto;
    }

    public String getNombreTipoGasto() {
        return nombreTipoGasto;
    }

    public void setNombreTipoGasto(String nombreTipoGasto) {
        this.nombreTipoGasto = nombreTipoGasto;
    }

    @XmlTransient
    public List<Gasto> getGastoList() {
        return gastoList;
    }

    public void setGastoList(List<Gasto> gastoList) {
        this.gastoList = gastoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTipoGasto != null ? codigoTipoGasto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoGasto)) {
            return false;
        }
        TipoGasto other = (TipoGasto) object;
        if ((this.codigoTipoGasto == null && other.codigoTipoGasto != null) || (this.codigoTipoGasto != null && !this.codigoTipoGasto.equals(other.codigoTipoGasto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TipoGasto[ codigoTipoGasto=" + codigoTipoGasto + " ]";
    }
    
}
