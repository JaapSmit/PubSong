package nl.pubsong.operations;

import nl.pubsong.music.AfspeellijstData;

public class AdminUser extends VotingSystem {
	public void addVote(AfspeellijstData afspeellijstData) {
		afspeellijstData.setVotes(afspeellijstData.getVotes()+100);
	}
}
