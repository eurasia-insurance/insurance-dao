package tech.lapsa.insurance.dao.beans;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import tech.lapsa.insurance.dao.InsuranceDAOPingService;
import tech.lapsa.insurance.dao.InsuranceDAOPingService.InsuranceDAOPingServiceLocal;
import tech.lapsa.insurance.dao.InsuranceDAOPingService.InsuranceDAOPingServiceRemote;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyExceptions;

@Stateless(name = InsuranceDAOPingService.BEAN_NAME)
@Interceptors(LoggingInterceptor.class)
//TODO REFACT : Remove this bean. Use EntityManagerControlBean directly
public class InsuranceDAOPingServiceBean implements InsuranceDAOPingServiceLocal, InsuranceDAOPingServiceRemote {

    @EJB
    private EntityManagerControlBean emControl;

    @Override
    public void ping() throws IllegalState {
	try {
	    emControl.ping();
	} catch (Exception e) {
	    throw MyExceptions.illegalStateFormat(e.getMessage());
	}
    }

}
