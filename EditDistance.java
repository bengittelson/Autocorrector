
public class EditDistance {
	public static int editDistance(String s, String t) {

		// initialize the table:
		int[][] myTable = new int[s.length() + 1][t.length() + 1];
		for (int i = 0; i < s.length() + 1; i++) {
			myTable[i][0] = i;
		}

		for (int j = 0; j < t.length() + 1; j++) {
			myTable[0][j] = j;
		}

		// dynamic programming array:
		for (int i = 1; i < s.length() + 1; i++) {
			for (int j = 1; j < t.length() + 1; j++) {
				int a = myTable[i - 1][j] + 1;
				int b = myTable[i][j - 1] + 1;
				int c = myTable[i - 1][j - 1];
				// System.out.println("s.charAt(i - 1): " + s.charAt(i - 1) +
				// "\n" + "t.charAt(j - 1): " + t.charAt(j - 1));
				// System.out.println("i: " + i + "\n" + "j: " + j);
				if (s.charAt(i - 1) != t.charAt(j - 1)) {
					c = c + 1;
				}
				myTable[i][j] = Math.min(a, Math.min(b, c));

			}
		}

		// System.out.println(Arrays.deepToString(myTable));
		return myTable[s.length()][t.length()];

	}

	// main method for testing purposes
	public static void main(String[] args) {
		System.out.println(editDistance("Saturday", "Sunday"));
	}
}
