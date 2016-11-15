package music;

import java.util.ArrayList;

public class Afspeellijst {
	private ArrayList<Nummer> lokaalAfspeellijst = new ArrayList<>();
	
	public void voegToe(Nummer nummer){
		lokaalAfspeellijst.add(nummer);
	}

}
