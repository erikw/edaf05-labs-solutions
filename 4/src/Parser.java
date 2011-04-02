import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

public class Parser {
	private List<Point> xList;
	private List<Point> yList;
	private Scanner scanner;

	public Parser() {
		scanner = new Scanner(System.in);
		setUp();
	}

	public Parser(String fileName) throws FileNotFoundException {
		scanner = new Scanner(new File(fileName));
		setUp();
	}

	private void setUp() {
		xList = new ArrayList<Point>();
		yList = new ArrayList<Point>();
	}
	public List<Point> getXList() {
		return xList;
	}

	public List<Point> getYList() {
		return yList;
	}

	public void parse() {
		String line = "";
		Pattern distPattern = Pattern.compile("^\\s*(?:\\d+|[a-z1-9]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s*$");
		Matcher distMatcher = distPattern.matcher("dummy-text");
		if (scanner.hasNextLine()) {
			do {
				line = scanner.nextLine();
				distMatcher.reset(line);
			} while (!distMatcher.matches());
		}
		double xCoord;
		double yCoord;
		Point point;
		while(scanner.hasNextLine()) {
			distMatcher.reset(line);
			if (distMatcher.matches()) {
				xCoord = Double.parseDouble(distMatcher.group(1));
				yCoord = Double.parseDouble(distMatcher.group(2));
				point = new Point(xCoord, yCoord);
				xList.add(point);
				yList.add(point);
			} 
			line = scanner.nextLine();
		}
	}
}
