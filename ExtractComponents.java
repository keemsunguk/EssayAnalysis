package EssayAnalysis;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.ie.machinereading.structure.EntityMention;
import edu.stanford.nlp.ie.machinereading.structure.ExtractionObject;
import edu.stanford.nlp.ie.machinereading.structure.MachineReadingAnnotations;
import edu.stanford.nlp.ie.machinereading.structure.RelationMention;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.stats.Counters;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.Timex;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Pair;
import edu.stanford.nlp.util.StringUtils;
import nu.xom.*;

public class ExtractComponents {
	
	public ExtractComponents() {
		
	}
	
	public static void PrintAnnotationComponent(Annotation annotation) {
		
		if(annotation.get(CoreAnnotations.SentencesAnnotation.class) != null){
			int sentCount = 1;
			
			for (CoreMap sentence: annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
			      //Integer lineNumber = sentence.get(CoreAnnotations.LineNumberAnnotation.class);
				System.out.print("line "+sentCount+":");
			    
				sentCount ++;
				
				// add the word table with all token-level annotations
				List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
				for(int j = 0; j < tokens.size(); j ++){
					String tmpWord=tokens.get(j).word();
					String tmpLemma=tokens.get(j).lemma();
					String tmpPos=tokens.get(j).get(CoreAnnotations.PartOfSpeechAnnotation.class);
					System.out.print(tmpLemma+"("+tmpPos+":"+") ");
				}
				System.out.println();
				// print tree info
				
			    Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
			    if(tree != null) {
			    	// print the constituent tree for this sentence
			    	SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
			    	System.out.println(dependencies);
			    	System.out.println(tree.toString());
			    }
				
				// print the MR entities and relations
/*				List<EntityMention> entities = sentence.get(MachineReadingAnnotations.EntityMentionsAnnotation.class);
				List<RelationMention> relations = sentence.get(MachineReadingAnnotations.RelationMentionsAnnotation.class);
				if (entities != null && ! entities.isEmpty()){
					System.out.println(entities.toString()); 
					if(relations != null){
						System.out.println(relations.toString());
				    }
				}
*/				
				  
				  /**
				   * Adds sentiment as an attribute of this sentence.
				   
				  Tree sentimentTree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				  if (sentimentTree != null) {
				    int sentiment = RNNCoreAnnotations.getPredictedClass(sentimentTree);
				    sentElem.addAttribute(new Attribute("sentimentValue", Integer.toString(sentiment)));
				    String sentimentClass = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
				    sentElem.addAttribute(new Attribute("sentiment", sentimentClass.replaceAll(" ", "")));
				  }
				
				  // add the sentence to the root
				  sentencesElem.appendChild(sentElem);*/
			}
			System.out.println("Total Sentence: "+sentCount);
		}
	}

}
