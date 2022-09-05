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
import modelo.AuditoriaPago;

/**
 *
 * @author usuario
 */
public class AuditoriaPagoJpaController implements Serializable {

    public AuditoriaPagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public AuditoriaPagoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("EgoPU");
    }

    public void create(AuditoriaPago auditoriaPago) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(auditoriaPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AuditoriaPago auditoriaPago) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            auditoriaPago = em.merge(auditoriaPago);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = auditoriaPago.getCodigoAuditoria();
                if (findAuditoriaPago(id) == null) {
                    throw new NonexistentEntityException("The auditoriaPago with id " + id + " no longer exists.");
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
            AuditoriaPago auditoriaPago;
            try {
                auditoriaPago = em.getReference(AuditoriaPago.class, id);
                auditoriaPago.getCodigoAuditoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The auditoriaPago with id " + id + " no longer exists.", enfe);
            }
            em.remove(auditoriaPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AuditoriaPago> findAuditoriaPagoEntities() {
        return findAuditoriaPagoEntities(true, -1, -1);
    }

    public List<AuditoriaPago> findAuditoriaPagoEntities(int maxResults, int firstResult) {
        return findAuditoriaPagoEntities(false, maxResults, firstResult);
    }

    private List<AuditoriaPago> findAuditoriaPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AuditoriaPago.class));
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

    public AuditoriaPago findAuditoriaPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AuditoriaPago.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuditoriaPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AuditoriaPago> rt = cq.from(AuditoriaPago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
