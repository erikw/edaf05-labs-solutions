import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Collection;
import java.util.HashMap;

public class Node {
	private int id;
	private Map<Node, Edge> forwardEdges;
	private Map<Node, Edge> backwardEdges;

	public Node(int id) {
		this.id = id;
		forwardEdges = new HashMap<Node, Edge>();
		backwardEdges = new HashMap<Node, Edge>();
	}

	public int pushAdjacentNodes(LinkedList<Node> list, int capacityLowCut) {
		int backwardAdds = pushAdjacentHelper(list, backwardEdges, capacityLowCut);
		return backwardAdds + pushAdjacentHelper(list, forwardEdges, capacityLowCut);	
	}

	private int pushAdjacentHelper(LinkedList<Node> stack, Map<Node, Edge> edges, int capacityLowCut) {
		int nbrPushs = 0;
		for (Edge edge : edges.values()) {
			if (edge.pushHead(stack, capacityLowCut)) {
				++nbrPushs;	
			}
		}
		return nbrPushs;
	}

	public void augmentPathTo(Node adjacentNode, int discrepance) {
		Edge edge = forwardEdges.get(adjacentNode);
		if (edge == null) {
			edge = backwardEdges.get(adjacentNode);
		} 
		if (edge != null) {
			edge.augment(discrepance);
		} 
	}

	public void addEdges(List<Node> list, Collection<Node> discovered) {
		addEdgesHelper(list, discovered, backwardEdges);
		addEdgesHelper(list, discovered, forwardEdges);
	}

	private void addEdgesHelper(List<Node> list, Collection<Node> discovered, Map<Node, Edge> edges) {
		Node head;
		for (Edge edge : edges.values()) {
			head = edge.getHeadIfHasFlow();
			if (head != null && !discovered.contains(head)){
				discovered.add(head);
				list.add(head);
			}
		}
	}

	public void findEdgesNotIn(Collection<Node> nodeSet, Collection<Edge> edgeSet) {
		for (Edge edge: forwardEdges.values()) {
			if (!nodeSet.contains(edge.getHead())) {
				edgeSet.add(edge);
			}
		}
	}

	public int edgeCapacityTo(Node head) {
		int capacity = -1;
		Edge edge = forwardEdges.get(head);
		if (edge == null) {
			edge = backwardEdges.get(head);
		}
		if (edge != null) {
			capacity = edge.getCapacity();
		} 
		return capacity;
	}
	
	public void addForwardEdge(Edge edge) {
		forwardEdges.put(edge.getHead(), edge);
	}
	
	public void addBackwardEdge(Edge edge) {
		backwardEdges.put(edge.getHead(), edge);
	}

	public int nbrEdges() {
		return forwardEdges.size() + backwardEdges.size();
	}
	
	public boolean equals(Object other) {
		Node otherNode = null;
		try {
			otherNode = (Node) other;
		} catch(ClassCastException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return id == otherNode.id;
	}

	public int hashCode() {
		return id;
	}
	
	public String toString() {
		return String.valueOf(id);
	}

	public void putAdjacentNodes(LinkedList<Node> list, Collection<Node> discovered) {
		putAdjacentHelper(list, discovered, forwardEdges);
		putAdjacentHelper(list, discovered, backwardEdges);	
	}

	private void putAdjacentHelper(LinkedList<Node> list, Collection<Node> discovered, Map<Node, Edge> edges) {
		for (Edge edge : edges.values()) {
			edge.putHead(list, discovered);
		}
	}
}
