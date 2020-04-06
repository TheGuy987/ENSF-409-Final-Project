import java.io.IOException;
import java.io.PrintWriter;

public class InputTesting {
	
	public static void main(String[] args) {
		BinSearchTree theTree = new BinSearchTree();
		String name = "input.txt";
		DataBaseReader theReader = new DataBaseReader(name, theTree);
		theReader.readFromFile();
		try {
			theTree.print_tree(theTree.root, new PrintWriter(System.out));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
