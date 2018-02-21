package tech.lapsa.insurance.dao.beans;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.lapsa.insurance.jpa.InsuranceConstants;

@Stateless
public class EntityManagerControlBean {

    @PersistenceContext(unitName = InsuranceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void flushAndClear() {
	em.flush();
	em.getEntityManagerFactory().getCache().evictAll();
    }
}
