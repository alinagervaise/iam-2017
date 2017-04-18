package fr.epita.iam.services;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import fr.epita.iam.datamodel.Identity;

public class IdentityHibernateDAO implements IdentityDAO {
	
	@Inject
	SessionFactory  sessionFactory;
	
	public void create(Identity entity ){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.persist(entity);
		transaction.commit();
		session.close();
	}

	public void update(Identity entity) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(entity);
		transaction.commit();
		session.close();
		
	}

	public void delete(Identity entity) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(entity);
		transaction.commit();
		session.close();
	}

	
	public List<Identity> readAll() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<Identity>  result = (List<Identity>)session.createCriteria(Identity.class)
				.setResultTransformer(Criteria.ROOT_ENTITY);
		session.close();
		return result;
	}

}
