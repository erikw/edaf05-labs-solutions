import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

public class Parser {
	private Scanner scanner;
	private int largestCapacity;

	public Parser() {
		scanner = new Scanner(System.in);
		largestCapacity = 0;
	}

	public Parser (String fileName) throws FileNotFoundException {
		scanner = new Scanner(new File(fileName));
		largestCapacity = 0;
	}

	public ResidualGraph getResidualGraph() {
		HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
		
		int nbrNodes = Integer.parseInt(scanner.nextLine());
		for (int i = 0; i < nbrNodes; ++i) {
			scanner.nextLine();
			nodes.put(i, new Node(i));
		}
	
		int nbrEdges = Integer.parseInt(scanner.nextLine());
		String line;
		String[] parts;
		int tailNbr, headNbr, capacity;
		Edge forwardEdge1, backwardEdge1;
		Edge forwardEdge2, backwardEdge2;
		Node tail, head;
		for (int i = 0; i < nbrEdges; ++i) {
			line = scanner.nextLine();
			parts = line.split(" ");
			tailNbr = Integer.parseInt(parts[0]);
			headNbr = Integer.parseInt(parts[1]);
			capacity = Integer.parseInt(parts[2]);
			if (capacity == -1) {
				capacity = Integer.MAX_VALUE;
			} else  if (capacity > largestCapacity) {
				largestCapacity = capacity;
			}
			tail = nodes.get(tailNbr);
			head = nodes.get(headNbr);
			forwardEdge1 = new Edge(tail, head, capacity, capacity);
			backwardEdge1 = new Edge(head, tail, capacity, 0);
			forwardEdge2 = new Edge(head, tail, capacity, capacity);
			backwardEdge2 = new Edge(tail, head, capacity, 0);
			tail.addForwardEdge(forwardEdge1);
			tail.addBackwardEdge(backwardEdge2);
			head.addBackwardEdge(backwardEdge1);
			head.addForwardEdge(forwardEdge2);
		}
		return new ResidualGraph(nodes.get(0), nodes.get(nbrNodes - 1));
	}

	public int getLargestCapacity() {
		return largestCapacity;
	}
}
