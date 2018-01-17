package tech.lapsa.insurance.dao.beans;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.lapsa.insurance.jpa.InsuranceConstants;
import com.lapsa.insurance.jpa.InsuranceVersion;

import tech.lapsa.insurance.dao.InsuranceDAOPingService;
import tech.lapsa.insurance.dao.InsuranceDAOPingService.InsuranceDAOPingServiceLocal;
import tech.lapsa.insurance.dao.InsuranceDAOPingService.InsuranceDAOPingServiceRemote;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.javax.jpa.commons.JPAConstants;

@Stateless(name = InsuranceDAOPingService.BEAN_NAME)
@Interceptors(LoggingInterceptor.class)
public class InsuranceDAOPingServiceBean implements InsuranceDAOPingServiceLocal, InsuranceDAOPingServiceRemote {

    @PersistenceContext(unitName = InsuranceConstants.PERSISTENCE_UNIT_NAME)
    protected EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ping() throws IllegalState {
	try {
	    em.find(InsuranceVersion.class, 1, JPAConstants.NO_CACHE_PROPERTIES);
	} catch (Exception e) {
	    throw MyExceptions.illegalStateFormat(e.getMessage());
	}
    }

}
