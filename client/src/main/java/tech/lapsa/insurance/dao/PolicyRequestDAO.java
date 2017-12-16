package tech.lapsa.insurance.dao;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.lapsa.insurance.domain.policy.PolicyRequest;

public interface PolicyRequestDAO extends GeneralInsuranceRequestDAO<PolicyRequest>, EJBConstants {

    @Local
    public interface PolicyRequestDAOLocal extends PolicyRequestDAO, GeneralInsuranceRequestDAOLocal<PolicyRequest> {
    }

    @Remote
    public interface PolicyRequestDAORemote extends PolicyRequestDAO, GeneralInsuranceRequestDAORemote<PolicyRequest> {
    }
}
