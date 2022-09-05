/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.ControlClaves;

/**
 *
 * @author usuario
 */
public class ControlClavesJpaController implements Serializable {

    public ControlClavesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public ControlClavesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("EgoPU");
    }

    public void create(ControlClaves controlClaves) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(controlClaves);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ControlClaves controlClaves) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            controlClaves = em.merge(controlClaves);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = controlClaves.getCodigoControl();
                if (findControlClaves(id) == null) {
                    throw new NonexistentEntityException("The controlClaves with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlClaves controlClaves;
            try {
                controlClaves = em.getReference(ControlClaves.class, id);
                controlClaves.getCodigoControl();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controlClaves with id " + id + " no longer exists.", enfe);
            }
            em.remove(controlClaves);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ControlClaves> findControlClavesEntities() {
        return findControlClavesEntities(true, -1, -1);
    }

    public List<ControlClaves> findControlClavesEntities(int maxResults, int firstResult) {
        return findControlClavesEntities(false, maxResults, firstResult);
    }

    private List<ControlClaves> findControlClavesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ControlClaves.class));
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

    public ControlClaves findControlClaves(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ControlClaves.class, id);
        } finally {
            em.close();
        }
    }

    public int getControlClavesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ControlClaves> rt = cq.from(ControlClaves.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
