package EJBs;



import java.time.LocalDateTime;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Stateless
@LocalBean
@Entity
public class Notification {
	//@Transient
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int Message_ID;
	public String message;
	public String notification_datetime;
	
	//@ManyToOne
	//@JoinColumn(name="userID")
	//public User userID;

	

	
	
	public int getMessage_ID() {
		return Message_ID;
	}
	public void setMessage_ID(int message_ID) {
		Message_ID = message_ID;
	}
	public String getNotification_datetime() {
		return notification_datetime;
	}
	public void setNotification_datetime(String notification_datetime) {
		this.notification_datetime = notification_datetime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
