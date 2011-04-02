import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ClosestPair {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Parser parser = null;
		if (args.length == 1) {
			try {
				parser = new Parser(args[0]);
			} catch (FileNotFoundException e) {
				System.err.println("Could not read file " + args[0]);
			}
		} else {
			parser = new Parser();
		}
		parser.parse();
		List<Point> xList = parser.getXList();
		List<Point> yList = parser.getYList();
		new ClosestPair().run(xList, yList);
	}

	public void run(List<Point> xList, List<Point> yList) {
		Collections.sort(xList, new XComparator());
		Collections.sort(yList, new YComparator());
		for (int i = 0; i < xList.size(); ++i) {
			xList.get(i).setXPos(i);
		}
		System.out.printf("%.14f", Math.sqrt(minDistance(xList, yList)));
	}

	public double minDistance(List<Point> xList, List<Point> yList) {
		double result = -1.0;
		if (xList.size() <= 3) {
			result = bruteForce(xList);
		} else {
			List<Point> leftPointsX = new ArrayList<Point>();
			List<Point> leftPointsY = new ArrayList<Point>();
			List<Point> rightPointsX = new ArrayList<Point>();
			List<Point> rightPointsY = new ArrayList<Point>();
			double xDivider = xList.get(xList.size() / 2).getXCoord();
			Point currentPoint;
			for (int i = 0; i < xList.size(); ++i) {
				currentPoint = xList.get(i);
				if (i <= (xList.size() / 2)) {
					leftPointsX.add(currentPoint);
				} else {
					rightPointsX.add(currentPoint);
				}
			}
			double xCoord;
			for (Point point : yList) {
				xCoord = point.getXCoord();
				if (xCoord <= xDivider) {
					leftPointsY.add(point);
				} else {
					rightPointsY.add(point);
				}
			}

			double deltaLeft = minDistance(leftPointsX, leftPointsY);
			double deltaRight = minDistance(rightPointsX, rightPointsY);
			double prevDelta = Math.min(deltaLeft, deltaRight);
			double leftBound = xDivider - Math.sqrt(prevDelta);
			double rightBound = xDivider + Math.sqrt(prevDelta);

			List<Point> closePoints = new ArrayList<Point>();
			for (Point point : yList) {
				xCoord = point.getXCoord();
				if ((xCoord > leftBound) && (xCoord < rightBound)) {
					closePoints.add(point);
				}
			}
			double currDelta = Double.MAX_VALUE;
			double compDistance;
			Point compPoint;
			for (int i = 0; i < (closePoints.size() - 1); ++i) {
				int j = 1;
				currentPoint = closePoints.get(i);
				while (j <= 7 && ((i + j) < closePoints.size())) {
					compPoint = closePoints.get(i + j++);
					compDistance = currentPoint.sqDistanceTo(compPoint);
					if (compDistance < currDelta) {
						currDelta = compDistance;
					}
				}
			}
			result = (prevDelta < currDelta) ? prevDelta : currDelta;
		}
		return result;
	}

	private double bruteForce(List<Point> xList) {
		double minDist = -1.0;
		int xSize = xList.size();
		if (xSize == 1) {
			minDist = Double.MAX_VALUE;
		} else if (xSize == 2) {
			minDist = xList.get(0).sqDistanceTo(xList.get(1));
		} else if (xSize == 3) {
			Point p1 = xList.get(0);
			Point p2 = xList.get(1);
			Point p3 = xList.get(2);
			double dist1 = p1.sqDistanceTo(p2);
			double dist2 = p1.sqDistanceTo(p3);
			double dist3 = p2.sqDistanceTo(p3);
			minDist= min(dist1, dist2, dist3);
		}
		return minDist;
	}
	private double min(double d1, double d2, double d3) {
		return Math.min(d1, Math.min(d2, d3));
	}
}
