import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUI extends JFrame {
	JButton insert, find, browse, createTree;
	JLabel title;
	JPanel titlePanel, buttonPanel;
	JScrollPane display;
	Controller control;
	
	public GUI() {
		control = new Controller();
		
		titlePanel = new JPanel();
		title = new JLabel("An Application to Maintain Student Records");
		titlePanel.add(title);
		
		insert = new JButton("Insert");
		find = new JButton("Find");
		browse = new JButton("Browse");
		createTree = new JButton("Create Tree From File");
		
		insert.addActionListener((ActionEvent e) -> { 
			control.insertPressed();
			});
		
		find.addActionListener((ActionEvent e) -> { 
			control.findPressed();
			});
		
		browse.addActionListener((ActionEvent e) -> { 
			control.browsePressed();
			});
		
		createTree.addActionListener((ActionEvent e) -> { 
			control.createTreePressed();
			});
		
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
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public static void main(String[] args) {
		GUI app = new GUI();
		app.setVisible(true);
	}
}
