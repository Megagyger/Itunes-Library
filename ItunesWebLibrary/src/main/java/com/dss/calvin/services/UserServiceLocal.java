package com.dss.calvin.services;

import java.util.Collection;

import javax.ejb.Local;

import com.dss.calvin.entity.User;

@Local
public interface UserServiceLocal {

	public void addUser(User user);
	public User getUserByUserName(String userName);
	public Collection<String> getAllUserNames();
	public Collection<String> getAllUserPersistentIDs();
	
}
