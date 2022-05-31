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
	
	
@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("login")
public String log(User u1)
{
	String response;
	String passw= u1.getPassword();
	
	String userN = u1.getUserName();
	User user = entitymanager.createQuery(
			  "SELECT u from User u WHERE u.username = :userN", User.class).
			setParameter("userN",userN ).getSingleResult();
	String pass = user.getPassword();
	
	
	if (pass.equals(passw))
	{
		response = "Login successfuly";
		flag = true;
	}
	
	else
	{
		response = "Login Failed";
		
	}
	
	return response;
}

@POST
@Consumes(MediaType.APPLICATION_JSON)
@Path("station")
public String CreateStation(Station s) 
{
	try 
	{
		String name = s.name;
		s.setStationName(name);
		String n = s.getStationName();
		String l = s.getLongitude();
		String la = s.getLatitude();
		
		entitymanager.persist(s);
		return "Success :" +n+" "+ l+" "+la ;
	}
		catch (Exception e)
	{
		throw new EJBException(e);
	}
	
}
	

@GET	
@Path("hello")
public String getHello()
{
	         return "Hello";
}

@GET	
@Path("hello_id/{id}")
public String getHello_ID(@PathParam("id")int id)
{
	         return  " hellooo " + id;
}


	


}
