public class Pair {
	private Word first, second;
	
	public Pair(Word first, Word second){
		this.first = first;
		this.second = second;
	}
	
	public Word getFirst() {
		return first;
	}
	public Word getSecond() {
		return second;
	}

	public String toString() {
		return "{" + first + ", " + second + "}";
	}
}
