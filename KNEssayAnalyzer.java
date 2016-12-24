package EssayAnalysis;

import java.io.*;
import java.util.*;

import edu.stanford.nlp.ling.CoreAnnotations;
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
    ArrayList <Features> featuresList = new ArrayList <Features>();
	
	Properties props = new Properties();

    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
    props.put("ner.model", "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");
    props.put("ner.applyNumericClassifiers", "false");

    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    SpellingChecker spellCheck = new SpellingChecker();
    Annotation annotation;
    
	for(EssayData e:essayList) {
		annotation = new Annotation(e.essay);
	    ArrayList <PosData> pos;
	
	    pipeline.annotate(annotation);
//	    pipeline.prettyPrint(annotation, out);
	    String localXmlName = xmlName+e.recno.toString()+".xml";
	    xmlOut = new PrintWriter(localXmlName);
	    pipeline.xmlPrint(annotation, xmlOut);	    
	    xmlOut.close();
	    
	    ExtractComponents.setSpellChecker(spellCheck);
	    ExtractComponents.resetCount();
	    ExtractComponents.PrintAnnotationComponent(annotation);
	    pos = ExtractComponents.getPOS();
	    
	    Features temp = new Features();
    	temp.PopulateFeaturesFromEssay(e);
    	temp.setMisspelledCount(ExtractComponents.getMisspellCount());
    	temp.setSentenceCount(annotation.get(CoreAnnotations.SentencesAnnotation.class).size());
    	temp.setLongWordsCount(ExtractComponents.getLongWordCount());
    	temp.PopulateFeaturesFromPos(pos);
     	featuresList.add(temp);
	}
	
	Features.WriteHeader();
    for(Features f : featuresList) {
		f.WriteCSV();
    }

	System.out.println("Done");
	out.close();
  }

}
