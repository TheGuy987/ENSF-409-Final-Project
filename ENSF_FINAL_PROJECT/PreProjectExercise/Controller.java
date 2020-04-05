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
	
	}

	public void createTreePress() {
		
	}
}
