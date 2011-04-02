import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.lang.StringBuilder;
import java.io.FileNotFoundException;
import java.util.TreeSet;

public class GorillaCucumber {
	private TreeSet<PathNode> path;
	private Blosum blosum;
	private static final char MISS_CHAR = '-';

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Missing arguments: <BLOSUM> <INFILE>");
			System.exit(1);
		}
		Parser parser = null;
		try {
			parser = new Parser(args[0], args[1]);
		} catch (FileNotFoundException e) {
			System.err.println("Could not read the files.");
			System.exit(1);
		}
		parser.parse();
		new GorillaCucumber(parser.getAnimals(), parser.getBlosum() );
	}

	public GorillaCucumber(Animal[] animals, Blosum blosum) {
		this.blosum = blosum;
		String dnaFirst;
		String[] result;
		for (int i = 0; i < animals.length; ++i) {
			dnaFirst = animals[i].getDNA();
			for (int j  = i + 1; j < animals.length; ++j) {
				result = alignTheseStrings(dnaFirst, animals[j].getDNA());		
				System.out.println(animals[i] + "--" + animals[j] + ": " + result[0]);
				System.out.println(result[1]);
				System.out.println(result[2]);
			}
		}
	}

	private String[] alignTheseStrings(String x, String y) {
		path = new TreeSet<PathNode>();
		divideAndConquerAlignment(x, y, 0, 0);
		/* align(x, y, 0, 0); */

		StringBuilder xBuilder = new StringBuilder();
		StringBuilder yBuilder = new StringBuilder();
		int xPos = 0; 
		int yPos = 0;
		int alignCost = 0;
		short relation;

		Iterator<PathNode> iterator = path.iterator();
		PathNode fromNode = iterator.next();
		PathNode toNode;
		char xChar, yChar;
		while (iterator.hasNext()) {
			toNode = iterator.next();
			relation = toNode.posRelativeTo(fromNode);
			if (relation == PathNode.ROW_DIFF) {
				alignCost += blosum.deltaCost();
				xBuilder.append(x.charAt(xPos++));
				yBuilder.append(MISS_CHAR);
			} else if (relation == PathNode.COL_DIFF) {
				alignCost += blosum.deltaCost();
				xBuilder.append(MISS_CHAR);
				yBuilder.append(y.charAt(yPos++));
			} else {
				xChar = x.charAt(xPos++);
				yChar = y.charAt(yPos++);
				alignCost += blosum.alignCostOf(xChar, yChar); 
				xBuilder.append(xChar);
				yBuilder.append(yChar);
			}
			fromNode = toNode;
		}
		String[] result = new String[3];
		result[0] = String.valueOf(alignCost);
		result[1] = xBuilder.toString();
		result[2] = yBuilder.toString();
		return result;
	}

	public void divideAndConquerAlignment(String x, String y, int xPos, int yPos) {
		int xLen = x.length();
		int yLen = y.length();
		if (xLen <= 2 || yLen <= 2) {
			align(x, y, xPos, yPos);
		} else {
			int q = 0;
			int alignCost;
			int maxAlign = Integer.MIN_VALUE;
			String leftX, rightX, leftY, rightY;
			int[][] leftAlign, rightAlign;
			List<PathNode> maxNodes = new LinkedList<PathNode>();
			for (int i = 0; i < xLen; ++i) {
				leftX = x.substring(0, i);
				rightX = new StringBuilder(x.substring(i, xLen)).reverse().toString();
				leftY = y.substring(0, (yLen - 1) / 2);
				rightY = new StringBuilder(y.substring((yLen - 1) / 2, yLen)).reverse().toString();
				leftAlign = spaceEfficientAlignment(leftX, leftY);
				rightAlign = spaceEfficientAlignment(rightX, rightY);

				alignCost = leftAlign[leftAlign.length - 1][1] + rightAlign[rightAlign.length - 1][1];
				/* if (alignCost == maxAlign) { */
				/*	q = i; */
				/*	maxNodes.add(new PathNode(xPos + i, yPos + (yLen - 1) / 2)); */
				/* } else  */
					/* if (alignCost >= maxAlign) { */
					if (alignCost > maxAlign) {
					q = i;
					maxNodes.clear();
					maxAlign = alignCost;
					maxNodes.add(new PathNode(xPos + i, yPos + (yLen - 1) / 2));
				}
			}
			path.addAll(maxNodes);
			divideAndConquerAlignment(x.substring(0, q), y.substring(0, (yLen - 1) / 2), xPos, yPos);
			divideAndConquerAlignment(x.substring(q, xLen), y.substring((yLen - 1)/ 2, yLen), xPos + q, yPos + (yLen - 1) / 2);
		}
	}

	private void align(String x, String y, int xPos, int yPos) {
		int xLen = x.length();
		int yLen = y.length();
		int[][] alignment = new int[xLen + 1][yLen + 1];
		for (int i = 0; i < alignment.length; ++i) {
			alignment[i][0] = i * blosum.deltaCost();
		}
		for (int j = 0; j < alignment[0].length; ++j) {
			alignment[0][j] =  j * blosum.deltaCost();
		}
		int alpha, firstCost, secondCost, thirdCost;
		for (int j = 1; j < alignment[0].length; ++j) {
			for (int i = 1; i < alignment.length; ++i) {
				alpha = blosum.alignCostOf(x.charAt(i - 1), y.charAt(j - 1));
				firstCost = alpha + alignment[i - 1][j - 1];
				secondCost = blosum.deltaCost() + alignment[i - 1][j];
				thirdCost = blosum.deltaCost() + alignment[i][j - 1];
				alignment[i][j] = max(firstCost, secondCost, thirdCost);
			}
		}

		int i = alignment.length - 1;
		int j = alignment[0].length - 1;
		while (i >= 0 && j >= 0) {
			PathNode pn = new PathNode(xPos + i, yPos + j);
			path.add(pn);
			if (i == 0) {
				j--;
			} else if (j == 0) {
				i--;
			} else if (alignment[i][j] == alignment[i - 1][j - 1] + blosum.alignCostOf(x.charAt(i - 1), y.charAt(j - 1))) {
				i--;
				j--;
			} else if (alignment[i][j] == alignment[i - 1][j] + blosum.deltaCost()) {
				i--;
			} else if (alignment[i][j] == alignment[i][j - 1] + blosum.deltaCost()) {
				j--;
			}
		}
	}

	private int[][] spaceEfficientAlignment(String x, String y) {
		int[][] align = new int[x.length() + 1][2];
		for (int i = 0; i < (x.length() + 1); ++i) {
			align[i][0] = i * blosum.deltaCost();
		}
		for (int j = 1; j < (y.length() + 1); ++j) {
			align[0][1] = j * blosum.deltaCost();
			for (int i = 1; i < align.length; ++i) {
				align[i][1] = max(
						blosum.alignCostOf(x.charAt(i - 1), y.charAt(j - 1))
								+ align[i - 1][0], blosum.deltaCost()
								+ align[i - 1][1], blosum.deltaCost()
								+ align[i][0]);
			}
			for (int k = 0; k < align.length; ++k) {
				align[k][0] = align[k][1];
			}
		}
		return align;
	}

	private int max(int i, int j, int z) {
		return Math.max(i, Math.max(j, z));
	}
}
