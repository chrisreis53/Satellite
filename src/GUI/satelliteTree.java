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
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;

public class satelliteTree extends JInternalFrame {
	private JTextField satNameField;

	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Database");
	JTree tree = new JTree(root);
	private JTextField constillationField;
	private JTextField uplinkFreqField;
	private JTextField downlinkFreqField;
	
		
	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
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
		panel.setBounds(252, 0, 596, 467);
		desktopPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Satellite name");
		lblNewLabel.setBounds(21, 21, 150, 31);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel);
		
		satNameField = new JTextField();
		satNameField.setBounds(167, 21, 186, 32);
		satNameField.setDropMode(DropMode.INSERT);
		panel.add(satNameField);
		satNameField.setColumns(10);
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("D:\\MyDocuments\\GitHub\\Satellite\\satellite.png"));
		    //C:\\Users\\Christopher\\Documents\\GitHub\\Satellite\\satellite.png
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image dimg = img.getScaledInstance(150,150,Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(dimg);
		JLabel lblSatImg = new JLabel(image);
		lblSatImg.setBackground(Color.WHITE);
		lblSatImg.setBounds(396, 51, 150, 150);
		lblSatImg.setVerticalAlignment(SwingConstants.TOP);
		lblSatImg.setText("");
		panel.add(lblSatImg);
		
		JLabel lblNewLabel_1 = new JLabel("Constillation");
		lblNewLabel_1.setBounds(21, 67, 114, 20);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Info");
		lblNewLabel_2.setBounds(21, 241, 67, 31);
		panel.add(lblNewLabel_2);
		
		constillationField = new JTextField();
		constillationField.setBounds(167, 61, 186, 32);
		panel.add(constillationField);
		constillationField.setColumns(10);
		
		JEditorPane infoPane = new JEditorPane();
		infoPane.setBounds(167, 256, 379, 113);
		panel.add(infoPane);
		
		JButton saveButton = new JButton("Commit to Database");
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.println("Saved!");
			}
		});
		saveButton.setBounds(138, 390, 230, 35);
		panel.add(saveButton);
		
		JLabel lbUplinkFreq = new JLabel("Uplink Freq");
		lbUplinkFreq.setBounds(21, 108, 138, 26);
		panel.add(lbUplinkFreq);
		
		JLabel lbDownlinkFreq = new JLabel("Downlink Freq");
		lbDownlinkFreq.setBounds(21, 155, 138, 26);
		panel.add(lbDownlinkFreq);
		
		JLabel lbSatelliteImage = new JLabel("Sat Image");
		lbSatelliteImage.setBounds(21, 202, 138, 26);
		panel.add(lbSatelliteImage);
		
		uplinkFreqField = new JTextField();
		uplinkFreqField.setBounds(167, 108, 186, 32);
		panel.add(uplinkFreqField);
		uplinkFreqField.setColumns(10);
		
		downlinkFreqField = new JTextField();
		downlinkFreqField.setBounds(167, 152, 186, 32);
		panel.add(downlinkFreqField);
		downlinkFreqField.setColumns(10);
		
		JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Satellite", "Station", "Geostationary", "Weather", "Amateur Radio"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectedIndex(2);
		list.setBounds(167, 201, 186, 31);
		panel.add(list);
		
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
			        constillationField.setText(sat.getConstillation());
			        satNameField.setText(sat.getTLE().getName());
			        uplinkFreqField.setText(Long.toString(sat.getUplinkFreq()));
			        downlinkFreqField.setText(Long.toString(sat.getDownlinkFreq()));
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
