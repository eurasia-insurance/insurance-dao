package tech.lapsa.insurance.dao.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.lapsa.insurance.domain.CompanyPointOfSale;

import tech.lapsa.insurance.dao.CompanyPointOfSaleDAO;
import tech.lapsa.insurance.dao.CompanyPointOfSaleDAO.CompanyPointOfSaleDAOLocal;
import tech.lapsa.insurance.dao.CompanyPointOfSaleDAO.CompanyPointOfSaleDAORemote;

@Stateless(name = CompanyPointOfSaleDAO.BEAN_NAME)
@Interceptors(LoggingInterceptor.class)
public class CompanyPointOfSaleDAOBean
	extends ABaseDAO<CompanyPointOfSale, Integer>
	implements CompanyPointOfSaleDAOLocal, CompanyPointOfSaleDAORemote {

    public CompanyPointOfSaleDAOBean() {
	super(CompanyPointOfSale.class);
    }

    @Override
    public List<CompanyPointOfSale> findAll() {
	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<CompanyPointOfSale> cq = cb.createQuery(CompanyPointOfSale.class);
	final Root<CompanyPointOfSale> root = cq.from(CompanyPointOfSale.class);
	cq.select(root);
	final TypedQuery<CompanyPointOfSale> q = em.createQuery(cq);
	return q.getResultList();
    }

}
