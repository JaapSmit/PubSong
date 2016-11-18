package nl.pubsong.operations;

import nl.pubsong.music.AfspeellijstData;

public class AdminUser extends VotingSystem {
	
	public void addVote(AfspeellijstData afspeellijstData, User user) {
		afspeellijstData.setVotes(afspeellijstData.getVotes()+100);
	}
	
	public void minusUserVote(User user) {}
	
	public void checkVotes(User user) {}
}
