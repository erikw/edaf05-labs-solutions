import java.util.Comparator;

public class YComparator implements Comparator<Point>{
	
	public int compare(Point lhs, Point rhs) {
		return lhs.compareY(rhs);
	}
}
