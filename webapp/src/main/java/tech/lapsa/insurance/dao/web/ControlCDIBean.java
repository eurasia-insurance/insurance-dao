package tech.lapsa.insurance.dao.web;

import java.io.Serializable;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import tech.lapsa.insurance.dao.InsuranceDAOPingService.InsuranceDAOPingServiceLocal;
import tech.lapsa.insurance.dao.beans.EntityManagerControlBean;
import tech.lapsa.java.commons.exceptions.IllegalState;

@Named("control")
@RequestScoped
@RolesAllowed("role-admin")
public class ControlCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private EntityManagerControlBean bean;

    public String doFlushAndClear() {
	bean.flushAndClear();
	return "home";
    }

    @EJB
    private InsuranceDAOPingServiceLocal pingService;

    public String doPingDatabase() throws IllegalState {
	pingService.ping();
	return "home";
    }

}
