import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Controller {
	BinSearchTree bst;
	
	public Controller() {
		bst = null;
	}
	
	public void insertPressed() {
		if(bst==null) {
			JOptionPane.showMessageDialog(null, "Please Create a Tree before using Insert","Error", JOptionPane.PLAIN_MESSAGE);
			return;
		}else {
			
			JPanel myPanel = new JPanel();
			
			JLabel idPrompt = new JLabel("Enter the Student ID");
			JLabel facultyPrompt = new JLabel("Enter Faculty");
			JLabel majorPrompt = new JLabel("Enter Student's Major");
			JLabel yearPrompt = new JLabel("Enter year");
			
			JTextField idField = new JTextField();
			JTextField facultyField = new JTextField();
			JTextField majorField = new JTextField();
			JTextField yearField = new JTextField();
			
			myPanel.add(idPrompt);
			myPanel.add(facultyPrompt);
			myPanel.add(majorPrompt);
			myPanel.add(yearPrompt);

			myPanel.add(idField);
			myPanel.add(facultyField);
			myPanel.add(majorField);
			myPanel.add(yearField);

			int check = JOptionPane.showConfirmDialog(null, myPanel,"Insert a New Node", JOptionPane.OK_CANCEL_OPTION);
			if(check!=JOptionPane.OK_OPTION) {
				JOptionPane.showMessageDialog(null, "Returning to main menu", "Error", JOptionPane.PLAIN_MESSAGE);
			}else {
				bst.insert(idField.getText(), facultyField.getText(), majorField.getText(), yearField.getText());
				JOptionPane.showMessageDialog(null, "Your Node has been added!", "Insert", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	public void findPressed() {
		
	}
	
	public void browsePressed() {
	
	}

	public void createTreePressed() {
		
	}
}
