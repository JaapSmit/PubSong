package nl.pubsong.music;

import java.util.ArrayList;

public class Afspeellijst {
	private ArrayList<Nummer> lokaalAfspeellijst = new ArrayList<>();
	
	public void voegToe(Nummer nummer){
		lokaalAfspeellijst.add(nummer);
	}
	
	public int getSize() {
		return lokaalAfspeellijst.size();
	}
	
	public ArrayList<Nummer> getAfspeellijst() {
		return lokaalAfspeellijst;
	}

}
