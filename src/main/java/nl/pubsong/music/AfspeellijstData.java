package nl.pubsong.music;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AfspeellijstData {
	private long id;
	private Nummer nummer;
	private int votes;
	private boolean adminVote;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne
	public Nummer getNummer() {
		return nummer;
	}
	public void setNummer(Nummer nummer) {
		this.nummer = nummer;
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
	public boolean isAdminVote() {
		return adminVote;
	}
	public void setAdminVote(boolean adminVote) {
		this.adminVote = adminVote;
	}
	
	
	
	
	
}
