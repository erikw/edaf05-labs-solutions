import java.lang.Comparable;

public class PathNode implements Comparable<PathNode> { 
	public static final short ROW_DIFF = 1;
	public static final short COL_DIFF = 2;
	public static final short DIA_DIFF = 3;
	public static final PathNode NIL = new PathNode (0, 0);

	private int row;
	private int col;

	 public PathNode(int row, int col) {
		this.row = row;
		this.col = col;
	 }
	 
	public short posRelativeTo(PathNode otherNode) {
		if ((row == otherNode.row + 1) && (col == otherNode.col)) {
			return ROW_DIFF;
		} else if ((row == otherNode.row) && (col == otherNode.col + 1)) {
			return COL_DIFF;
		} else if ((row == otherNode.row + 1) && (col == otherNode.col + 1)) {
			return DIA_DIFF;
		} else {
			return -1;
		}
	}

	public int compareTo(PathNode rhs) {
		int colDiff = col - rhs.col;
		if (colDiff == 0) {
			return row -rhs.row;
		} else {
			return colDiff;
		}
	}

	public boolean equals(Object cmpObj) {
		PathNode rhs = null;
		try {
			rhs = (PathNode) cmpObj;
		} catch (ClassCastException e) {
			System.err.println("Tried to compare something else than a path-node with a path-node.");
			System.exit(1);
		}
		return compareTo(rhs) == 0;
	}

	public String toString() {
		return "(" + row + ", " + col + ")";
	}
}
