import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



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
		String[] seperated = line.trim().split("\\s+");
		theTree.insert(seperated[0], seperated[1], seperated[2], seperated[3]);
	}
}
