package nl.pubsong.operations;

import nl.pubsong.music.AfspeellijstData;

public abstract class VotingSystem {
	public abstract void addVote(AfspeellijstData afspeellijstData, User user);
	
	public abstract void minusUserVote(User user);
	
	public abstract void checkVotes(User user);
}
