import java.lang.StringBuilder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;

public class ResidualGraph {
	private Node source;
	private Node sink;
	private LinkedList<Node> nextPath;
	private int flow;

	public ResidualGraph(Node source, Node sink) {
		this.source = source;
		this.sink = sink;
		flow = 0;
	}

	public boolean existsPathToSink(int capacityLowCut) {
		Collection<Node> discovered = new HashSet<Node>();
		LinkedList<Node> dfsStack = new LinkedList<Node>();
		dfsStack.add(source);
		nextPath = sinkDFS(discovered, dfsStack, capacityLowCut);
		return nextPath != null;
	}

	public LinkedList<Node> sinkDFS(Collection<Node> discovered, LinkedList<Node> dfsStack, int capacityLowCut) {
		LinkedList<Node> result = null;
		/* if (!dfsStack.isEmpty()) { */
		Node current = dfsStack.pop();
		if (current == sink) {
			result = new LinkedList<Node>();
			result.addLast(sink);
		} else if (!discovered.contains(current)){
			discovered.add(current);
			int nbrPushedNodes = current.pushAdjacentNodes(dfsStack, capacityLowCut);
			int i = 0;
			while (result == null && i++ < nbrPushedNodes) {
				result = sinkDFS(discovered, dfsStack, capacityLowCut);
			}
			if (result != null) {
				result.addFirst(current);
			}
		}
		/* } */
		return result;
	}

	public void augmentNextPath() {
		int bottleneck = bottleneck();
		Node prev = nextPath.get(0);
		Node next;
		for (int i = 1; i < nextPath.size() - 1; ++i){
			next = nextPath.get(i);
			prev.augmentPathTo(next, -bottleneck);
			next.augmentPathTo(prev, bottleneck);
			prev = next;
		}
		flow += bottleneck;
	}
	
	private int bottleneck() {
		int bottleneck = Integer.MAX_VALUE;
		Node prev = nextPath.get(0);
		Node next;
		for (int i = 1; i < nextPath.size() - 1; ++i){
			next = nextPath.get(i);
			int cap = prev.edgeCapacityTo(next);
			if (cap < bottleneck){
				bottleneck = cap;
			}
			prev = next;
		}
		return bottleneck;
	}

	public String minCutString() {
		Collection<Node> sourceSet = new HashSet<Node>();
		Collection<Edge> minCutEdges = new LinkedList<Edge>();
		List<Node> currentLayer = new LinkedList<Node>();
		List<Node> nextLayer;
		currentLayer.add(source);
		sourceSet.add(source);
		while (!currentLayer.isEmpty()){
			nextLayer = new LinkedList<Node>();
			for (Node node : currentLayer){
				node.addEdges(nextLayer, sourceSet);
			}
			currentLayer = nextLayer;
		}
		
		for (Node node : sourceSet) {
			node.findEdgesNotIn(sourceSet, minCutEdges);
		}

		StringBuilder sb = new StringBuilder();
		int minCapacity = 0;
		for (Edge minEdge : minCutEdges) {
			sb.append(minEdge).append('\n');
			minCapacity += minEdge.getRealCapacity();
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		/* sb.append("Summation minCutEdgesCapacity = " + minCapacity); */
		return sb.toString();
	}

	public int getFlow() {
		return flow;
	}
}
