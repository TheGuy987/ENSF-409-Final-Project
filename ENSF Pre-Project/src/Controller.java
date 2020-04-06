  
import java.awt.FlowLayout;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;

public class Controller {
	BinSearchTree bst;
	GUI mainPane;
	
	public Controller(GUI mainPane) {
		bst = new BinSearchTree();
		this.mainPane = mainPane;
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
			JOptionPane.showMessageDialog(null, "Please Creste a Tree before using Fine", "Error", JOptionPane.PLAIN_MESSAGE);
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