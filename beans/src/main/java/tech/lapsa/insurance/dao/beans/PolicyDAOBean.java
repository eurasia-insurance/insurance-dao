package tech.lapsa.insurance.dao.beans;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.lapsa.insurance.domain.policy.Policy;

import tech.lapsa.insurance.dao.PolicyDAO;
import tech.lapsa.insurance.dao.PolicyDAO.PolicyDAOLocal;
import tech.lapsa.insurance.dao.PolicyDAO.PolicyDAORemote;

@Stateless(name = PolicyDAO.BEAN_NAME)
@Interceptors(LoggingInterceptor.class)
public class PolicyDAOBean
	extends ABaseDAO<Policy>
	implements PolicyDAOLocal, PolicyDAORemote {

    public PolicyDAOBean() {
	super(Policy.class);
    }
}
