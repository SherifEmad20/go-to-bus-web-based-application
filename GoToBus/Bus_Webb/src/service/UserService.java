package service;

import java.awt.List;
import java.util.ArrayList;


import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import EJBs.Station;
import EJBs.User;





@Stateless
@Path("/users")

public class UserService {
	
	boolean flag = false;
	
	@PersistenceContext(unitName="hello")
	private EntityManager entitymanager;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("user")
	public String register(User u) 
	{
		try 
		{
			String us = u.username;
			//User u = new User();
			u.setUserName(us);
			entitymanager.persist(u);
			return "Success " ;
		}
			catch (Exception e)
		{
			throw new EJBException(e);
		}
	}
	
