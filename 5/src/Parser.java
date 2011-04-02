import java.io.FileNotFoundException;
import java.io.File;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Parser { 
	private ArrayList<Animal> animals;
	private Blosum blosum;
	private Scanner blosumScanner;
	private Scanner dnaScanner;

	 public Parser(String blosumFleName, String dnaFileName) throws FileNotFoundException {
		animals = new ArrayList<Animal>();
		blosumScanner  = new Scanner(new File(blosumFleName));
		dnaScanner = new Scanner(new File(dnaFileName));
	 }

	 public Animal[] getAnimals() {
		 Animal[] resArray = new Animal[animals.size()];
		 return animals.toArray(resArray);
	 }

	 public Blosum getBlosum() {
		 return blosum;
	 }

	 public void parse() {
		String name, dna;
		Pattern namePattern = Pattern.compile("^>([-\\p{L}]+?)\\s.*$", Pattern.MULTILINE);
		Matcher nameMatcher = namePattern.matcher("dummy-text");
		String line = dnaScanner.nextLine();
		while (dnaScanner.hasNextLine()) {
			nameMatcher.reset(line).matches();
			name = nameMatcher.group(1);
			dna = dnaScanner.nextLine();
			if (dnaScanner.hasNextLine()) {
				line = dnaScanner.nextLine();

				if (!nameMatcher.reset(line).matches()) {
					dna += line;
					dna += dnaScanner.nextLine();
					if (dnaScanner.hasNextLine() ) {
						line = dnaScanner.nextLine();

					}
				}
			}
			animals.add(new Animal(name, dna));
		}

		for (int i = 0; i < 6; ++i) {
			blosumScanner.nextLine();
		}
		short[][] cost = new short[26][26];
		String charLine = blosumScanner.nextLine();
		String[] charOrder = charLine.split("\\s+\\*?");
		String[] parts = new String[0];
		for (int i = 0; i < 23; ++i) {
			line = blosumScanner.nextLine();
			parts = line.split("\\s+");
			char lineCh = parts[0].charAt(0);
			for (int j = 1; j < (parts.length - 1); ++j) {
				cost[lineCh - 'A'][charOrder[j].charAt(0) - 'A'] = Short.parseShort(parts[j]);
			}
		}
		short delta = Short.parseShort(parts[parts.length - 1]);
		blosum = new Blosum(delta, cost);

		/* String chst = ""; */
		/* for (int i = 0; i < 26; ++i) { */
		/* 	chst += "  " + (char) ('A' + i); */
		/* } */
		/* System.out.println(" " + chst); */
		/* for (int i = 0; i < cost.length; ++i) { */
		/* 	String lin = (char) ('A' + i) + "" ; */
		/* 	for (int j = 0; j < cost[0].length; ++j) { */
		/* 		if (cost[i][j] >= 0) { */
		/* 			lin += "  " + (cost[i][j]); */
		/* 		} else { */
		/* 			lin += " " + (cost[i][j]); */
		/* 		} */
		/* 	} */
		/* 	System.out.println(lin); */
		/* } */
	 }
}
