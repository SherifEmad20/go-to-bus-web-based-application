package EJBs;

<<<<<<< HEAD

import java.util.ArrayList;
import java.util.List;
=======
>>>>>>> efc0d67 (Station Entity)
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
<<<<<<< HEAD
import javax.persistence.CascadeType;
=======
>>>>>>> efc0d67 (Station Entity)
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Stateless
@LocalBean
@Entity
@Table(name="USER")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")

	int id;
	@Column(name="username")
	public String username;
	@Column(name="password")

	String password;
	@Column(name="full_name")

	String full_name;
	@Column(name="role")

	String role;
	
	
<<<<<<< HEAD
@OneToMany
//@JoinColumn(name="trips")
public List<Trip> trips;

@OneToMany
public List<Notification> UserNotifications;
=======
/*	@ManyToMany
	@JoinTable(
			name="UserXTrip",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="trip_id"))
	Set<Trip> trips;*/
	
>>>>>>> efc0d67 (Station Entity)
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String userName) {
		username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
