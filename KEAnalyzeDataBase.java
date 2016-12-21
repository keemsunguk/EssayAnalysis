package EssayAnalysis;

import java.util.*;

public class KEAnalyzeDataBase {
	private static KNEssayDB essayDB = null;
	private static boolean _verboseMode = true;

	public static void main(String[] args) {

	    if (args.length > 0) {
	    	Features.WriteHeader(args[0]);
	    } else {
	    	Features.WriteHeader();
	    }

	  	essayDB = new KNEssayDB(2);			//Connect to database and populate the data
	  	essayDB.SetVerbose(_verboseMode);
	  	if(_verboseMode) System.out.println("Verbose Mode On");		

	  	ArrayList <EssayData> essayList = essayDB.PopulateEssayData();

	    ArrayList <Features> featuresList = new ArrayList <Features>();
	    for (EssayData e : essayList) {
	    	ArrayList <PoeData> poe = essayDB.GetPoeData(e.recno);
	    	Features temp = new Features();
	    	temp.PopulateFeaturesFromEssay(e);
	    	temp.PopulateFeaturesFromPos(poe);
	    	featuresList.add(temp);
	    }
	    
	    for(Features f : featuresList) {
	    	if (args.length > 0) {
	    		f.WriteCSV(args[0]);   		
	    	} else {
	    		f.WriteCSV();
	    	}
	    }

	}

}
