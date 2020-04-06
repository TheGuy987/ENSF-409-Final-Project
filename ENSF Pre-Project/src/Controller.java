import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Controller {
	BinSearchTree bst;
	
	public Controller() {
		bst = new BinSearchTree();
	}
	
	public void insertPressed() {
		JPanel myPanel = new JPanel();
			
		JLabel idPrompt = new JLabel("Enter the Student ID");
		JLabel facultyPrompt = new JLabel("Enter Faculty");
		JLabel majorPrompt = new JLabel("Enter Student's Major");
		JLabel yearPrompt = new JLabel("Enter year");
			
		JTextField idField = new JTextField(5);
		JTextField facultyField = new JTextField(2);
		JTextField majorField = new JTextField(4);
		JTextField yearField = new JTextField(1);
			
		myPanel.add(idPrompt);
		myPanel.add(idField);
		
		myPanel.add(facultyPrompt);
		myPanel.add(facultyField);
		
		myPanel.add(majorPrompt);
		myPanel.add(majorField);
		
		myPanel.add(yearPrompt);
		myPanel.add(yearField);

		int check = JOptionPane.showConfirmDialog(null, myPanel,"Insert a New Node", JOptionPane.OK_CANCEL_OPTION);
		if(check==JOptionPane.OK_OPTION) {
			bst.insert(idField.getText(), facultyField.getText(), majorField.getText(), yearField.getText());
			JOptionPane.showMessageDialog(null, "Your Node has been added for student "+idField.getText(), "Insert", JOptionPane.PLAIN_MESSAGE);
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
	
	}

	public void createTreePressed() {
		String fileName = JOptionPane.showInputDialog("Enter the file name:");
		if(fileName != null) {
			DataBaseReader createTree = new DataBaseReader(fileName, bst);
			createTree.readFromFile();
		}
	}
}