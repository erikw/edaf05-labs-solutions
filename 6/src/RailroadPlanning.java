import java.io.FileNotFoundException;

public class RailroadPlanning {

	public static void main(String[] args) {
		Parser parser = null;
		if (args.length > 0) {
			try {
				parser = new Parser(args[0]);
			} catch (FileNotFoundException e) {
				System.err.println("Could not read file " + args[0]);
				System.exit(1);
			}
		} else {
			parser = new Parser();
		}
		new RailroadPlanning().run(parser.getResidualGraph(), parser.getLargestCapacity());
	}

	public void run(ResidualGraph residualGraph, int largestCapacity) {
		int capacityLowCut = (int) Math.pow(Math.floor(Math.log(largestCapacity) / Math.log(2)), 2);
		while (capacityLowCut >= 1) {
			while (residualGraph.existsPathToSink(capacityLowCut)) {
				residualGraph.augmentNextPath();
			}
			capacityLowCut /= 2;
		}
		System.out.println(residualGraph.getFlow());
		System.out.println(residualGraph.minCutString());
	}
}
