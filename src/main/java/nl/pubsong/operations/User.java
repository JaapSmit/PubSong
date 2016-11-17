package nl.pubsong.operations;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class User {
	private String userName;
	private String passWord;
	private long id;
	
	private String rightsString;
	
	public User() {
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	@Transient
	private VotingSystem rights;
	
	@Transient
	public VotingSystem getRights() {
		if(rights == null) {
			if(rightsString.equals("BasicUser")) {
				this.rights = new BasicUser();
			} else if(rightsString.equals("AdminUser")) {
				this.rights = new AdminUser();
			}
		}
		return rights;
	}
	
	@Transient
	public void setRights(VotingSystem rights) {
		this.rights = rights;
	}
	
	public String getRightsString() {
		return rightsString;
	}
	public void setRightsString(String rightsString) {
		this.rightsString = rightsString;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void createRights() {
		if(rightsString.equals("BasicUser")) {
			this.rights = new BasicUser();
		}
	}
	
}
