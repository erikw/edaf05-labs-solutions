import java.util.Comparator;

public class XComparator implements Comparator<Point>{

	public int compare(Point lhs, Point rhs) {
		return lhs.compareX(rhs);
	}
}
