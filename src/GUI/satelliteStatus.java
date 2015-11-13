package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import satellite.*;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;

public class satelliteStatus extends JInternalFrame {

	SatelliteTrack curSat = null;
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Database");
	JTree tree = new JTree(root);
	
	/**
	 * Create the frame.
	 */
	public satelliteStatus() {
		setBounds(100, 100, 740, 530);
		
		JDesktopPane desktopPane = new JDesktopPane();
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JScrollPane treescroll = new JScrollPane(tree);
		treescroll.setBounds(0, 0, 254, 481);
		desktopPane.add(treescroll);
		
		JPanel panel = new JPanel();
		panel.setBounds(254, 0, 470, 481);
		desktopPane.add(panel);
		
		JButton btnStartTracking = new JButton("Start Tracking");
		btnStartTracking.setBounds(35, 390, 165, 48);
		btnStartTracking.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				TrackerThread.startTrack(curSat.getTLE().getName(), "Albuquerque");
				//thread.setSatellite(curSat);
				//(new Thread(new thread())).start();
			}
		});
		panel.setLayout(null);
		panel.add(btnStartTracking);
		
		JButton btnStopTracking = new JButton("Stop Tracking");
		btnStopTracking.setBounds(257, 390, 161, 48);
		btnStopTracking.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				tracker.stopTrack(curSat.getTLE().getName());
				//thread.setSatellite(curSat);
				//(new Thread(new thread())).start();
			}
		});
		panel.add(btnStopTracking);
		
		JLabel lblLatitude = new JLabel("Latitude");
		lblLatitude.setBounds(35, 21, 165, 31);
		panel.add(lblLatitude);
		
		JLabel lblLongitude = new JLabel("Longitude");
		lblLongitude.setBounds(35, 73, 92, 26);
		panel.add(lblLongitude);
		
		JLabel lblAltitude = new JLabel("Altitude");
		lblAltitude.setBounds(35, 120, 92, 26);
		panel.add(lblAltitude);
		
		JLabel lblRange = new JLabel("Range");
		lblRange.setBounds(35, 172, 92, 26);
		panel.add(lblRange);
		
		JLabel lblLat = new JLabel("Lat");
		lblLat.setBounds(164, 23, 92, 26);
		panel.add(lblLat);
		
		JLabel lblLong = new JLabel("Long");
		lblLong.setBounds(164, 73, 92, 26);
		panel.add(lblLong);
		
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
			        curSat = SatelliteDB.sat(SatelliteDB.getSatIndex((String) nodeInfo));
			        //trackerThread track = new trackerThread(curSat);
			        System.out.println(curSat.getTLE().getName());
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
