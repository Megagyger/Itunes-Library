package com.dss.calvin.rest;

import java.util.Collection;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.servlet.FilterRegistration.Dynamic;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dss.calvin.entity.User;
import com.dss.calvin.services.UserServiceLocal;

@Path("/user")
public class UserREST {

	@EJB
	private UserServiceLocal service;
	
	@GET
	@Path("/persistIDs")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<String> getAllUserPersistentIDs(){
		return service.getAllUserPersistentIDs();
	}
	
	@POST
	@Path("/checkPass")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkPasswordForUser(JsonObject json){
		String name = json.getString("userName");
		String password = json.getString("password");
		User realUser = service.getUserByUserName(name);
		
		if(realUser.getPassword().equals(password)){
			return "correct";
		}else
			return "wrong";
	}
	
	@GET
	@Path("/userNames")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<String> getAllUserNames(){
		return service.getAllUserNames();
	}

	@GET
	@Path("user/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserByUserName(@PathParam("userName") String userName) {
		return service.getUserByUserName(userName);

	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addUser(User user) {
		
		service.addUser(user);
	}
	

}
