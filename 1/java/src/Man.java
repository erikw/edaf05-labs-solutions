import java.util.List;

public class Man extends Person {
	private Woman wife;
	private List<Woman> prefs;

	public Man(int id, String name) {
		super(id, name);
	}

	public Man proposeNextHottie() {
		Woman target = prefs.remove(0);
		return target.recieveProposalFrom(this);
	}

	public void setPrefs(List<Woman> prefs) {
		this.prefs = prefs;
	}

	public void engage(Woman woman) {
		wife = woman;
	}
	public void dumped() {
		wife = null;
	}

	public String toString() {
		return name + " -- " + wife.name;
	}
}
