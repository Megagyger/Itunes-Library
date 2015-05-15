package com.dss.calvin.dao;

import java.util.Collection;

import javax.ejb.Local;

import com.dss.calvin.entity.User;

@Local
public interface UserDAO {

	public void addUser(User user);
	public User getUserByUserName(String UserName);
	public Collection<String> getAllUserNames();
	public Collection<String> getAllUserPersistentIDs();
	
	
}
