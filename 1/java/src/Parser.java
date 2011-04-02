import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Parser {
	private String line = "";
	private Scanner scanner;
	private ArrayList<Man> men;
	private Woman[] women;

	public Parser() {
		scanner = new Scanner(System.in);
	}

	public Parser(String fileName) throws FileNotFoundException {
		scanner = new Scanner(new File(fileName));
	}



	public void  parse() {
		fetchLine();
		while (line.matches("^\\s*#.*$") && scanner.hasNextLine()) {
			fetchLine();
		}

		int n = Integer.parseInt(line.split("=\\s?", -1)[1].trim());
		women = new Woman[n];
		men = new ArrayList<Man>(n);

		for (int i = 0; i < (2 * n); ++i) {
			fetchLine();
			String[] parts = line.split("\\s", 2);
			int id = Integer.parseInt(parts[0]);
			String name = parts[1];
			if ((id % 2) == 0) {
				id = (id / 2 ) -1;
				women[id] = new Woman(id, name);
			} else {
				id /= 2;
				men.add(id, new Man(id, name));
			}
		}
		fetchLine();
		for (int i = 0; i < (2 * n); ++i) {
			fetchLine();
			String[] parts = line.split(":?\\s", n + 1);
			int id = Integer.parseInt(parts[0]);
			if ((id % 2) == 0) {
				id = (id / 2) - 1;
				int[] prefs = new int[n];
				for (int j = 0; j < n; ++j) {
					int manID = (Integer.parseInt(parts[j + 1].trim())) / 2;
					prefs[manID] = j;
				}
				women[id].setPrefs(prefs);
			} else  {
				id /= 2;
				LinkedList<Woman> prefs = new LinkedList<Woman>();
				for (int j = 0; j < n; ++j) {
					int womanID = (Integer.parseInt(parts[j + 1].trim()) / 2) - 1;
					prefs.addLast(women[womanID]);
				}
				men.get(id).setPrefs(prefs);
			}
		}
		scanner.close();
	}

	public ArrayList<Man> getMen() {
		return men;
	}

	public Woman[] getWomen() {
		return women;
	}

	private void fetchLine() {
		line = scanner.nextLine();
	}
}
