import java.util.ArrayList;
import java.util.Collections;
import java.io.FileNotFoundException;

public class SpanningUSA {

	public static void main(String[] args) {
		Parser parser = null;
		if (args.length == 1) {
			try {
				parser = new Parser(args[0]);
			} catch (FileNotFoundException e) {
				System.err.println("The file " + args[0] + " could not be read.");
			}
		} else {
			parser = new Parser();			
		}
		parser.parse();
		new SpanningUSA().run(parser.getNodes(), parser.getEdges());
	}

	public void run(ArrayList<Node> nodes, ArrayList<Edge> edges) {
		Collections.sort(edges);
		DisjointSet dSet = new DisjointSet(nodes);
		Node firstSet, secondSet;
		int totWeight = 0;
		for (Edge edge : edges) {
			firstSet = dSet.find(edge.getFirst());
			secondSet = dSet.find(edge.getSecond());
			if (firstSet != secondSet) {
				totWeight += edge.getDistance();
				dSet.union(firstSet, secondSet);
			}
		}
		System.out.println(totWeight);
	}
}
