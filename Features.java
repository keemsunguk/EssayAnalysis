package EssayAnalysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Features {
	public Integer essayNo;
	public Integer rate;
	private Integer length;
	private Integer wordCount;
	private Double avgWordLength;
	private Integer noOfSentences;
	private Integer noOfLongWords;
	private Integer CCcount;
	private Integer CDcount;
	private Integer DTcount;
	private Integer EXcount;
	private Integer INcount;
	private Integer JJcount;
//	private Integer JJScount;
	private Integer MDcount;
	private Integer NNcount;	
	private Integer NNPcount;
//	private Integer NNScount;
	private Integer PRPcount;
//	private Integer PRP2count;
	private Integer RBcount;
	private Integer TOcount;
	private Integer VBcount;
	private Integer VBDcount;
	private Integer VBGcount;
	private Integer VBNcount;
	private Integer VBPcount;
	private Integer VBZcount;
	private Integer VBall;
	private Integer WDTcount;
	private Integer WPcount;
	private Integer WRBcount;
//	private Integer noOfParagraphs;
//	private Integer noOfGoodSentences;
	
	public Features() {
		noOfLongWords = 0;
		CCcount = 0;
		CDcount = 0;
		DTcount = 0;
		EXcount = 0;
		INcount = 0;
		JJcount = 0;
//		JJScount = 0;
		MDcount = 0;
		NNcount = 0;	
		NNPcount = 0;
//		NNScount = 0;
		PRPcount = 0;
//		PRP2count = 0;
		RBcount = 0;
		TOcount = 0;
		VBcount = 0;
		VBDcount = 0;
		VBGcount = 0;
		VBNcount = 0;
		VBPcount = 0;
		VBZcount = 0;
		WDTcount = 0;
		WPcount = 0;
		WRBcount = 0;
	}
	
	public void PopulateFeaturesFromEssay(EssayData e) {
		essayNo = e.recno;
		rate = e.rate;
		length = e.essay.length();
		wordCount = e.wordCount;
	}
	
	public void PopulateFeaturesFromPos(ArrayList<PoeData> posList) {
		int totalLength = 0;
		int wcnt = 0;
		int maxSentenceNo = 0;

		for(PoeData p : posList) {
			if(!(p.lemma.equals(".") ||  p.lemma.equals(",")) ) {
				totalLength += p.lemma.length();
				wcnt++;
				if(p.lemma.length() > 5) {
					noOfLongWords++;
				}
			}
			if(maxSentenceNo < p.sentenceNo) {
				maxSentenceNo = p.sentenceNo;
			}
			
			switch(p.pos) {
			case "CC":
				CCcount++;
				break;
			case "CD":
				CDcount++;
				break;
			case "DT":
				DTcount++;
				break;
			case "EX":
				EXcount++;
				break;
			case "IN":
				INcount++;
				break;
			case "JJ":
			case "JJS":
				JJcount++;
				break;
			case "MD":	
				MDcount++;
				break;
			case "NN":
			case "NNS":
				NNcount++;
				break;
			case "NNP":
				NNPcount++;
				break;
			case "PRP":
			case "PRP.":
				PRPcount++;
				break;
			case "RB":
				RBcount++;
				break;
			case "TO":
				TOcount++;
				break;
			case "VB":
				VBcount++;
				break;
			case "VBD":
				VBDcount++;
				break;
			case "VBG":
				VBGcount++;
				break;
			case "VBN":
				VBNcount++;
				break;
			case "VBP":
				VBPcount++;
				break;
			case "VBZ":
				VBZcount++;
				break;
			case "WDT":
				WDTcount++;
				break;
			case "WP":
				WPcount++;
				break;
			case "WRB":
				WRBcount++;
				break;
			}
		}
		avgWordLength = Double.valueOf(totalLength)/wcnt;
		noOfSentences = maxSentenceNo;
		VBall = VBcount+VBDcount+VBGcount+VBNcount+VBPcount+VBZcount;
		
	}
	
	public void WriteCSV() {
		System.out.format("%d,%d,%d,%d,", essayNo,rate,length, wordCount);
		System.out.format("%3.2f,%d,%d,%d,", avgWordLength, noOfSentences,noOfLongWords, VBall);
		System.out.format("%d,%d,%d,%d,", CCcount, CDcount, DTcount, EXcount);
		System.out.format("%d,%d,%d,%d,", INcount, JJcount, MDcount, NNcount);
		System.out.format("%d,%d,%d,%d,", NNPcount, PRPcount, RBcount, TOcount);
		System.out.format("%d,%d,%d,%d,", VBcount, VBDcount, VBGcount, VBNcount);
		System.out.format("%d,%d,%d,%d,%d\n", VBPcount, VBZcount, WDTcount, WPcount, WRBcount);
	}
	
	public void WriteCSV(String f) {
		try(FileWriter fw = new FileWriter(f, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
		{
			out.format("%d,%d,%d,%d,", essayNo,rate,length, wordCount);
			out.format("%3.2f,%d,%d,%d,", avgWordLength, noOfSentences,noOfLongWords, VBall);
			out.format("%d,%d,%d,%d,", CCcount, CDcount, DTcount, EXcount);
			out.format("%d,%d,%d,%d,", INcount, JJcount, MDcount, NNcount);
			out.format("%d,%d,%d,%d,", NNPcount, PRPcount, RBcount, TOcount);
			out.format("%d,%d,%d,%d,", VBcount, VBDcount, VBGcount, VBNcount);
			out.format("%d,%d,%d,%d,%d\n", VBPcount, VBZcount, WDTcount, WPcount, WRBcount);
			out.close();
			bw.close();
			fw.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	
	}
	
	public static void WriteHeader() {
	    System.out.println("eid,rate,length,wcount,avgWordLength, noOfSentences,noOfLongWords,VBall,"
	    		+ "CCcount, CDcount, DTcount, EXcount,INcount, JJcount, MDcount, NNcount,NNPcount, PRPcount, RBcount, TOcount,"
	    		+ "VBcount, VBDcount, VBGcount, VBNcount,VBPcount, VBZcount, WDTcount, WPcount, WRBcount");		
	}
	public static void WriteHeader(String f) {
		try(FileWriter fw = new FileWriter(f);		//overwrite
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
		{
			out.println("eid,rate,length,wcount,avgWordLength, noOfSentences,noOfLongWords,VBall,"
		    		+ "CCcount, CDcount, DTcount, EXcount,INcount, JJcount, MDcount, NNcount,NNPcount, PRPcount, RBcount, TOcount,"
		    		+ "VBcount, VBDcount, VBGcount, VBNcount,VBPcount, VBZcount, WDTcount, WPcount, WRBcount");			out.close();
			bw.close();
			fw.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}
}
