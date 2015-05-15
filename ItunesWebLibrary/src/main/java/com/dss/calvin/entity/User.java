package com.dss.calvin.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String userName;

	private String password;

	private String persistenceID;

	public User() {

	}

	public User(String userName, String password, String persistenceID) {
		this.userName = userName;
		this.password = password;
		this.persistenceID = persistenceID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPersistenceID() {
		return persistenceID;
	}

	public void setPersistenceID(String persistenceID) {
		this.persistenceID = persistenceID;
	}

}
