package nl.pubsong.operations;

import nl.pubsong.music.AfspeellijstData;

public class AdminUser extends VotingSystem {
	
	public void addVote(AfspeellijstData afspeellijstData, User user) {
		afspeellijstData.setVotes(afspeellijstData.getVotes()+1000);
		afspeellijstData.setAdminVote(true);
	}
	
	public void minusUserVote(User user) {}
	
	public void checkVotes(User user) {}
}
