package tech.lapsa.insurance.dao.beans;

import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.lapsa.insurance.jpa.InsuranceConstants;
import com.lapsa.insurance.jpa.InsuranceVersion;

import tech.lapsa.java.commons.function.MyMaps;

@Stateless
public class EntityManagerControlBean {

    @PersistenceContext(unitName = InsuranceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void flushAndClear() {
	em.flush();
	em.getEntityManagerFactory().getCache().evictAll();
    }

    // TODO REFACT Use constants from jpa-commons/tech.lapsa.javax.jpa.commons.JPAConstants
    private static final String HINT_JAVAX_PERSISTENCE_CACHE_STORE_MODE = "javax.persistence.cache.storeMode";
    private static final String HINT_JAVAX_PERSISTENCE_CACHE_RETREIVE_MODE = "javax.persistence.cache.retreiveMode";

    private static final Map<String, Object> NO_CACHE_PROPS = MyMaps.of( //
	    HINT_JAVAX_PERSISTENCE_CACHE_RETREIVE_MODE, CacheRetrieveMode.BYPASS, //
	    HINT_JAVAX_PERSISTENCE_CACHE_STORE_MODE, CacheStoreMode.REFRESH);

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ping() {
	em.find(InsuranceVersion.class, 1, NO_CACHE_PROPS);
    }
}
