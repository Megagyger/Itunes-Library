package com.dss.calvin.dao.jpa;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dss.calvin.dao.UserDAO;
import com.dss.calvin.entity.User;

@Stateless
@Local
public class JPAUserDAO implements UserDAO {

	@PersistenceContext
	EntityManager entityManager;

	public void addUser(User user) {
		entityManager.persist(user);

	}

	public User getUserByUserName(String userName) {
		Query query = entityManager
				.createQuery("from User u where u.userName = :userName");
		query.setParameter("userName", userName);
		
		
		User nullUser = null;

		try {
			return (User) query.getSingleResult();
		} catch (NoResultException nre) {
			// Expected exception when non registered user logs in
			return nullUser;
		}

	}

	@Override
	public Collection<String> getAllUserNames() {
		Query query = entityManager.createQuery("select u.userName from User u");
		return query.getResultList();
	}

	@Override
	public Collection<String> getAllUserPersistentIDs() {
		Query query = entityManager.createQuery("select u.persistenceID from User u");
		return query.getResultList();
	}

	

}
