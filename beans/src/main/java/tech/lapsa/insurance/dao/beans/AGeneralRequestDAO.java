package tech.lapsa.insurance.dao.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.ejb.EJBException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.lapsa.insurance.domain.EntitySuperclass_;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.Request_;
import com.lapsa.insurance.domain.RequesterData_;
import com.lapsa.insurance.domain.crm.User;

import tech.lapsa.insurance.dao.GeneralRequestDAO.GeneralRequestDAOLocal;
import tech.lapsa.insurance.dao.GeneralRequestDAO.GeneralRequestDAORemote;
import tech.lapsa.insurance.dao.ListWithStats;
import tech.lapsa.insurance.dao.RequestFilter;
import tech.lapsa.insurance.dao.RequestSort;
import tech.lapsa.insurance.dao.RequestSort.RequestSortField;
import tech.lapsa.insurance.dao.RequestSort.RequestSortOrder;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.patterns.dao.beans.Predictates;

public abstract class AGeneralRequestDAO<T extends Request>
	extends ABaseDAO<T, Integer>
	implements GeneralRequestDAOLocal<T>, GeneralRequestDAORemote<T> {

    public AGeneralRequestDAO(final Class<T> entityClass) {
	super(entityClass);
    }

    // byStatus

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findByArchived(final boolean archived) throws IllegalArgument {
	try {
	    return _findByArchived(archived);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Long countByArchived(final boolean archived) throws IllegalArgument {
	try {
	    return _countByArchived(archived);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findByArchived(int from, int limit, final boolean archived) throws IllegalArgument {
	try {
	    return _findByArchived(from, limit, archived);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    // byFilter

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findByFilter(final RequestFilter filter,
	    final RequestSort sort,
	    final boolean showNoCreators, final User... onlyCreators)
	    throws IllegalArgument {
	try {
	    return _findByFilter(filter, sort, showNoCreators, onlyCreators);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findByFilter(final int from,
	    final int limit, final RequestFilter filter,
	    final RequestSort sort,
	    final boolean showNoCreators,
	    final User... onlyCreators)
	    throws IllegalArgument {
	try {
	    return _findByFilter(from, limit, filter, sort, showNoCreators, onlyCreators);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ListWithStats<T> findByFilterWithStats(final int from,
	    final int limit,
	    final RequestFilter filter,
	    final RequestSort sort,
	    final boolean showNoCreators,
	    final User... onlyCreators)
	    throws IllegalArgument {
	try {
	    return _findByFilterWithStats(from, limit, filter, sort, showNoCreators, onlyCreators);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Long countByFilter(RequestFilter filter, boolean showNoCreators, User... onlyCreators)
	    throws IllegalArgument {
	try {
	    return _countByFilter(filter, showNoCreators, onlyCreators);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}

    }

    private static final boolean DEFAULT_SHOW_NO_CREATORS = true;
    private static final RequestSortField DEFAULT_SORT_FIELD = RequestSortField.CREATED;
    private static final RequestSortOrder DEFAULT_SORT_ORDER = RequestSortOrder.DESC;
    private static final RequestSort DEFAULT_SORT = new RequestSort(DEFAULT_SORT_FIELD, DEFAULT_SORT_ORDER);

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findByFilter(final RequestFilter filter) throws IllegalArgument {
	try {
	    return _findByFilter(filter, DEFAULT_SORT, DEFAULT_SHOW_NO_CREATORS);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findByFilter(int from, int limit, final RequestFilter filter) throws IllegalArgument {
	try {
	    return _findByFilter(from, limit, filter, DEFAULT_SORT, DEFAULT_SHOW_NO_CREATORS);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ListWithStats<T> findByFilterWithStats(final int from,
	    final int limit,
	    final RequestFilter filter,
	    final RequestSort sort)
	    throws IllegalArgument {
	try {
	    return _findByFilterWithStats(from, limit, filter, sort, DEFAULT_SHOW_NO_CREATORS);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Long countByFilter(RequestFilter filter)
	    throws IllegalArgument {
	try {
	    return _countByFilter(filter, DEFAULT_SHOW_NO_CREATORS);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}

    }

    // allOpen

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findAllInbox() {
	return _findAllOpen();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findAllInbox(int from, int limit) throws IllegalArgument {
	try {
	    return _findAllInbox(from, limit);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ListWithStats<T> findAllOpenWithStats(int from, int limit) throws IllegalArgument {
	try {
	    return _findAllOpenWithStats(from, limit);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Long countAllInbox() {
	return _countAllInbox();
    }

    // all

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findAll() {
	return _findAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findAll(int from, int limit) throws IllegalArgument {
	try {
	    return _findAll(from, limit);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ListWithStats<T> findAllWithStats(int from, int limit) throws IllegalArgument {
	try {
	    return _findAllWithStats(from, limit);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Long countAll() {
	return _countAll();
    }

    // preparer

    protected void prepareRequestFilterPredictates(final RequestFilter filter, final CriteriaBuilder cb,
	    final Root<T> root, final List<Predicate> whereOptions) {

	// request id
	if (filter.getId() != null && filter.getId() > 0)
	    whereOptions.add(
		    cb.equal(root.get(EntitySuperclass_.id), filter.getId()));

	// requester name mask
	Predictates.textMatches(cb, root.get(Request_.requester).get(RequesterData_.name),
		filter.getRequesterNameMask()) //
		.ifPresent(whereOptions::add);

	// requester taxpayer number
	filter.optionalRequesterTaxpayerNumber() //
		.map(x -> cb.equal(root.get(Request_.requester).get(RequesterData_.idNumber), x)) //
		.ifPresent(whereOptions::add);

	// archived
	if (filter.getArchived() != null)
	    whereOptions.add(cb.equal(root.get(Request_.archived), filter.getArchived().booleanValue()));

	// progress status
	if (filter.getProgressStatus() != null)
	    whereOptions.add(cb.equal(root.get(Request_.progressStatus), filter.getProgressStatus()));

	if (filter.getZoneId() != null) {

	    // created after
	    if (filter.getCreatedAfter() != null)
		whereOptions
			.add(cb.greaterThanOrEqualTo(root.get(Request_.created),
				filter.getCreatedAfter().atZone(filter.getZoneId()).toInstant()));

	    // created before
	    if (filter.getCreatedBefore() != null)
		whereOptions.add(cb.lessThanOrEqualTo(root.get(Request_.created),
			filter.getCreatedBefore().atZone(filter.getZoneId()).toInstant()));

	    // completed after
	    if (filter.getCompletedAfter() != null)
		whereOptions.add(
			cb.greaterThanOrEqualTo(root.get(Request_.completed),
				filter.getCompletedAfter().atZone(filter.getZoneId()).toInstant()));

	    // completed before
	    if (filter.getCompletedBefore() != null)
		whereOptions
			.add(cb.lessThanOrEqualTo(root.get(Request_.completed),
				filter.getCompletedBefore().atZone(filter.getZoneId()).toInstant()));

	}

	// created by
	if (filter.getCreatedBy() != null)
	    whereOptions
		    .add(cb.equal(root.get(Request_.createdBy), filter.getCreatedBy()));

	// picked by
	if (filter.getPickedBy() != null)
	    whereOptions
		    .add(cb.equal(root.get(Request_.pickedBy), filter.getPickedBy()));

	// completed by
	if (filter.getCompletedBy() != null)
	    whereOptions
		    .add(cb.equal(root.get(Request_.completedBy), filter.getCompletedBy()));
    }

    // byStatus

    private List<T> _findByArchived(final boolean archived) {

	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<T> cq = cb.createQuery(entityClass);
	final Root<T> root = cq.from(entityClass);

	_whereCriteriaByStatus(cb, root, cq, archived);

	final TypedQuery<T> q = em.createQuery(cq.select(root));

	return _requireUnlimitedQuery(q)
		.getResultList();
    }

    private Long _countByArchived(final boolean archived) {

	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
	final Root<T> root = cq.from(entityClass);

	_whereCriteriaByStatus(cb, root, cq, archived);

	final TypedQuery<Long> q = em.createQuery(cq.select(cb.count(root)));

	return q.getSingleResult();
    }

    private List<T> _findByArchived(int from, int limit, final boolean archived) {

	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<T> cq = cb.createQuery(entityClass);
	final Root<T> root = cq.from(entityClass);

	_whereCriteriaByStatus(cb, root, cq, archived);

	final TypedQuery<T> q = em.createQuery(cq.select(root));

	return _requireLimitedQuery(from, limit, q)
		.getResultList();
    }

    // byFilter

    private List<T> _findByFilter(final RequestFilter filter,
	    final RequestSort sort,
	    final boolean showNoCreators,
	    final User... onlyCreators)
	    throws IllegalArgumentException {

	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<T> cq = cb.createQuery(entityClass);
	final Root<T> root = cq.from(entityClass);

	_whereCriteriaByFilter(cb, root, cq, filter, showNoCreators, onlyCreators);
	_sortingCriteria(cb, root, cq, sort);

	final TypedQuery<T> q = em.createQuery(cq.select(root));

	return _requireUnlimitedQuery(q)
		.getResultList();
    }

    private List<T> _findByFilter(final int from,
	    final int limit,
	    final RequestFilter filter,
	    final RequestSort sort,
	    final boolean showNoCreators,
	    final User... onlyCreators) {

	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<T> cq = cb.createQuery(entityClass);
	final Root<T> root = cq.from(entityClass);

	_whereCriteriaByFilter(cb, root, cq, filter, showNoCreators, onlyCreators);
	_sortingCriteria(cb, root, cq, sort);

	final TypedQuery<T> q = em.createQuery(cq.select(root));

	return _requireLimitedQuery(from, limit, q)
		.getResultList();
    }

    private ListWithStats<T> _findByFilterWithStats(final int from,
	    final int limit,
	    final RequestFilter filter,
	    final RequestSort sort,
	    final boolean showNoCreators,
	    final User... onlyCreators) {

	final Long count = _countByFilter(filter, showNoCreators, onlyCreators);
	final List<T> list = _findByFilter(from, limit, filter, sort, showNoCreators, onlyCreators);

	return new ListWithStats<T>(list, from, limit, count);
    }

    private Long _countByFilter(final RequestFilter filter,
	    final boolean showNoCreators,
	    final User... onlyCreators) {

	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
	final Root<T> root = cq.from(entityClass);

	_whereCriteriaByFilter(cb, root, cq, filter, showNoCreators, onlyCreators);

	final TypedQuery<Long> q = em.createQuery(cq.select(cb.count(root)));

	return q.getSingleResult();
    }

    // allOpen

    private List<T> _findAllOpen() {

	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<T> cq = cb.createQuery(entityClass);
	final Root<T> root = cq.from(entityClass);

	_whereCriteriaAllOpen(cb, root, cq);

	final TypedQuery<T> q = em.createQuery(cq.select(root));

	return _requireUnlimitedQuery(q)
		.getResultList();
    }

    private List<T> _findAllInbox(final int from, final int limit) {

	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<T> cq = cb.createQuery(entityClass);
	final Root<T> root = cq.from(entityClass);

	_whereCriteriaAllOpen(cb, root, cq);

	final TypedQuery<T> q = em.createQuery(cq.select(root));

	return _requireLimitedQuery(from, limit, q)
		.getResultList();
    }

    private ListWithStats<T> _findAllOpenWithStats(final int from, final int limit) {

	final Long count = _countAllInbox();
	final List<T> list = _findAllInbox(from, limit);

	return new ListWithStats<T>(list, from, limit, count);
    }

    private Long _countAllInbox() {

	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
	final Root<T> root = cq.from(entityClass);

	_whereCriteriaAllOpen(cb, root, cq);

	final TypedQuery<Long> q = em.createQuery(cq.select(cb.count(root)));

	return q.getSingleResult();
    }

    // all

    private List<T> _findAll() {

	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<T> cq = cb.createQuery(entityClass);
	final Root<T> root = cq.from(entityClass);

	_whereCriteriaAll(cb, root, cq);

	final TypedQuery<T> q = em.createQuery(cq.select(root));

	return _requireUnlimitedQuery(q)
		.getResultList();
    }

    private List<T> _findAll(int from, int limit) {

	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<T> cq = cb.createQuery(entityClass);
	final Root<T> root = cq.from(entityClass);

	_whereCriteriaAll(cb, root, cq);

	final TypedQuery<T> q = em.createQuery(cq.select(root));

	return _requireLimitedQuery(from, limit, q)
		.getResultList();
    }

    private ListWithStats<T> _findAllWithStats(int from, int limit) {

	final Long count = _countAll();
	final List<T> list = _findAll(from, limit);

	return new ListWithStats<T>(list, from, limit, count);
    }

    private Long _countAll() {

	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
	final Root<T> root = cq.from(entityClass);

	_whereCriteriaAll(cb, root, cq);

	final TypedQuery<Long> q = em.createQuery(cq.select(cb.count(root)));

	return q.getSingleResult();
    }

    // criterias

    private <O> CriteriaQuery<O> _sortingCriteria(final CriteriaBuilder cb,
	    final Root<T> root,
	    final CriteriaQuery<O> cq,
	    final RequestSort sort) throws IllegalArgumentException {
	final Function<Expression<?>, Order> orderFunc = sortFunc(cb, sort);
	final Expression<?> orderExpression = sortExpression(root, sort);
	return cq.orderBy(orderFunc.apply(orderExpression));
    }

    private Function<Expression<?>, Order> sortFunc(final CriteriaBuilder cb, final RequestSort sort)
	    throws IllegalArgumentException {
	MyObjects.requireNonNull(sort, "sort");
	switch (sort.getOrder()) {
	case ASC:
	    return cb::asc;
	case DESC:
	    return cb::desc;
	}
	throw MyExceptions.illegalArgumentFormat("Invalid sort order %1$s", sort.getOrder());
    }

    private Expression<?> sortExpression(final Root<T> root, final RequestSort sort) throws IllegalArgumentException {
	MyObjects.requireNonNull(sort, "sort");
	switch (sort.getField()) {
	case CREATED:
	    return root.get(Request_.created);
	case ID:
	    return root.get(Request_.id);
	case REQUESTER_NAME:
	    return root.get(Request_.requester).get(RequesterData_.name);
	case UPDATED:
	    return root.get(Request_.updated);
	case REQUESTER_ID_NUMBER:
	    return root.get(Request_.requester).get(RequesterData_.idNumber);
	case REQUESTER_PHONE:
	    return root.get(Request_.requester).get(RequesterData_.phone);
	}
	throw MyExceptions.illegalArgumentFormat("Invalid sort field %1$s", sort.getField());
    }

    private <O> CriteriaQuery<O> _whereCriteriaAllOpen(final CriteriaBuilder cb,
	    final Root<T> root,
	    final CriteriaQuery<O> cq) {
	try {
	    return _whereCriteriaByStatus(cb, root, cq, false);
	} catch (IllegalArgumentException e) {
	    // it should not happens
	    throw new EJBException(e.getMessage());
	}
    }

    private <O> CriteriaQuery<O> _whereCriteriaAll(CriteriaBuilder cb, Root<T> root, CriteriaQuery<O> cq) {
	return cq;
    }

    private <O> CriteriaQuery<O> _whereCriteriaByStatus(final CriteriaBuilder cb,
	    final Root<T> root,
	    final CriteriaQuery<O> cq,
	    final boolean archived) throws IllegalArgumentException {
	return cq //
		.where(cb.equal(root.get(Request_.archived), archived));
    }

    private <O> CriteriaQuery<O> _whereCriteriaByFilter(final CriteriaBuilder cb,
	    final Root<T> root,
	    final CriteriaQuery<O> cq,
	    final RequestFilter filter,
	    final boolean showNoCreators,
	    final User... onlyCreators) throws IllegalArgumentException {
	MyObjects.requireNonNull(filter, "filter");

	final List<Predicate> whereOptions = new ArrayList<>();

	prepareRequestFilterPredictates(filter, cb, root, whereOptions);

	if (onlyCreators != null && onlyCreators.length > 0) {
	    final List<Predicate> creatorsRestriction = new ArrayList<>();
	    if (showNoCreators)
		creatorsRestriction.add(cb.isNull(root.get(Request_.createdBy)));
	    for (final User u : onlyCreators)
		creatorsRestriction.add(cb.equal(root.get(Request_.createdBy), u));
	    whereOptions.add(cb.or(creatorsRestriction.toArray(new Predicate[0])));
	}

	return cq //
		.where(cb.and(whereOptions.toArray(new Predicate[0]))); //
    }

    // limits

    protected <O> TypedQuery<O> _requireUnlimitedQuery(final TypedQuery<O> query) {
	return query.setFirstResult(0).setMaxResults(Integer.MAX_VALUE);
    }

    protected <O> TypedQuery<O> _requireLimitedQuery(final int from, final int limit, final TypedQuery<O> query)
	    throws IllegalArgumentException {
	if (from < 0)
	    throw MyExceptions.illegalArgumentFormat("Invalid from value %1$s. It should be greater than zero", from);

	if (limit <= 0)
	    throw MyExceptions.illegalArgumentFormat(
		    "Invalid limit value %1$s. It should be greater than zero", limit);

	return query.setFirstResult(from).setMaxResults(limit);
    }

}
