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
		if(user.getVotes() > 0) {
			user.setVotes(user.getVotes()-1);
		}
	}
	
	public void checkVotes(User user) {
		if(user.getVotes() < 3) {
			long timeDiff = (Duration.between(user.getLastVoteDate(), LocalDateTime.now())).toMinutes();
			System.out.println(timeDiff);
			System.out.println(user.getVotes());
			if(timeDiff + user.getVotes() > 3){
				user.setVotes(3);
				user.setDate();
			} else {
				user.setVotes(user.getVotes() + (int)timeDiff);
				user.setDate();
			}
		
		}
	}
	
	public void resetVoteUser() {
		
	}
}
