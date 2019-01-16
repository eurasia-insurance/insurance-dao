package tech.lapsa.insurance.dao.beans;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.lapsa.insurance.domain.EntitySuperclass;
import com.lapsa.insurance.jpa.InsuranceConstants;

import tech.lapsa.patterns.dao.beans.AGeneralDAO;

public abstract class ABaseDAO<T extends EntitySuperclass>
	extends AGeneralDAO<T, Integer> {

    protected ABaseDAO(final Class<T> entityClazz) {
	super(entityClazz);
    }

    @PersistenceContext(unitName = InsuranceConstants.PERSISTENCE_UNIT_NAME)
    protected EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
	return em;
    }

    @Override
    protected <ET extends T> void beforeSave(ET entity) {
	entity.touchUpdated();
    }

}
