package EssayAnalysis;

import java.io.*;
import java.util.*;

import edu.stanford.nlp.pipeline.*;

public class KNEssayAnalyzer {
	
	private static KNEssayDB essayDB = null;
	private static boolean _verboseMode = true;

  public static void main(String[] args) throws IOException {
    PrintWriter out;
    if (args.length > 0) {
      out = new PrintWriter(args[0]);
    } else {
      out = new PrintWriter(System.out);
    }
    
    String xmlName;
    PrintWriter xmlOut = null;

    if (args.length > 1) {
    	xmlName = args[1];
    } else {
    	xmlName = "essay";
    }

	essayDB = new KNEssayDB(2);			//Connect to database and populate the data
	essayDB.SetVerbose(_verboseMode);
	if(_verboseMode) System.out.println("Verbose Mode On");		

	ArrayList <EssayData> essayList = essayDB.PopulateEssayData();
	
	Properties props = new Properties();

    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
    props.put("ner.model", "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");
    props.put("ner.applyNumericClassifiers", "false");

    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    Annotation annotation;
    
	for(EssayData e:essayList) {
		annotation = new Annotation(e.essay);
	
	    pipeline.annotate(annotation);
//	    pipeline.prettyPrint(annotation, out);
	    String localXmlName = xmlName+e.recno.toString()+".xml";
	    xmlOut = new PrintWriter(localXmlName);
	    pipeline.xmlPrint(annotation, xmlOut);	    
	    xmlOut.close();
	    
	    ExtractXMLComponent.PrintAnnotationComponent(annotation);

	}
	System.out.println("Done");
	out.close();
  }

}
