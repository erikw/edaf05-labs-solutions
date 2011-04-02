import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictWord {

	public  Map<String, Word> edges;
	private String combo;

	public DictWord(String combo) {
		this.combo = combo;
		edges = new HashMap<String, Word>();
	}

	public void addEdge(Word edge) {
		if (!edges.containsValue(edge)) {
			edges.put(edge.toString(), edge);
		}
	}

	public void putWords(List<Word> nextLayer, boolean[] discovered) {
		for (Word word : edges.values()) {
			if (!discovered[word.getIndex()]) {
				discovered[word.getIndex()] = true;
				nextLayer.add(word);
			}
		}
	}

	public String toString() {
		return combo;
	}
}
