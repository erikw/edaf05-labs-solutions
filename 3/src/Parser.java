import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	private Scanner scanner;
	private ArrayList<Node> nodes;
	private Map<String, Node> nodeMap;
	private ArrayList<Edge> edges;

	public Parser() {
		scanner = new Scanner(System.in);
		setUp();
	}

	public Parser(String fileName) throws FileNotFoundException {
		scanner = new Scanner(new File(fileName));
		setUp();
	}

	private void setUp() {
		nodes = new ArrayList<Node>();
		nodeMap = new HashMap<String, Node>();
		edges = new ArrayList<Edge>();
	}

	public void parse() {
		int nodeCount = 0;
		int edgeCount = 0;
		int weigth;
		boolean parsingDistances = false;
		String currLine;
		String[] parts;
		String distance;
		Node node, firstNode, secondNode;
		Pattern distSplitter = Pattern.compile("--|\\s(?=\\[)");
		Pattern distDetecterPatt = Pattern.compile(".*--.*");
		Matcher distDetecter = distDetecterPatt.matcher("dummy-text");
		while (scanner.hasNextLine()) {
			currLine = scanner.nextLine();
			if (!parsingDistances) {
				distDetecter.reset(currLine);
				if (distDetecter.matches()) {
					parsingDistances = true;
				}
			}
			if (parsingDistances) {
				parts = distSplitter.split(currLine);
				firstNode = nodeMap.get(parts[0]);
				secondNode = nodeMap.get(parts[1]);
				distance = parts[2];
				weigth = Integer.parseInt(distance.substring(1,	distance.length() - 1));
				edges.add(edgeCount++, new Edge(firstNode, secondNode, weigth));
			} else {
				node = new Node(nodeCount);
				nodes.add(nodeCount++, node);
				nodeMap.put(currLine.trim(), node);
			}
		}
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}
}
