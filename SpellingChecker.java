package EssayAnalysis;

//import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.dictionary.Dictionary;

/*
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
*/
public class SpellingChecker {
	
	private static Dictionary dictionary;
	
	public SpellingChecker() throws IOException {
		System.out.println("Building Dictionairy");
		String s;
		try {
			JWNL.initialize(new FileInputStream("/Users/keemsunguk/Applications/jwnl14-rc2/config/file_properties.xml"));
		} catch (JWNLException e) {
			e.printStackTrace();
		}
		if(!JWNL.isInitialized()) {
			System.out.println("Dictionary missing");
		}
		dictionary = Dictionary.getInstance();
	}

	public static boolean eval(String pos, String word){
		POS p = new POS;
		if( dictionary.getIndexWord(p,word) ) {
			return true;
		}
		return false;
	}
	

}
