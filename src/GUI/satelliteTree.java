package GUI;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import satellite.*;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.DropMode;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JEditorPane;

public class satelliteTree extends JInternalFrame {
	private JTextField textField;

	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Database");
	JTree tree = new JTree(root);
	private JTextField textField_1;
	
		
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
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Satellite name");
		lblNewLabel.setBounds(47, 38, 81, 14);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(138, 35, 60, 20);
		textField.setDropMode(DropMode.INSERT);
		panel.add(textField);
		textField.setColumns(10);
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("D:\\MyDocuments\\GitHub\\Satellite\\satellite.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image dimg = img.getScaledInstance(150,150,Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(dimg);
		JLabel lblSatImg = new JLabel(image);
		lblSatImg.setBackground(Color.WHITE);
		lblSatImg.setBounds(378, 11, 150, 150);
		lblSatImg.setVerticalAlignment(SwingConstants.TOP);
		lblSatImg.setText("");
		panel.add(lblSatImg);
		
		JLabel lblNewLabel_1 = new JLabel("Constillation");
		lblNewLabel_1.setBounds(47, 63, 81, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Info");
		lblNewLabel_2.setBounds(47, 88, 46, 14);
		panel.add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(138, 60, 86, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(138, 88, 172, 85);
		panel.add(editorPane);
		
		//Where the tree is initialized:
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Listen for when the selection changes.
		tree.addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
		                           tree.getLastSelectedPathComponent();

		    /* if nothing is selected */ 
		        if (node == null) return;

		    /* retrieve the node that was selected */ 
		        Object nodeInfo = node.getUserObject();
		 
		    /* React to the node selection. */
			    if (node.isLeaf()) {
			        SatelliteTrack sat = SatelliteDB.sat(SatelliteDB.getSatIndex((String) nodeInfo));
			        System.out.println(sat.getTLE().getName());
			    } else {
			    	System.out.println("Not Leaf");
			    }
		    }
		});

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
