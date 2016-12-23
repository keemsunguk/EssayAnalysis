package EssayAnalysis;

import org.xeustechnologies.googleapi.spelling.Configuration;
import org.xeustechnologies.googleapi.spelling.Language;
import org.xeustechnologies.googleapi.spelling.SpellChecker;
import org.xeustechnologies.googleapi.spelling.SpellCorrection;
import org.xeustechnologies.googleapi.spelling.SpellRequest;
import org.xeustechnologies.googleapi.spelling.SpellResponse;

public class SpellingChecker {
		
	public SpellingChecker()  {
	}

	public static void eval(String e){
		// Proxy settings 
		Configuration config = new Configuration(); 
		config.setProxy( "my_proxy_host", 8080, "http" );

		SpellChecker checker = new SpellChecker( config ); 
		checker.setOverHttps( true ); // Use https (default true from v1.1) 
		checker.setLanguage( Language.ENGLISH ); // Use English (default)

		SpellRequest request = new SpellRequest(); 
		request.setText(e); 
		request.setIgnoreDuplicates( true ); // Ignore duplicates

		SpellResponse spellResponse = checker.check( request );

		for( SpellCorrection sc : spellResponse.getCorrections() ) 
			System.out.println( sc.getValue() );
	}
	

}
