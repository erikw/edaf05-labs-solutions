import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class StableMatch {
	private Parser parser;
	private ArrayList<Man> men;

	public StableMatch() {
		parser = new Parser();

	}
	public StableMatch(String fileName) {
		try {
		parser = new Parser(fileName);
		} catch (FileNotFoundException e) {
			System.err.println("The file '" + fileName + "' was not found");
		}
	}

	public void run() {
		parser.parse();
		men = parser.getMen();
		List<Man> singleMen = new LinkedList<Man>();
		singleMen.addAll(men);
		Woman[]	women = parser.getWomen();
		Man currentMan;
		Man newSingle;
		while(!singleMen.isEmpty()) {
			currentMan = singleMen.remove(0);
			newSingle = currentMan.proposeNextHottie();
			if  (newSingle != null) {
				singleMen.add(newSingle);
			}
		}
		printResult();
	}


	private void printResult() {
		for(Man man : men) {
			System.out.println(man);
		}
	}


	public static void main(String[] args) {
		if (args.length > 0) {
			new StableMatch(args[0]).run();
		} else {
			new StableMatch().run();
		}
	}
}
