import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Controller {
	BinSearchTree bst;
	
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
				JOptionPane.showMessageDialog(null, "Id: " + nodeFind.data.id + "\nFaculty: " + nodeFind.data.faculty + "\nMajor: " + nodeFind.data.major + "\nYear: " + nodeFind.data.year, "Search Results", JOptionPane.INFORMATION_MESSAGE, null);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Please Create a Tree before using Insert","Error", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public void browsePressed() {
	
	}

	public void createTreePressed() {
		String fileName = JOptionPane.showInputDialog("Enter the file name:");
		if(fileName != null) {
			DataBaseReader createTree = new DataBaseReader(fileName, bst);
			createTree.readFromFile();
		}
	}
}