import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Parser {

	private int nbrWords;
	private ArrayList<Pair> pairs;
	private File wordFile;
	private File pairFile;
	private boolean fromFile;

	public Parser() {
		
	}
	
	public Parser(String wordFileName, String pairFileName)
			throws FileNotFoundException {
		this.wordFile = new File(wordFileName);
		this.pairFile = new File(pairFileName);
		fromFile = true;
	}
	

	public void parse() throws FileNotFoundException {
		nbrWords = 0;
		Scanner scanner;
		if (fromFile) {
			scanner = new Scanner(wordFile);
		} else {
			scanner = new Scanner(System.in);
		}
		HashMap<String, DictWord> dictionary = new HashMap<String, DictWord>();
		HashMap<String, Word> words = new HashMap<String, Word>();
		while (scanner.hasNextLine()) {
			String word = scanner.nextLine();
			words.put(word, new Word(word, nbrWords++, dictionary));
		}
		scanner.close();
		if (fromFile) {
			scanner = new Scanner(pairFile);
		} else {
			scanner = new Scanner(System.in);
		}
		
		pairs = new ArrayList<Pair>();
		while (scanner.hasNextLine()) {
			String[] pairWords = scanner.nextLine().split(" ");
			Word word1 = words.get(pairWords[0]);
			Word word2 = words.get(pairWords[1]);
			pairs.add(new Pair(word1, word2));
		}
		scanner.close();
	//	System.out.println("nbrWord = " + nbrWords);
	//	for (Pair pair : pairs) {
	//		System.out.println("Word1=" + pair.getFirst() + ",word2=" + pair.getSecond());
	//	}
		//for (Word word : words.values()) {
		//	System.out.println(word);
		//	for (DictWord dw : word.edges) {
		//		System.out.println(" -> " + dw);
		//	}
		//}
		//System.out.println("----------------");
		//for(DictWord dw : dictionary.values()) {
		//	System.out.println(dw);
		//	for (Word word : dw.edges.values()) {
		//		System.out.println(" -> " + word);
		//	}
		//}

	}

	public int getNbrWords() {
		return nbrWords;
	}

	public ArrayList<Pair> getPairs() {
		return pairs;
	}
}
