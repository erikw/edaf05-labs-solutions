public class Edge implements Comparable<Edge> {
	private Node first;
	private Node second;
	private int distance;
	
	public Edge(Node first, Node second, int distance) {
		this.first = first;
		this.second = second;
		this.distance = distance;
	}
	
	public Node getFirst() {
		return first;
	}
	
	public Node getSecond() {
		return second;
	}
	public int getDistance() {
		return distance;
	}

	public int compareTo(Edge rhs) {
		return this.distance - rhs.distance;
	}
	
	public String toString() {
		return first.toString() + " <--> " + second.toString();
	}
}
