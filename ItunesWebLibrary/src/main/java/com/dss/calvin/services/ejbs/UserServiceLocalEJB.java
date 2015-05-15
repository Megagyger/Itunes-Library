package com.dss.calvin.services.ejbs;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.dss.calvin.dao.UserDAO;
import com.dss.calvin.entity.User;
import com.dss.calvin.services.UserServiceLocal;

@Stateless
@Local
public class UserServiceLocalEJB implements UserServiceLocal {

	@EJB
	private UserDAO dao;

	public void addUser(User user) {
		dao.addUser(user);

	}

	public User getUserByUserName(String userName) {
		return dao.getUserByUserName(userName);
	}

	@Override
	public Collection<String> getAllUserNames() {
		return dao.getAllUserNames();
	}

	@Override
	public Collection<String> getAllUserPersistentIDs() {
		return dao.getAllUserPersistentIDs();
	}

	
}
