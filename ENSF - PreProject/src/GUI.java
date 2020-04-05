import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUI extends JFrame {
	JButton insert, find, browse, createTree;
	JLabel title;
	JPanel titlePanel, buttonPanel;
	JScrollPane display;
	
	public GUI() {
		titlePanel = new JPanel();
		title = new JLabel("An Application to Maintain Student Records");
		titlePanel.add(title);
		
		insert = new JButton("Insert");
		find = new JButton("Find");
		browse = new JButton("Browse");
		createTree = new JButton("Create Tree From File");
		
		//insert.addActionListener();		
		buttonPanel = new JPanel();
		buttonPanel.add(insert);
		buttonPanel.add(find);
		buttonPanel.add(browse);
		buttonPanel.add(createTree);
		
		display = new JScrollPane();
		
		this.add("South",buttonPanel);
		this.add("North",titlePanel);
		this.add("Center",display);
		this.setSize(720,480);
	}
	
	public String find() {
		String idToSearch;
		idToSearch = JOptionPane.showInputDialog("Please enter the student's id:");
		return idToSearch;
	}
	
	public static void main(String[] args) {
		GUI app = new GUI();
		app.setVisible(true);
		app.find();
	}
}