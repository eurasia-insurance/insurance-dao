package tech.lapsa.insurance.dao.beans;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.InsuranceRequest_;
import com.lapsa.insurance.domain.ObtainingData_;
import com.lapsa.insurance.domain.PaymentData_;

import tech.lapsa.insurance.dao.GeneralInsuranceRequestDAO.GeneralInsuranceRequestDAOLocal;
import tech.lapsa.insurance.dao.GeneralInsuranceRequestDAO.GeneralInsuranceRequestDAORemote;
import tech.lapsa.insurance.dao.RequestFilter;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.patterns.dao.beans.Predictates;

public abstract class AGeneralInsuranceRequestDAO<T extends InsuranceRequest>
	extends AGeneralRequestDAO<T>
	implements GeneralInsuranceRequestDAOLocal<T>, GeneralInsuranceRequestDAORemote<T> {

    public AGeneralInsuranceRequestDAO(final Class<T> entityClass) {
	super(entityClass);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findByPaymentInvoiceNumber(final String invoiceNumber) throws IllegalArgument {
	return _requireUnlimitedQuery(_findByPaymentInvoiceNumber(invoiceNumber)).getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findByPaymentInvoiceNumber(final int from, final int limit, final String invoiceNumber)
	    throws IllegalArgument {
	return _requireLimitedQuery(from, limit, _findByPaymentInvoiceNumber(invoiceNumber)).getResultList();
    }

    // preparer

    @Override
    protected void prepareRequestFilterPredictates(final RequestFilter filter, final CriteriaBuilder cb,
	    final Root<T> root,
	    final List<Predicate> whereOptions) {
	super.prepareRequestFilterPredictates(filter, cb, root, whereOptions);

	// request type
	filter.optionalRequestType() //
		.map(x -> cb.equal(root.get(InsuranceRequest_.type), x)) //
		.ifPresent(whereOptions::add);

	// payment status
	filter.optionalPaymentStatus() //
		.map(x -> cb.equal(root.get(InsuranceRequest_.payment).get(PaymentData_.status), x)) //
		.ifPresent(whereOptions::add);

	// payment external Id
	filter.optionalPaymentInvoiceNumber() //
		.map(x -> cb.equal(root.get(InsuranceRequest_.payment).get(PaymentData_.invoiceNumber), x)) //
		.ifPresent(whereOptions::add);

	// obtaining method
	filter.optionalObtainingMethod() //
		.map(x -> cb.equal(root.get(InsuranceRequest_.obtaining).get(ObtainingData_.method), x)) //
		.ifPresent(whereOptions::add);

	// obtaining status
	filter.optionalObtainingStatus() //
		.map(x -> cb.equal(root.get(InsuranceRequest_.obtaining).get(ObtainingData_.status), x)) //
		.ifPresent(whereOptions::add);

	// transaction status
	filter.optionalTransactionStatus() //
		.map(x -> cb.equal(root.get(InsuranceRequest_.transactionStatus), x)) //
		.ifPresent(whereOptions::add);

	// agreement number mask
	Predictates.textMatches(cb, root.get(InsuranceRequest_.agreementNumber),
		filter.getAgreementNumberMask()) //
		.ifPresent(whereOptions::add);

	// transaction problem
	filter.optionalTransactionProblem() //
		.map(x -> cb.equal(root.get(InsuranceRequest_.transactionProblem), x)) //
		.ifPresent(whereOptions::add);
    }

    // queries

    protected TypedQuery<T> _findByPaymentInvoiceNumber(final String invoiceNumber) throws IllegalArgument {
	MyStrings.requireNonEmpty(IllegalArgument::new, invoiceNumber, "invoiceNumber");
	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<T> cq = cb.createQuery(entityClass);
	final Root<T> root = cq.from(entityClass);
	cq.select(root)
		.where(cb.equal(root.get(InsuranceRequest_.payment)
			.get(PaymentData_.invoiceNumber), invoiceNumber));

	final TypedQuery<T> q = em.createQuery(cq);
	return q;
    }
}
