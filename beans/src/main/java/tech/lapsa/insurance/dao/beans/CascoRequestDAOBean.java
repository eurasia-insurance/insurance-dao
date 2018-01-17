package tech.lapsa.insurance.dao.beans;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.lapsa.insurance.domain.casco.CascoRequest;

import tech.lapsa.insurance.dao.CascoRequestDAO;
import tech.lapsa.insurance.dao.CascoRequestDAO.CascoRequestDAOLocal;
import tech.lapsa.insurance.dao.CascoRequestDAO.CascoRequestDAORemote;

@Stateless(name = CascoRequestDAO.BEAN_NAME)
@Interceptors(LoggingInterceptor.class)
public class CascoRequestDAOBean
	extends AGeneralInsuranceRequestDAO<CascoRequest>
	implements CascoRequestDAOLocal, CascoRequestDAORemote {

    public CascoRequestDAOBean() {
	super(CascoRequest.class);
    }
}
