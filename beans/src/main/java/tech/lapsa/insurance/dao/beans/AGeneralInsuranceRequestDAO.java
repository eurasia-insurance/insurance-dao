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
    public List<T> findByAgreementNumber(final String agreementNumber) throws IllegalArgument {
	try {
	    return findByAgreementNumberQuery(agreementNumber)
		    .getResultList();
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findByAgreementNumber(final int from, final int limit, final String agreementNumber)
	    throws IllegalArgument {
	try {
	    return _requireLimitedQuery(from, limit, findByAgreementNumberQuery(agreementNumber))
		    .getResultList();
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgument(e);
	}
    }

    private TypedQuery<T> findByAgreementNumberQuery(final String agreementNumber) throws IllegalArgumentException {
	MyStrings.requireNonEmpty(agreementNumber, "agreementNumber");
	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<T> cq = cb.createQuery(entityClass);
	final Root<T> root = cq.from(entityClass);
	cq.select(root)
		.where(cb.equal(root.get(InsuranceRequest_.agreementNumber), agreementNumber));
	final TypedQuery<T> q = em.createQuery(cq);
	return q;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findByPaymentInvoiceNumber(final String invoiceNumber) throws IllegalArgument {
	return _requireUnlimitedQuery(findByPaymentInvoiceNumberQuery(invoiceNumber)).getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findByPaymentInvoiceNumber(final int from, final int limit, final String invoiceNumber)
	    throws IllegalArgument {
	return _requireLimitedQuery(from, limit, findByPaymentInvoiceNumberQuery(invoiceNumber)).getResultList();
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

	// payment invoice number
	filter.optionalPaymentInvoiceNumber() //
		.map(x -> cb.equal(root.get(InsuranceRequest_.payment).get(PaymentData_.invoiceNumber), x)) //
		.ifPresent(whereOptions::add);

	// payment reference
	filter.optPaymentReference() //
		.map(x -> cb.equal(root.get(InsuranceRequest_.payment).get(PaymentData_.reference), x)) //
		.ifPresent(whereOptions::add);

	// payment method name
	Predictates.textMatches(cb, root.get(InsuranceRequest_.payment).get(PaymentData_.methodName),
		filter.getPaymentMethodNameMask()) //
		.ifPresent(whereOptions::add);

	// payment card
	Predictates.textMatches(cb, root.get(InsuranceRequest_.payment).get(PaymentData_.card),
		filter.getPaymentCard()) //
		.ifPresent(whereOptions::add);

	// payment card bank
	filter.optPaymentCardBank() //
		.map(x -> cb.equal(root.get(InsuranceRequest_.payment).get(PaymentData_.cardBank), x)) //
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

    protected TypedQuery<T> findByPaymentInvoiceNumberQuery(final String invoiceNumber) throws IllegalArgument {
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
