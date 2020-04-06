import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;



public class DataBaseReader {
	
	private BinSearchTree theTree;
	private String fileName;

	public DataBaseReader(String fileName, BinSearchTree theTree) {
		this.fileName = fileName;
		this.theTree = theTree;	
	}
	
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
	
	private void addToTree(String line) {
		String[] separated = line.trim().split("\\s+");
		theTree.insert(separated[0], separated[1], separated[2], separated[3]);
	}
}
