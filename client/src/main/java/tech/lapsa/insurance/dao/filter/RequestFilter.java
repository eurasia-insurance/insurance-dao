package tech.lapsa.insurance.dao.filter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.elements.InsuranceRequestType;
import com.lapsa.insurance.elements.ObtainingMethod;
import com.lapsa.insurance.elements.ObtainingStatus;
import com.lapsa.insurance.elements.PaymentMethod;
import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.ProgressStatus;
import com.lapsa.insurance.elements.RequestSource;
import com.lapsa.insurance.elements.RequestStatus;
import com.lapsa.insurance.elements.TransactionProblem;
import com.lapsa.insurance.elements.TransactionStatus;

import tech.lapsa.java.commons.function.MyOptionals;

public interface RequestFilter {

    // Request properties

    Integer getId();

    String getRequesterNameMask();

    String getRequesterIdNumberMask();

    RequestSource getRequestSource();

    default Optional<RequestSource> optionalRequestSource() {
	return MyOptionals.of(getRequestSource());
    }

    RequestStatus getRequestStatus();

    ProgressStatus getProgressStatus();

    LocalDateTime getCreatedAfter();

    LocalDateTime getCreatedBefore();

    LocalDateTime getCompletedAfter();

    LocalDateTime getCompletedBefore();

    User getCreatedBy();

    User getAcceptedBy();

    User getCompletedBy();

    User getClosedBy();

    // InsuranceRequest properties

    InsuranceRequestType getRequestType();

    default Optional<InsuranceRequestType> optionalRequestType() {
	return MyOptionals.of(getRequestType());
    }

    PaymentMethod getPaymentMethod();

    default Optional<PaymentMethod> optionalPaymentMethod() {
	return MyOptionals.of(getPaymentMethod());
    }

    PaymentStatus getPaymentStatus();

    default Optional<PaymentStatus> optionalPaymentStatus() {
	return MyOptionals.of(getPaymentStatus());
    }

    ObtainingMethod getObtainingMethod();

    default Optional<ObtainingMethod> optionalObtainingMethod() {
	return MyOptionals.of(getObtainingMethod());
    }

    ObtainingStatus getObtainingStatus();

    default Optional<ObtainingStatus> optionalObtainingStatus() {
	return MyOptionals.of(getObtainingStatus());
    }

    TransactionStatus getTransactionStatus();

    default Optional<TransactionStatus> optionalTransactionStatus() {
	return MyOptionals.of(getTransactionStatus());
    }

    String getAgreementNumberMask();

    TransactionProblem getTransactionProblem();

    default Optional<TransactionProblem> optionalTransactionProblem() {
	return MyOptionals.of(getTransactionProblem());
    }

    ZoneId getZoneId();
}
