package tech.lapsa.insurance.dao.beans;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.lapsa.insurance.jpa.InsuranceConstants;
import com.lapsa.insurance.jpa.InsuranceVersion;

import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.javax.jpa.commons.JPAConstants;

@Stateless
public class EntityManagerControlBean {

    @PersistenceContext(unitName = InsuranceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void flushAndClear() {
	em.flush();
	em.getEntityManagerFactory().getCache().evictAll();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ping() throws IllegalState {
	try {
	    em.find(InsuranceVersion.class, 1, JPAConstants.NO_CACHE_PROPERTIES);
	} catch (RuntimeException e) {
	    throw MyExceptions.format(IllegalState::new,
		    "Illegal stae of persistence layer. %1$s throwed with message %2$s", e.getClass(), e.getMessage());
	}
    }
}
