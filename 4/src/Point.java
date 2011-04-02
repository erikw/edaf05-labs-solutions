public class Point { 
	private double x;
	private double y;
	private int xPos;

	 public Point(double x, double y) {
		this.x = x;
		this.y = y;
	 }

	 public double sqDistanceTo(Point otherPoint) {
		 if (this == otherPoint) {
			 return 0.0;
		 } else {
			 return Math.pow((this.x - otherPoint.x), 2) + Math.pow((this.y - otherPoint.y), 2);
			 /* return Math.hypot((this.x - otherPoint.x), (this.y - otherPoint.y)); */
		 }
	 }

	public double getXCoord() {
		return x;
	}
	
	public int compareX(Point point){
		return compareCoord(this.x, point.x);
	}
	
	public int compareY(Point point){
		return compareCoord(this.y, point.y);
	}
	
	private int compareCoord(double lhs, double rhs){
		int ret =0;
		double diff = lhs - rhs;
		if (diff > 0) {
			ret = 1;
		} else if (diff < 0){
			ret = -1;
		}
		return ret;
	}
	public int getXpos() {
		return xPos;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public String toString(){
		return "(" +  x + ", " + y + ")";
	}
}
