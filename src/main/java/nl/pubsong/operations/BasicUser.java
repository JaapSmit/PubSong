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
			int intTimeDiff = (int)(timeDiff/60); // delen door 60, en dan afkappen achter de komma. Als het goed is blijven nu hele getallen over.
			if(intTimeDiff > 0) { 
				if(intTimeDiff + user.getVotes() > 3){
					user.setVotes(3);
					user.setDate();
				} else {
					user.setVotes(user.getVotes() + intTimeDiff);
					user.setDate();
				}
			}
		
		}
	}
}
