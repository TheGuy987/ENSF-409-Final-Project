import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Controller {
	private BinSearchTree bst;
	
	public Controller() {
		bst = new BinSearchTree();
	}
	
	public void insertPressed() {
		if(bst.root==null) {
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
		if(bst.root==null) {
			JOptionPane.showMessageDialog(null, "Please Create a Tree before using Browse","Error", JOptionPane.PLAIN_MESSAGE);
		}else {
			
		}
	}

	public void createTreePressed() {
		String fileName = JOptionPane.showInputDialog("Enter the file name:");
		DataBaseReader createTree = new DataBaseReader(fileName, bst);
		createTree.readFromFile();
	}
}