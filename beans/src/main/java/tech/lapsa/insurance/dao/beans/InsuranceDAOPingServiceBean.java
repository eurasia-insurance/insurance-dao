package tech.lapsa.insurance.dao.beans;

import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
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
import tech.lapsa.java.commons.function.MyMaps;

@Stateless(name = InsuranceDAOPingService.BEAN_NAME)
@Interceptors(LoggingInterceptor.class)
public class InsuranceDAOPingServiceBean implements InsuranceDAOPingServiceLocal, InsuranceDAOPingServiceRemote {

    @PersistenceContext(unitName = InsuranceConstants.PERSISTENCE_UNIT_NAME)
    protected EntityManager em;

    //TODO REFACT Use constants from jpa-commons
    private static final String HINT_JAVAX_PERSISTENCE_CACHE_STORE_MODE = "javax.persistence.cache.storeMode";
    private static final String HINT_JAVAX_PERSISTENCE_CACHE_RETREIVE_MODE = "javax.persistence.cache.retreiveMode";

    private static final Map<String, Object> NO_CACHE_PROPS = MyMaps.of( //
	    HINT_JAVAX_PERSISTENCE_CACHE_RETREIVE_MODE, CacheRetrieveMode.BYPASS, //
	    HINT_JAVAX_PERSISTENCE_CACHE_STORE_MODE, CacheStoreMode.REFRESH);

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ping() throws IllegalState {
	try {
	    em.find(InsuranceVersion.class, 1, NO_CACHE_PROPS);
	} catch (Exception e) {
	    throw MyExceptions.illegalStateFormat(e.getMessage());
	}
    }

}
