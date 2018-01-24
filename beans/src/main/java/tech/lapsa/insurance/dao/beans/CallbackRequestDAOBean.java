package tech.lapsa.insurance.dao.beans;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.lapsa.insurance.domain.CallbackRequest;

import tech.lapsa.insurance.dao.CallbackRequestDAO;
import tech.lapsa.insurance.dao.CallbackRequestDAO.CallbackRequestDAOLocal;
import tech.lapsa.insurance.dao.CallbackRequestDAO.CallbackRequestDAORemote;

@Stateless(name = CallbackRequestDAO.BEAN_NAME)
@Interceptors(LoggingInterceptor.class)
public class CallbackRequestDAOBean
	extends AGeneralInsuranceRequestDAO<CallbackRequest>
	implements CallbackRequestDAOLocal, CallbackRequestDAORemote {

    public CallbackRequestDAOBean() {
	super(CallbackRequest.class);
    }
}
