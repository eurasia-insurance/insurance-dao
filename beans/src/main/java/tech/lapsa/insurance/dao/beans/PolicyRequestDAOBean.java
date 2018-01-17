package tech.lapsa.insurance.dao.beans;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.lapsa.insurance.domain.policy.PolicyRequest;

import tech.lapsa.insurance.dao.PolicyRequestDAO;
import tech.lapsa.insurance.dao.PolicyRequestDAO.PolicyRequestDAOLocal;
import tech.lapsa.insurance.dao.PolicyRequestDAO.PolicyRequestDAORemote;

@Stateless(name = PolicyRequestDAO.BEAN_NAME)
@Interceptors(LoggingInterceptor.class)
public class PolicyRequestDAOBean
	extends AGeneralInsuranceRequestDAO<PolicyRequest>
	implements PolicyRequestDAOLocal, PolicyRequestDAORemote {

    public PolicyRequestDAOBean() {
	super(PolicyRequest.class);
    }
}
