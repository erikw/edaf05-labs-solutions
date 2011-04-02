import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WordLadders {
	
	public static void main(String[] args) {
		try {
			new WordLadders().run(args);
		} catch (FileNotFoundException e) {
			System.err.println("Files could not be read");
		}
	}
	
	public void run(String[] args) throws FileNotFoundException {
		Parser parser;
		if (args.length == 2) {
			parser = new Parser(args[0], args[1]);
		} else {
			parser = new Parser();
		}
		parser.parse();
		ArrayList<Pair> pairs = parser.getPairs();
		int nbrWords = parser.getNbrWords();
		
		for (Pair pair: pairs) {
			boolean[] discovered = new boolean[nbrWords];
			List<Word> currentLayer = new LinkedList<Word>();
			List<Word> nextLayer;
			currentLayer.add(pair.getFirst());
			discovered[pair.getFirst().getIndex()] = true;
			int distance = 0;
			int secondIndex = pair.getSecond().getIndex();
			//System.out.println(pair);
			while (!currentLayer.isEmpty() && !discovered[secondIndex]) {
				nextLayer = new LinkedList<Word>();
				for (Word word : currentLayer) {	
					word.putAdjacentWords(nextLayer,discovered);
				}
				distance++;
				currentLayer = nextLayer;
			}
			//for (int i = 0; i < discovered.length; ++i) {
			//	System.out.println("discovered[" + i + "] = " + (discovered[i] ? "true" : "false"));
			//}
			System.out.println(discovered[secondIndex] ? distance : -1);
		}
	}

}
