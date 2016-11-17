package nl.pubsong.music;

import java.util.ArrayList;

public class Afspeellijst {
	private ArrayList<AfspeellijstData> lokaalAfspeellijst = new ArrayList<>();
	
	public void voegToe(AfspeellijstData afspeellijstData){
		lokaalAfspeellijst.add(afspeellijstData);
	}
	
	public int getSize() {
		return lokaalAfspeellijst.size();
	}
	
	public ArrayList<AfspeellijstData> getAfspeellijst() {
		return lokaalAfspeellijst;
	}

}
