import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;


/**
 * Class that uses a file to fill a binary Search Tree. It has variables that hold the
 * name of the input file, and an initialized Binary Search Tree to be filled.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public class DataBaseReader {
	
	private BinSearchTree theTree;
	private String fileName;

	/**
	 * Constructor that takes in a file name and a BinSearchTree object. It sets these to
	 * the corrosponding instance variables.
	 * @param fileName String to set variable fileName to.
	 * @param theTree BinSearchTree object that will be populated using the contents of the 
	 * file specified by fileName.
	 */
	public DataBaseReader(String fileName, BinSearchTree theTree) {
		this.fileName = fileName;
		this.theTree = theTree;	
	}
	
	/**
	 * Creates a BefferedReader object to read from the specified file, then goes through the input
	 * file line by line, calling the helper method addToTree for each line read. If the file
	 * specified by variable fileName is not found, the method creates a Jpane to alert the user,
	 * and ends early.
	 */
	public void readFromFile() {
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(new File(fileName)));
		}catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "\nCannot find that file", " Warning",JOptionPane.PLAIN_MESSAGE);
			return;
		}
		
		String line = "";
		
		try {
			while((line = input.readLine()) != null) {
			addToTree(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Helper method called by readFromFile. It seperates a line read from the input
	 * file into ID number, faculty, major, and year of study. Then it creates a new
	 * Node for theTree object using this information.
	 * @param line String holding the contents of one line of the input file.
	 */
	private void addToTree(String line) {
		String[] separated = line.trim().split("\\s+");
		theTree.insert(separated[0], separated[1], separated[2], separated[3]);
	}
}
