package nl.pubsong.music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Afspeellijst {
	private List<AfspeellijstData> lokaalAfspeellijst = new ArrayList<>();
	
	public void voegToe(AfspeellijstData afspeellijstData){
		lokaalAfspeellijst.add(afspeellijstData);
	}
	
	public int getSize() {
		return lokaalAfspeellijst.size();
	}
	
	public List<AfspeellijstData> getAfspeellijst() {
		return lokaalAfspeellijst;
	}
	
	public void sort(){
		Collections.sort(lokaalAfspeellijst, new Comparator<AfspeellijstData>() {
		    @Override
		    public int compare(AfspeellijstData o1, AfspeellijstData o2) {
		        return o2.getVotes() - o1.getVotes();
		    }
		});	
	}

}
