package EssayAnalysis;

import java.io.*;
import java.util.*;

//import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
//import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;

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
	    pipeline.prettyPrint(annotation, out);
	    String localXmlName = xmlName+e.recno.toString()+".xml";
	    xmlOut = new PrintWriter(localXmlName);
	    pipeline.xmlPrint(annotation, xmlOut);
	    xmlOut.close();
	    
/*	    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	    if (sentences != null && sentences.size() > 0) {
	      CoreMap sentence = sentences.get(0);
	      Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
	      out.println(tree.toString());
	    }
*/	    
	}
	System.out.println("Done");
  }

}
