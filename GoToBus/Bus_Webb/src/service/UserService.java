package service;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
//import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import EJBs.Notification;
import EJBs.Station;
import EJBs.Trip;
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
		
		
		entitymanager.persist(s);
		return "Success " + s.getId() ;
	}
		catch (Exception e)
	{
		throw new EJBException(e);
	}
	
}

@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("station_id/{idd}")
public Station GetStation(@PathParam("idd") int id)
{
	Station st = entitymanager.createQuery(
			  "SELECT u from Station u WHERE u.id = :ID", Station.class).
			setParameter("ID",id ).getSingleResult();
	return st;
}

@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("trip")
public String CreateTrip(Trip t)
{
	try 
	{
		String at = t.arrival_time;
		t.setArrival_time(at);
	
		
		
		entitymanager.persist(t);
		return "Success "+ t.getArrival_time()+" "+t.getDeparture_time();
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
