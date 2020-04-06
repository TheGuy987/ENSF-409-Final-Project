import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Controller {
	BinSearchTree bst;
	
	public Controller() {
		bst = null;
	}
	
	public void insertPressed() {
		if(bst==null) {
			JOptionPane.showMessageDialog(null, "Please Create a Tree before using Insert","Error", JOptionPane.PLAIN_MESSAGE);
		}else {
			JDialog insertFields = new JDialog();
			insertFields.setSize(500, 300);
			insertFields.setVisible(true);
		}
	}
	
	public void findPressed() {
		
	}
	
	public void browsePressed() {
		if(bst==null) {
			JOptionPane.showMessageDialog(null, "Please Create a Tree before using Browse","Error", JOptionPane.PLAIN_MESSAGE);
		}else {
			
		}
	}

	public void createTreePressed() {
		String fileName = JOptionPane.showInputDialog("Enter the file name:");
		DataBaseReader createTree = new DataBaseReader(fileName, bst);
		bst = createTree.readFromFile();
	}
}