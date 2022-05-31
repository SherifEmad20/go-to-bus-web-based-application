package service;



import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Transient;
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
//@PermitAll

public class UserService {
	User CurrentUser;

	
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
			return "Success" ;
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
		CurrentUser=user;
		
	}
	
	else
	{
		response = "Login Failed";
		
	}
	
	return response;
}

@POST
@Consumes(MediaType.APPLICATION_JSON)
//@RolesAllowed("admin")
@Path("station")
public String CreateStation(Station s) 
{
	try 
	{
	
		String role = CurrentUser.getRole();
		
		
		if(!role.equals("admin"))
		{
			return "You are not allowed to access this functions!!!";
		}
		
		
		String name = s.name;
		s.setStationName(name);
		
		
		entitymanager.persist(s);
		return "Success  " ;
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
//@RolesAllowed("admin")
@Path("trip")
public String CreateTrip(Trip t)
{
	try 
	{
		
String role = CurrentUser.getRole();
		
		
		if(!role.equals("admin"))
		{
			return "You are not allowed to access this functions!!!";
		}
		

		String at = t.arrival_time;
		t.setArrival_time(at);
	
		
		
		entitymanager.persist(t);
		return "Success";
	}
		catch (Exception e)
	{
		throw new EJBException(e);
	}
}

public static class UserxTrip{
int user_id;
int trip_id;
String from_date;
String to_date;
int from_station;
int to_station;
public String getFrom_date() {
	return from_date;
}
public void setFrom_date(String from_date) {
	this.from_date = from_date;
}
public String getTo_date() {
	return to_date;
}
public void setTo_date(String to_date) {
	this.to_date = to_date;
}
public int getFrom_station() {
	return from_station;
}
public void setFrom_station(int from_station) {
	this.from_station = from_station;
}
public int getTo_station() {
	return to_station;
}
public void setTo_station(int to_station) {
	this.to_station = to_station;
}

public int getUser_id() {
	return user_id;
}
public void setUser_id(int user_id) {
	this.user_id = user_id;
}
public int getTrip_id() {
	return trip_id;
}
public void setTrip_id(int trip_id) {
	this.trip_id = trip_id;
}

}

@POST
@Path("booktrip")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//@RolesAllowed({"admin","client"})
public String BookTrip(UserxTrip ut)
{
	try 
	{

		
		int user_id = ut.getUser_id();
		int trip_id = ut.getTrip_id();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now(); 
		   String dato = dtf.format(now);
		  
		   Notification notf = new Notification();
		
		
		User user = entitymanager.createQuery(
				  "SELECT u from User u WHERE u.id = :userID", User.class).
				setParameter("userID",user_id ).getSingleResult();
		
		Trip tr = entitymanager.createQuery(
				  "SELECT u from Trip u WHERE u.id = :tripID",Trip.class).
				setParameter("tripID",trip_id ).getSingleResult();
		
		user.trips.add(tr);
		
		int Seats = tr.getAvailable_seats();
		tr.setAvailable_seats(Seats-1);
		String message = "You have booked trip from " +tr.getFrom_station() + " to " + tr.getTo_station()+" successfully";
		
		
		notf.message=message;
		notf.notification_datetime=dato;
		
		user.UserNotifications.add(notf);
		
		
		try 
		{
			
			entitymanager.persist(notf);
			return "Success";
			
			
		}
		
		catch (Exception e)
		{
			throw new EJBException(e);
		}
		
		
		
		
		
		
	}
		catch (Exception e)
	{
		throw new EJBException(e);
	}
}

@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

@Path("searchtrips")
public List<Trip> SearchTrips(UserxTrip t)
{
	String FromDate = t.getFrom_date();
	String ToDate = t.getTo_date();
	int ToStation=t.getTo_station();
	int FromStation=t.getFrom_station();
	Station ToStat = entitymanager.createQuery(
			  "SELECT u from Station u WHERE u.id = :statID", Station.class).
			setParameter("statID",ToStation ).getSingleResult();
	Station FromStat = entitymanager.createQuery(
			  "SELECT u from Station u WHERE u.id = :statID", Station.class).
			setParameter("statID",FromStation ).getSingleResult();
	
	String ToStatName=ToStat.name;
	String FromStatName=FromStat.name;
	
	         List<Trip> t1 = entitymanager.createQuery("SELECT u from Trip u WHERE u.from_station= :fromstat AND u.to_station = :tostat AND u.departure_time LIKE '" + FromDate +"%' AND u.arrival_time LIKE '" + ToDate+"%' " , Trip.class).
	        		 setParameter("fromstat", FromStatName).
	        		 setParameter("tostat", ToStatName).
	        		 getResultList();
	        	
			 
			
			return  t1;
			
			
	}

@GET	
@Path("viewtrips/{idd}")
@Produces(MediaType.APPLICATION_JSON)
//@PermitAll
public List UserTrips(@PathParam("idd")int userID)
{
	User user = entitymanager.createQuery(
			  "SELECT u from User u WHERE u.id = :userID", User.class).
			setParameter("userID",userID ).getSingleResult();
List<Trip> un = user.trips;
	
	List<Trip> un2 = new ArrayList() ;
	for (int i =0 ; i<un.size();i++)
	{
		un2.add(un.get(i));
	}
	
	return un2;
	
}

@GET	
@Path("notifications/{idd}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//@RolesAllowed({"admin","client"})
public List<Notification> UserNotifications(@PathParam("idd")int userID)
{
	User user = entitymanager.createQuery(
			  "SELECT u from User u WHERE u.id = :userID", User.class).
			setParameter("userID",userID).getSingleResult();
	List<Notification> un = user.UserNotifications;
	
	List<Notification> un2 = new ArrayList() ;
	for (int i =0 ; i<un.size();i++)
	{
		un2.add(un.get(i));
	}
	
	

		
	
	
	
	return un2;
	
}






@GET	
@Path("hello")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public String getHello()
{
	//List<Notification> n1 = entitymanager.createQuery("SELECT u FROM Notification u").getResultList();
	
	
	return "Hello";
}


@GET	
@Path("hello_id/{id}")
public String getHello_ID(@PathParam("id")int id)
{
	         return  " hellooo " + id;
}


	


}
