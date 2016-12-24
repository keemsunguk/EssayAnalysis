package EssayAnalysis;

public class PosData {
	int essayNo;
	int sentenceNo;
	String pos;
	String lemma;
	
	public PosData() {
		
	}
	
	public PosData(int eno, int sno, String p, String l) {
		essayNo = eno;
		sentenceNo = sno;
		pos = p;
		lemma = l;
	}
}
