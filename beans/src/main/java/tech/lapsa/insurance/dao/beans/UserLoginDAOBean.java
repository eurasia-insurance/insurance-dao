package tech.lapsa.insurance.dao.beans;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.lapsa.insurance.domain.crm.UserLogin;
import com.lapsa.insurance.domain.crm.UserLogin_;

import tech.lapsa.insurance.dao.UserLoginDAO;
import tech.lapsa.insurance.dao.UserLoginDAO.UserLoginDAOLocal;
import tech.lapsa.insurance.dao.UserLoginDAO.UserLoginDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.patterns.dao.NotFound;

@Stateless(name = UserLoginDAO.BEAN_NAME)
@Interceptors(LoggingInterceptor.class)
public class UserLoginDAOBean
	extends ABaseDAO<UserLogin, Integer>
	implements UserLoginDAOLocal, UserLoginDAORemote {

    public UserLoginDAOBean() {
	super(UserLogin.class);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public UserLogin getByName(final String name) throws IllegalArgument, NotFound {
	MyStrings.requireNonEmpty(IllegalArgument::new, name, "name");
	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<UserLogin> cq = cb.createQuery(entityClass);
	final Root<UserLogin> root = cq.from(entityClass);
	cq.select(root)
		.where(
			cb.equal(root.get(UserLogin_.name), name));
	final TypedQuery<UserLogin> q = em.createQuery(cq);
	return signleResult(q);
    }
}
