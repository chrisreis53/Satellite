package GUI;

import java.awt.EventQueue;
import java.util.Enumeration;

import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import satellite.*;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class satelliteTree extends JInternalFrame {
	private JTextField textField;

	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Database");
	JTree tree = new JTree(root);
	
		
	/**
	 * Create the frame.
	 */
	public satelliteTree() {
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 862, 519);
		
		root.add(new DefaultMutableTreeNode("Other"));
		
		JDesktopPane desktopPane = new JDesktopPane();
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JScrollPane treescroll = new JScrollPane(tree);
		treescroll.setBounds(0, 0, 254, 481);
		desktopPane.add(treescroll);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(252, 0, 596, 481);
		desktopPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

	}
	
	public void updateTree(SatelliteDB satellites){
		
		//Iterate through sat database
		for(int i = 0;i<satellites.getSize();i++){
			boolean b_set = false;
			//pull constellation from satellite
			String constillation = satellites.sat(i).getConstillation();
			//pull nodes from Jtree
			int count = root.getChildCount();
			System.out.println(count);
			for(int j = 0; j<count;j++){
				System.out.println(root.getChildCount());
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(j);
				if(node.toString().equalsIgnoreCase(constillation.trim())){
					node.add(new DefaultMutableTreeNode(satellites.sat(i).getTLE().getName()));
					System.out.println("added to existing");
					b_set = true;
				} 
			}
			if(!b_set) { 
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(constillation);
				newNode.add(new DefaultMutableTreeNode(satellites.sat(i).getTLE().getName()));
				root.add(newNode);
			}
			
		}
	}
}
