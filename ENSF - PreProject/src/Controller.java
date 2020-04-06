import java.awt.FlowLayout;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;

public class Controller {
	BinSearchTree bst;
	GUI mainPane;
	
	public Controller(GUI mainPane) {
		bst = new BinSearchTree();
		this.mainPane = mainPane;
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
		// Tree exists
		if(bst.root!=null) {
			String idToSearch = JOptionPane.showInputDialog("Please enter the student's id:");
			Node nodeFind = bst.find(bst.root, idToSearch);
			
			// Record exists
			if(nodeFind != null) {
				JOptionPane.showMessageDialog(null, "Id: " + nodeFind.data.id + "\nFaculty: " + nodeFind.data.faculty + "\nMajor: " + nodeFind.data.major + "\nYear: " + nodeFind.data.year, "Search Results", JOptionPane.INFORMATION_MESSAGE, null);
			}
			// Record doesn't exists
			else {
				JOptionPane.showMessageDialog(null, "Error: Record not found!", "Error", JOptionPane.INFORMATION_MESSAGE, null);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Please Create a Tree before using Insert", "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public void browsePressed() {
		mainPane.remove(mainPane.display);
		if(bst.root != null)
			mainPane.display = new JScrollPane(updateTextArea(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		else
			mainPane.display = new JScrollPane(new JTextArea("",23,62), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainPane.display.setSize(300, 20);
		mainPane.add("Center",mainPane.display);
        mainPane.add(mainPane.display);
        mainPane.pack();
	}
	
	public JTextArea updateTextArea() {
		StringWriter out = new StringWriter();
		PrintWriter writer = new PrintWriter(out);
		try {
			bst.print_tree(bst.root, writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JTextArea jta = new JTextArea(out.toString(), 23, 62);
		return jta;
	}

	public void createTreePressed() {
		String fileName = JOptionPane.showInputDialog("Enter the file name:");
		if(fileName != null) {
			DataBaseReader createTree = new DataBaseReader(fileName, bst);
			createTree.readFromFile();
		}
	}
}