package GUI;

import java.awt.EventQueue;

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

public class satelliteTree extends JInternalFrame {
	private JTextField textField;

	JTree tree = new JTree();
	/**
	 * Create the frame.
	 */
	public satelliteTree() {
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 872, 640);
		
		JDesktopPane desktopPane = new JDesktopPane();
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		
		tree.setBounds(0, 0, 254, 588);
		desktopPane.add(tree);
		
		JPanel panel = new JPanel();
		panel.setBounds(252, 0, 604, 588);
		desktopPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

	}
	
	public void updateTree(SatelliteDB satellites){
		for(int i = 0;i<satellites.getSize();i++){
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
			model.insertNodeInto(new DefaultMutableTreeNode(satellites.sat(i).getTLE().getName()), root, root.getChildCount());
		}
	}

}
