package tech.lapsa.insurance.dao.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.Request_;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.domain.crm.UserLogin;
import com.lapsa.insurance.domain.crm.User_;

import tech.lapsa.insurance.dao.UserDAO;
import tech.lapsa.insurance.dao.UserDAO.UserDAOLocal;
import tech.lapsa.insurance.dao.UserDAO.UserDAORemote;
import tech.lapsa.insurance.dao.UserLoginDAO.UserLoginDAOLocal;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.patterns.dao.NotFound;

@Stateless(name = UserDAO.BEAN_NAME)
@Interceptors(LoggingInterceptor.class)
public class UserDAOBean
	extends ABaseDAO<User, Integer>
	implements UserDAOLocal, UserDAORemote {

    public UserDAOBean() {
	super(User.class);
    }

    @EJB
    private UserLoginDAOLocal userLoginDAO;

    //

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User getByLogin(final String login) throws IllegalArgument, NotFound {
	return _getByLogin(login);
    }

    private User _getByLogin(final String login) throws IllegalArgument, NotFound {
	MyStrings.requireNonEmpty(IllegalArgument::new, login, "login");
	final UserLogin userLogin = userLoginDAO.getByName(login);
	return userLogin.getUser();
    }

    //

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<User> findAll() {
	return _findAll();
    }

    private List<User> _findAll() {
	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<User> cq = cb.createQuery(entityClass);
	final Root<User> root = cq.from(entityClass);
	cq.select(root);
	final TypedQuery<User> q = em.createQuery(cq);
	return q.getResultList();
    }

    //

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<User> findVisible() {
	return _findVisible();
    }

    private List<User> _findVisible() {
	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<User> cq = cb.createQuery(entityClass);
	final Root<User> root = cq.from(entityClass);
	cq.select(root).where(cb.isFalse(root.get(User_.hidden)));
	final TypedQuery<User> q = em.createQuery(cq);
	return q.getResultList();
    }

    //

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Deprecated
    public List<User> findAllWhoCreatedRequest() {
	return _findAllWhoEverCreatedRequest();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<User> findAllWhoEverCreatedRequest() {
	return _findAllWhoEverCreatedRequest();
    }

    private List<User> _findAllWhoEverCreatedRequest() {
	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<User> cq = cb.createQuery(entityClass);
	final Root<Request> root = cq.from(Request.class);
	cq.select(root.get(Request_.createdBy)) //
		.groupBy(root.get(Request_.createdBy)) //
	// not used because it is a long request for MySql. Java Stream API used
	// to make it distinct
	// .distinct(true) //
	;
	final TypedQuery<User> q = em.createQuery(cq);
	final List<User> r = q.getResultList();
	return r.stream() //
		.distinct()
		.collect(MyCollectors.unmodifiableList());
    }

    //

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<User> findAllWhoEverPickedRequest() {
	return _findAllWhoEverAcceptRequest();
    }

    private List<User> _findAllWhoEverAcceptRequest() {
	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<User> cq = cb.createQuery(entityClass);
	final Root<Request> root = cq.from(Request.class);
	cq.select(root.get(Request_.pickedBy)) //
		.groupBy(root.get(Request_.pickedBy))
	// not used because it is a long request for MySql. Java Stream API used
	// to make it distinct
	// .distinct(true) //
	;
	final TypedQuery<User> q = em.createQuery(cq);
	final List<User> r = q.getResultList();
	return r.stream() //
		.distinct()
		.collect(MyCollectors.unmodifiableList());
    }

    //

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<User> findAllWhoEverCompleteRequest() {
	return _findAllWhoEverCompleteRequest();
    }

    private List<User> _findAllWhoEverCompleteRequest() {
	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<User> cq = cb.createQuery(entityClass);
	final Root<Request> root = cq.from(Request.class);
	cq.select(root.get(Request_.completedBy)) //
		.groupBy(root.get(Request_.completedBy)) //
	// not used because it is a long request for MySql. Java Stream API used
	// to make it distinct
	// .distinct(true) //
	;
	final TypedQuery<User> q = em.createQuery(cq);
	final List<User> r = q.getResultList();
	return r.stream() //
		.distinct()
		.collect(MyCollectors.unmodifiableList());
    }

    //

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<User> findAllWithNoGroup() {
	return _findAllWithNoGroup();
    }

    private List<User> _findAllWithNoGroup() {
	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<User> cq = cb.createQuery(entityClass);
	final Root<User> root = cq.from(entityClass);
	cq.select(root).where(cb.isEmpty(root.get(User_.groups)));
	final TypedQuery<User> q = em.createQuery(cq);
	return q.getResultList();
    }
}
