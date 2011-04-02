import java.util.List;

public class Woman extends Person {
	 private Man husband;
	// man nummer x finns på prefs(x) med dess prio. Lägst = högst prio
	private int[] prefs;	

	public Woman(int id, String name) {
		super(id, name);
	}

	public Man recieveProposalFrom(Man proposer) {
		if (husband == null) {
			husband = proposer;
			proposer.engage(this);
			return null;
		} else {
			int proposerPos  = prefs[proposer.id];
			int husbandPos = prefs[husband.id];
			if (proposerPos < husbandPos) {
				husband.dumped();
				Man dumpedMan = husband;
				proposer.engage(this);
				husband = proposer;
				return dumpedMan;
			} else {
				return proposer;
			}
		}
	}

	public void setPrefs(int[] prefs) {
		this.prefs = prefs;
	}
}
