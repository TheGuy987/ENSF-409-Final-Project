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
		
	}
	
	public void browsePressed() {
	
	}

	public void createTreePressed() {
		JPanel myPanel = new JPanel();
		JTextField filenameField = new JTextField(10);
		JLabel filenamePrompt = new JLabel("Enter the file name:");
		myPanel.add("SOUTH",filenamePrompt);
		myPanel.add("CENTER",filenameField);
		
		JOptionPane.showMessageDialog(null, myPanel,"Input",JOptionPane.OK_CANCEL_OPTION);
		
		//send filename to Matt's code
	}
}
