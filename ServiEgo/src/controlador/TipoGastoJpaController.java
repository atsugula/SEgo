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
import modelo.Gasto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import modelo.TipoGasto;

/**
 *
 * @author usuario
 */
public class TipoGastoJpaController implements Serializable {

    public TipoGastoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TipoGastoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("EgoPU");
    }
    
    public void create(TipoGasto tipoGasto) {
        if (tipoGasto.getGastoList() == null) {
            tipoGasto.setGastoList(new ArrayList<Gasto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Gasto> attachedGastoList = new ArrayList<Gasto>();
            for (Gasto gastoListGastoToAttach : tipoGasto.getGastoList()) {
                gastoListGastoToAttach = em.getReference(gastoListGastoToAttach.getClass(), gastoListGastoToAttach.getCodigoGasto());
                attachedGastoList.add(gastoListGastoToAttach);
            }
            tipoGasto.setGastoList(attachedGastoList);
            em.persist(tipoGasto);
            for (Gasto gastoListGasto : tipoGasto.getGastoList()) {
                TipoGasto oldCodigoTipoGastoOfGastoListGasto = gastoListGasto.getCodigoTipoGasto();
                gastoListGasto.setCodigoTipoGasto(tipoGasto);
                gastoListGasto = em.merge(gastoListGasto);
                if (oldCodigoTipoGastoOfGastoListGasto != null) {
                    oldCodigoTipoGastoOfGastoListGasto.getGastoList().remove(gastoListGasto);
                    oldCodigoTipoGastoOfGastoListGasto = em.merge(oldCodigoTipoGastoOfGastoListGasto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoGasto tipoGasto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoGasto persistentTipoGasto = em.find(TipoGasto.class, tipoGasto.getCodigoTipoGasto());
            List<Gasto> gastoListOld = persistentTipoGasto.getGastoList();
            List<Gasto> gastoListNew = tipoGasto.getGastoList();
            List<String> illegalOrphanMessages = null;
            for (Gasto gastoListOldGasto : gastoListOld) {
                if (!gastoListNew.contains(gastoListOldGasto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gasto " + gastoListOldGasto + " since its codigoTipoGasto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Gasto> attachedGastoListNew = new ArrayList<Gasto>();
            for (Gasto gastoListNewGastoToAttach : gastoListNew) {
                gastoListNewGastoToAttach = em.getReference(gastoListNewGastoToAttach.getClass(), gastoListNewGastoToAttach.getCodigoGasto());
                attachedGastoListNew.add(gastoListNewGastoToAttach);
            }
            gastoListNew = attachedGastoListNew;
            tipoGasto.setGastoList(gastoListNew);
            tipoGasto = em.merge(tipoGasto);
            for (Gasto gastoListNewGasto : gastoListNew) {
                if (!gastoListOld.contains(gastoListNewGasto)) {
                    TipoGasto oldCodigoTipoGastoOfGastoListNewGasto = gastoListNewGasto.getCodigoTipoGasto();
                    gastoListNewGasto.setCodigoTipoGasto(tipoGasto);
                    gastoListNewGasto = em.merge(gastoListNewGasto);
                    if (oldCodigoTipoGastoOfGastoListNewGasto != null && !oldCodigoTipoGastoOfGastoListNewGasto.equals(tipoGasto)) {
                        oldCodigoTipoGastoOfGastoListNewGasto.getGastoList().remove(gastoListNewGasto);
                        oldCodigoTipoGastoOfGastoListNewGasto = em.merge(oldCodigoTipoGastoOfGastoListNewGasto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoGasto.getCodigoTipoGasto();
                if (findTipoGasto(id) == null) {
                    throw new NonexistentEntityException("The tipoGasto with id " + id + " no longer exists.");
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
            TipoGasto tipoGasto;
            try {
                tipoGasto = em.getReference(TipoGasto.class, id);
                tipoGasto.getCodigoTipoGasto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoGasto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Gasto> gastoListOrphanCheck = tipoGasto.getGastoList();
            for (Gasto gastoListOrphanCheckGasto : gastoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoGasto (" + tipoGasto + ") cannot be destroyed since the Gasto " + gastoListOrphanCheckGasto + " in its gastoList field has a non-nullable codigoTipoGasto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoGasto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoGasto> findTipoGastoEntities() {
        return findTipoGastoEntities(true, -1, -1);
    }

    public List<TipoGasto> findTipoGastoEntities(int maxResults, int firstResult) {
        return findTipoGastoEntities(false, maxResults, firstResult);
    }

    private List<TipoGasto> findTipoGastoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoGasto.class));
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

    public TipoGasto findTipoGasto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoGasto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoGastoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoGasto> rt = cq.from(TipoGasto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public TipoGasto consultarxNombre(String nombre) {
        try {
            Query query = getEntityManager().createQuery("select t from TipoGasto t where t.nombreTipoGasto = :nombreTipoGasto")
                    .setParameter("nombreTipoGasto", nombre);
            return (TipoGasto) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
