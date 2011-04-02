import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

public class Word {
	private String word;
	public List<DictWord> edges;
	private int index;

	public Word(String word, int index, HashMap<String, DictWord> dictionary) {
		this.word = word;
		this.index = index;
		edges = new LinkedList<DictWord>();

	for (int i = 1; i < 5; ++i) {
			String part = word.substring(0, i) + word.substring(i + 1);
			arcify(dictionary, false, part.toCharArray(), 0);
	}
		arcify(dictionary, true, word.substring(1).toCharArray(), 0);
		
	}
	
	private void arcify(HashMap<String, DictWord> dictionary, boolean linktoDict,
			char[] chars, int start) {
		int end = chars.length;
		if (start == end) {
			String combo = new String(chars);
			DictWord dict = dictionary.get(combo);
			if (dict == null) {
				dict = new DictWord(combo);			
				dictionary.put(combo, dict);
			}
			if (linktoDict) {
				// Create an edge from this word to the dictword. 
				edges.add(dict);
			}
			// Create an directed edge from the dictword to this word.
			dict.addEdge(this);
		} else {
			for (int i = start; i < end; ++i) {
				swapChars(chars, start, i);
				arcify(dictionary, linktoDict, chars, start + 1);
				swapChars(chars, start, i);
			}
		}
	}

	private void swapChars(char[] chars, int pos1, int pos2) {
		char tmp = chars[pos1];
		chars[pos1] = chars[pos2];
		chars[pos2] = tmp;
	}

	public void putAdjacentWords(List<Word> nextLayer, boolean[] discovered) {
		for (DictWord dictWord : edges) {
			dictWord.putWords(nextLayer, discovered);
		}
	}

	public int getIndex() {
		return index;
	}
	
	public String toString() {
		return word;
	}
}
