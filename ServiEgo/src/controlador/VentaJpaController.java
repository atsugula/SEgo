/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cliente;
import modelo.Usuario;
import modelo.DetalleVenta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Pago;
import modelo.Venta;

/**
 *
 * @author usuario
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public VentaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("EgoPU");
    }
    
    public int create2(Venta venta){
        
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(venta);
        em.flush();//sincroniza el objeto con la bd
        em.getTransaction().commit();
        em.close();
        return venta.getIdVenta();
    
    }

    public void create(Venta venta) {
        if (venta.getDetalleVentaList() == null) {
            venta.setDetalleVentaList(new ArrayList<DetalleVenta>());
        }
        if (venta.getPagoList() == null) {
            venta.setPagoList(new ArrayList<Pago>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idCliente = venta.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdCliente());
                venta.setIdCliente(idCliente);
            }
            Usuario codigoUsuario = venta.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario = em.getReference(codigoUsuario.getClass(), codigoUsuario.getCodigoUsuario());
                venta.setCodigoUsuario(codigoUsuario);
            }
            List<DetalleVenta> attachedDetalleVentaList = new ArrayList<DetalleVenta>();
            for (DetalleVenta detalleVentaListDetalleVentaToAttach : venta.getDetalleVentaList()) {
                detalleVentaListDetalleVentaToAttach = em.getReference(detalleVentaListDetalleVentaToAttach.getClass(), detalleVentaListDetalleVentaToAttach.getIdDetalle());
                attachedDetalleVentaList.add(detalleVentaListDetalleVentaToAttach);
            }
            venta.setDetalleVentaList(attachedDetalleVentaList);
            List<Pago> attachedPagoList = new ArrayList<Pago>();
            for (Pago pagoListPagoToAttach : venta.getPagoList()) {
                pagoListPagoToAttach = em.getReference(pagoListPagoToAttach.getClass(), pagoListPagoToAttach.getCodigoPago());
                attachedPagoList.add(pagoListPagoToAttach);
            }
            venta.setPagoList(attachedPagoList);
            em.persist(venta);
            if (idCliente != null) {
                idCliente.getVentaList().add(venta);
                idCliente = em.merge(idCliente);
            }
            if (codigoUsuario != null) {
                codigoUsuario.getVentaList().add(venta);
                codigoUsuario = em.merge(codigoUsuario);
            }
            for (DetalleVenta detalleVentaListDetalleVenta : venta.getDetalleVentaList()) {
                Venta oldIdVentaOfDetalleVentaListDetalleVenta = detalleVentaListDetalleVenta.getIdVenta();
                detalleVentaListDetalleVenta.setIdVenta(venta);
                detalleVentaListDetalleVenta = em.merge(detalleVentaListDetalleVenta);
                if (oldIdVentaOfDetalleVentaListDetalleVenta != null) {
                    oldIdVentaOfDetalleVentaListDetalleVenta.getDetalleVentaList().remove(detalleVentaListDetalleVenta);
                    oldIdVentaOfDetalleVentaListDetalleVenta = em.merge(oldIdVentaOfDetalleVentaListDetalleVenta);
                }
            }
            for (Pago pagoListPago : venta.getPagoList()) {
                Venta oldIdVentaOfPagoListPago = pagoListPago.getIdVenta();
                pagoListPago.setIdVenta(venta);
                pagoListPago = em.merge(pagoListPago);
                if (oldIdVentaOfPagoListPago != null) {
                    oldIdVentaOfPagoListPago.getPagoList().remove(pagoListPago);
                    oldIdVentaOfPagoListPago = em.merge(oldIdVentaOfPagoListPago);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta venta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta persistentVenta = em.find(Venta.class, venta.getIdVenta());
            Cliente idClienteOld = persistentVenta.getIdCliente();
            Cliente idClienteNew = venta.getIdCliente();
            Usuario codigoUsuarioOld = persistentVenta.getCodigoUsuario();
            Usuario codigoUsuarioNew = venta.getCodigoUsuario();
            List<DetalleVenta> detalleVentaListOld = persistentVenta.getDetalleVentaList();
            List<DetalleVenta> detalleVentaListNew = venta.getDetalleVentaList();
            List<Pago> pagoListOld = persistentVenta.getPagoList();
            List<Pago> pagoListNew = venta.getPagoList();
            List<String> illegalOrphanMessages = null;
            for (Pago pagoListOldPago : pagoListOld) {
                if (!pagoListNew.contains(pagoListOldPago)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pago " + pagoListOldPago + " since its idVenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdCliente());
                venta.setIdCliente(idClienteNew);
            }
            if (codigoUsuarioNew != null) {
                codigoUsuarioNew = em.getReference(codigoUsuarioNew.getClass(), codigoUsuarioNew.getCodigoUsuario());
                venta.setCodigoUsuario(codigoUsuarioNew);
            }
            List<DetalleVenta> attachedDetalleVentaListNew = new ArrayList<DetalleVenta>();
            for (DetalleVenta detalleVentaListNewDetalleVentaToAttach : detalleVentaListNew) {
                detalleVentaListNewDetalleVentaToAttach = em.getReference(detalleVentaListNewDetalleVentaToAttach.getClass(), detalleVentaListNewDetalleVentaToAttach.getIdDetalle());
                attachedDetalleVentaListNew.add(detalleVentaListNewDetalleVentaToAttach);
            }
            detalleVentaListNew = attachedDetalleVentaListNew;
            venta.setDetalleVentaList(detalleVentaListNew);
            List<Pago> attachedPagoListNew = new ArrayList<Pago>();
            for (Pago pagoListNewPagoToAttach : pagoListNew) {
                pagoListNewPagoToAttach = em.getReference(pagoListNewPagoToAttach.getClass(), pagoListNewPagoToAttach.getCodigoPago());
                attachedPagoListNew.add(pagoListNewPagoToAttach);
            }
            pagoListNew = attachedPagoListNew;
            venta.setPagoList(pagoListNew);
            venta = em.merge(venta);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getVentaList().remove(venta);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getVentaList().add(venta);
                idClienteNew = em.merge(idClienteNew);
            }
            if (codigoUsuarioOld != null && !codigoUsuarioOld.equals(codigoUsuarioNew)) {
                codigoUsuarioOld.getVentaList().remove(venta);
                codigoUsuarioOld = em.merge(codigoUsuarioOld);
            }
            if (codigoUsuarioNew != null && !codigoUsuarioNew.equals(codigoUsuarioOld)) {
                codigoUsuarioNew.getVentaList().add(venta);
                codigoUsuarioNew = em.merge(codigoUsuarioNew);
            }
            for (DetalleVenta detalleVentaListOldDetalleVenta : detalleVentaListOld) {
                if (!detalleVentaListNew.contains(detalleVentaListOldDetalleVenta)) {
                    detalleVentaListOldDetalleVenta.setIdVenta(null);
                    detalleVentaListOldDetalleVenta = em.merge(detalleVentaListOldDetalleVenta);
                }
            }
            for (DetalleVenta detalleVentaListNewDetalleVenta : detalleVentaListNew) {
                if (!detalleVentaListOld.contains(detalleVentaListNewDetalleVenta)) {
                    Venta oldIdVentaOfDetalleVentaListNewDetalleVenta = detalleVentaListNewDetalleVenta.getIdVenta();
                    detalleVentaListNewDetalleVenta.setIdVenta(venta);
                    detalleVentaListNewDetalleVenta = em.merge(detalleVentaListNewDetalleVenta);
                    if (oldIdVentaOfDetalleVentaListNewDetalleVenta != null && !oldIdVentaOfDetalleVentaListNewDetalleVenta.equals(venta)) {
                        oldIdVentaOfDetalleVentaListNewDetalleVenta.getDetalleVentaList().remove(detalleVentaListNewDetalleVenta);
                        oldIdVentaOfDetalleVentaListNewDetalleVenta = em.merge(oldIdVentaOfDetalleVentaListNewDetalleVenta);
                    }
                }
            }
            for (Pago pagoListNewPago : pagoListNew) {
                if (!pagoListOld.contains(pagoListNewPago)) {
                    Venta oldIdVentaOfPagoListNewPago = pagoListNewPago.getIdVenta();
                    pagoListNewPago.setIdVenta(venta);
                    pagoListNewPago = em.merge(pagoListNewPago);
                    if (oldIdVentaOfPagoListNewPago != null && !oldIdVentaOfPagoListNewPago.equals(venta)) {
                        oldIdVentaOfPagoListNewPago.getPagoList().remove(pagoListNewPago);
                        oldIdVentaOfPagoListNewPago = em.merge(oldIdVentaOfPagoListNewPago);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venta.getIdVenta();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getIdVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pago> pagoListOrphanCheck = venta.getPagoList();
            for (Pago pagoListOrphanCheckPago : pagoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Venta (" + venta + ") cannot be destroyed since the Pago " + pagoListOrphanCheckPago + " in its pagoList field has a non-nullable idVenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente idCliente = venta.getIdCliente();
            if (idCliente != null) {
                idCliente.getVentaList().remove(venta);
                idCliente = em.merge(idCliente);
            }
            Usuario codigoUsuario = venta.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario.getVentaList().remove(venta);
                codigoUsuario = em.merge(codigoUsuario);
            }
            List<DetalleVenta> detalleVentaList = venta.getDetalleVentaList();
            for (DetalleVenta detalleVentaListDetalleVenta : detalleVentaList) {
                detalleVentaListDetalleVenta.setIdVenta(null);
                detalleVentaListDetalleVenta = em.merge(detalleVentaListDetalleVenta);
            }
            em.remove(venta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Venta findVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //consultar ventas tipo CREDITO CON SALDO >0
    public List<Venta> consultarVentasPago(){
        String consulta = "SELECT v from Venta v where v.tipoPagoVenta = 'CREDITO' and v.saldoVenta > 0";
        Query query = getEntityManager().createQuery(consulta);
        return query.getResultList();//getSingleResult
    }
    
    public List<Venta> consultarVentasPagoCliente(int cedula){
        Query query = getEntityManager().createQuery("SELECT v from Venta v where v.tipoPagoVenta = 'CREDITO' and v.saldoVenta > 0 and v.idCliente.cedulaCliente = :cedula")
                .setParameter("cedula", cedula);
        return query.getResultList();//getSingleResult        
    }
    
    public List<Venta> userVentas(Usuario codigo){
        //si el usuario a hecho ventas no lo deja eliminar
        Query query = getEntityManager().createQuery("SELECT v FROM Venta v WHERE v.codigoUsuario = :codigo")
                .setParameter("codigo", codigo);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
        
    }
    
    public List<Venta> clienteVentas(Cliente codigo){
        //si el usuario a hecho ventas no lo deja eliminar
        Query query = getEntityManager().createQuery("SELECT v FROM Venta v WHERE v.idCliente = :codigo")
                .setParameter("codigo", codigo);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
        
    }
    
}
