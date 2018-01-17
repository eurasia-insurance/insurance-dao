package tech.lapsa.insurance.dao.beans;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.lapsa.insurance.domain.Request;

import tech.lapsa.insurance.dao.RequestDAO;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAOLocal;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;

@Stateless(name = RequestDAO.BEAN_NAME)
@Interceptors(LoggingInterceptor.class)
public class RequestDAOBean
	extends AGeneralRequestDAO<Request>
	implements RequestDAOLocal, RequestDAORemote {

    public RequestDAOBean() {
	super(Request.class);
    }
}
