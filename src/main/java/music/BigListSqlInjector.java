package music;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BigListSqlInjector {
	public static String artiest;
	
	public static void main(String[] args) {
		
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader("C:/Users/Student/workspace/PubSong/src/main/lijstMuziek.txt"));
		    StringBuilder sb = new StringBuilder();
		    String line = file.readLine();

		    while (line != null) {
		    	if(!line.isEmpty()) {
		    		formatLine(line);
		    	}
		        //sb.append(System.lineSeparator());
		        line = file.readLine();
		    }
		    //String everything = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	static void formatLine(String line) {
		if(line.charAt(0) == '-') {
			// ben je een artiestenregel
			boolean found = false;
			int index = 2;
			while(!found) {
				int plek = line.indexOf('-', index);
				if(line.charAt(plek+1) == '-') {
					// gevonden
					artiest = line.substring(2, plek).trim();
					found = true;
					System.out.println(artiest);
				} else {
					index++;
				}
				// om oneindige loop te voorkomen
				if(line.length()-1 == index) {
					found = true;
				}
			}
		} else if (line.charAt(0) == '0' || 
					line.charAt(0) == '1' ||
					line.charAt(0) == '2' ||
					line.charAt(0) == '3' ||
					line.charAt(0) == '4' ||
					line.charAt(0) == '5' ||
					line.charAt(0) == '6' ||
					line.charAt(0) == '7' ||
					line.charAt(0) == '8' ||
					line.charAt(0) == '9') {
			// ben je een nummer
			int plek = line.indexOf('\t');
			if(plek == -1) {
				plek = line.indexOf(' ');
			}
			int eind = line.indexOf('[');
			String titel = line.substring(plek, eind).trim();
			Nummer nummer = new Nummer(artiest, titel);
		} else {
			// negeren we je
		}
		
		
		
	}
}
