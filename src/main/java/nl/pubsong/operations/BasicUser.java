package nl.pubsong.operations;

import java.time.Duration;
import java.time.LocalDateTime;

import nl.pubsong.music.AfspeellijstData;

public class BasicUser extends VotingSystem {
	
	public void addVote(AfspeellijstData afspeellijstData, User user) {
		if(user.getVotes() > 0) {
			afspeellijstData.setVotes(afspeellijstData.getVotes()+1);
		}
	}
	
	public void minusUserVote(User user) {
		user.setVotes(user.getVotes()-1);
	}
	
	public void addUserVote(User user) {
		user.setVotes(user.getVotes()+1);
	}
	
	public void checkVotes(User user) {
		if(user.getVotes() < 3) {
			long timeDiff = (Duration.between(user.getLastVoteDate(), LocalDateTime.now())).toHours();
			if(timeDiff >= 1){
				addUserVote(user);
			}
		}
	}
	
	public void resetVoteUser() {
		
	}
}
