package nl.pubsong.operations;

import java.time.Duration;
import java.time.LocalDateTime;

import nl.pubsong.music.AfspeellijstData;

public class PremiumUser extends VotingSystem{

	@Override
	public void addVote(AfspeellijstData afspeellijstData, User user) {
		if(user.getVotes() > 0) {
			afspeellijstData.setVotes(afspeellijstData.getVotes()+2);
		}
	}

	@Override
	public void minusUserVote(User user) {
		if(user.getVotes() > 0) {
			user.setVotes(user.getVotes()-1);
		}
		
	}

	@Override
	public void checkVotes(User user) {
		if(user.getVotes() < 6) {
			long timeDiff = (Duration.between(user.getLastVoteDate(), LocalDateTime.now())).toMillis(); // bijv 5000, 5 sec.. op de halve minuut krijgt hij erwat bij. (normal user is 1 minuut, dus 60000)
			timeDiff *= 2; // twee keer zo snel?
			System.out.println(timeDiff);
			System.out.println(user.getVotes());
			int intTimeDiff = (int)(timeDiff/60000); // delen door 60000, en dan afkappen achter de komma. Als het goed is blijven nu hele getallen over.
			if(intTimeDiff > 0) { 
				if(intTimeDiff + user.getVotes() > 6){
					user.setVotes(6);
					user.setDate();
				} else {
					user.setVotes(user.getVotes() + intTimeDiff);
					user.setDate();
				}
			} 
		
		}
		
	}

}
