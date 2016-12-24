package EssayAnalysis;

import java.io.BufferedReader;
import java.io.File;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Dictionary;
//import java.util.Vector;



public class SpellingChecker {
	private File inf;
	private static ArrayList<String> dictMemory = new ArrayList<String>();
	
	public SpellingChecker() {
		System.out.println("Building Dictionairy");
		String s = "dictionary.txt";
		inf = new File(s);
		
		try {
			BufferedReader inbuf = new BufferedReader (new FileReader (inf));
			try {
				String oneLine;
				while ((oneLine = inbuf.readLine()) != null) {
					dictMemory.add(oneLine);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				inbuf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public static boolean eval(String word) {
		if (dictMemory.contains(word)) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<String> getDictionary() {
		return dictMemory;
	}
}
