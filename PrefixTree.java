import java.util.LinkedList;

public class PrefixTree {
	public Node root;

	// node subclass
	public class Node {

		public char data;
		public Node[] children;
		public boolean wordEnd;

		public Node(char d) {
			data = d;
			children = new Node[26];
		}
	}

	public PrefixTree() {
		// set the root equal to a null value
		root = new Node('0');
	}

	public PrefixTree(Node rootNode) {
		root = rootNode;
	}

	public void addWord(String word) {
		int levelCounter = 0;
		Node currentNode = root;
		char[] characters = word.toCharArray();

		// make a new node for each character in the word if there's no existing
		// one
		while (levelCounter < characters.length) {
			if (currentNode.children[characters[levelCounter] - 'a'] != null) {
				currentNode = currentNode.children[characters[levelCounter] - 'a'];
				if (levelCounter == characters.length - 1) {
					currentNode.wordEnd = true;
				}
				levelCounter++;
			}

			// add a new node if one doesn't already exist for that character
			else {
				Node addNode = new Node(characters[levelCounter]);
				currentNode.children[characters[levelCounter] - 'a'] = addNode;
				currentNode = addNode;
				if (levelCounter == characters.length - 1) {
					currentNode.wordEnd = true;
				}
				levelCounter++;
			}
		}
	}

	public PrefixTree findPrefix(String prefix) {
		if (this.contains(prefix) == false) {
			return null;
		}

		// find the first letter in the prefix and make a new tree with that as
		// the root
		// set currentNode = root
		char[] characters = prefix.toCharArray();
		int levelCounter = 0;
		Node currentNode = root;
		boolean foundChar = false;
		PrefixTree myTree = null;

		while (levelCounter <= characters.length && foundChar == false) {
			// System.out.println("currentNode.data: " + currentNode.data + "\n"
			// + "characters[characters.length - 1]: " +
			// characters[characters.length - 1]);
			if (currentNode.data == characters[characters.length - 1]) {
				// System.out.println("I got into the if.");
				myTree = new PrefixTree(currentNode);
				foundChar = true;
			} else {
				currentNode = currentNode.children[characters[levelCounter] - 'a'];
				levelCounter++;
			}

		}

		return myTree;

	}

	// kickstarter method for findWords
	public LinkedList<String> findWords() {
		LinkedList<String> words = new LinkedList<String>();
		String s = "";
		findWords(root, words, s);
		return words;
	}

	// recursive findWords
	private void findWords(Node n, LinkedList<String> words, String s) {
		if (n != null) {
			s += n.data;
			if (n.wordEnd == true) {
				words.add(s);
			}
			for (int i = 0; i < n.children.length; i++) {
				if (n.children[i] != null) {
					findWords(n.children[i], words, s);
					// System.out.println("s: " + s);
					// System.out.println("n.data: " + n.data);

				}
			}
		}
	}

	public boolean contains(String word) {
		char[] characters = word.toCharArray();
		int levelCounter = 0;
		Node currentNode = root;

		// for each character, check if that child exists (starting at that
		// root)
		// if it doesn't, return false;
		while (levelCounter < characters.length) {
			if (currentNode.children[characters[levelCounter] - 'a'] == null) {
				return false;
			} else {
				currentNode = currentNode.children[characters[levelCounter] - 'a'];
				levelCounter++;
			}
		}

		return true;
	}

	// tester method for just this class
	public static void main(String[] args) {
		PrefixTree myTree = new PrefixTree();
		myTree.addWord("dogs");
		myTree.addWord("donut");
		myTree.addWord("dogged");
		System.out.println("myTree.contains('dogs'): "
				+ myTree.contains("dogs"));
		System.out.println("myTree.contains('dots'): "
				+ myTree.contains("dots"));
		System.out.println("myTree.contains('dogged'): "
				+ myTree.contains("dogged"));
		myTree.findPrefix("dog");

	}
}
