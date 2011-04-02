import java.util.Collection;
import java.util.LinkedList;

public class Edge {
	private int realCapacity;
	private int capacity;
	private Node tail;
	private Node head;
	
	public Edge(Node tail, Node head, int realCapacity, int capacity) {
		this.tail = tail;
		this.head = head;
		this.realCapacity = realCapacity;
		this.capacity = capacity;
	}

	public boolean pushHead(LinkedList<Node> stack, int capacityLowCut) {
		boolean success = false;
		if (capacity > 0 && capacity >= capacityLowCut) {
			stack.push(head);
			success = true;
		}
		return success;
	}
	
	public void augment(int discrepance) {
		capacity += discrepance;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getRealCapacity() {
		return realCapacity;
	}
	
	public Node getHead() {
		return head;
	}

	public Node getHeadIfHasFlow() {
		Node result = null;
		if (capacity > 0) {
			result = head;
		} 
		return result;
	}

	public void  putHead(LinkedList<Node> list, Collection<Node> discovered) {
		if (capacity > 0 && !discovered.contains(head)) {
			discovered.add(head);
			list.addLast(head);
		}
	}

	public String toString() {
		return tail + " " + head + " " + realCapacity;
	}
}
