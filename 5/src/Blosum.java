public class Blosum {
	private short deltaCost;
	private short[][] cost;
	
	public Blosum(short deltaCost, short[][] costMat) {
		this.deltaCost = deltaCost;		
		this.cost = costMat;
	}

	public short deltaCost() {
		return deltaCost;
	}

	public short alignCostOf(char from, char to) {
		return cost[from -'A'][to - 'A'];
	}
}
