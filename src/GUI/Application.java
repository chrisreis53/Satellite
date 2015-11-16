package GUI;

import java.awt.EventQueue;

import satellite.*;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JPanel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import java.awt.Rectangle;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JToolBar;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Application {

	private JFrame frmSatelliteTracker;
	static TrackerList trackList = new TrackerList();
	static SatelliteDB satellites = new SatelliteDB("SatelliteDB");
	static view3D threeDview = new view3D();

	static ActionListener updateTracks = new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
	    	List<satPosition> pos = TrackerList.getTracks();
	    	threeDview.updateTracks(pos);
	    	System.out.println("update");
	    }
	};
	
	static ActionListener addtrack = new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
	    	threeDview.addSatellite(TrackerList.getTracks());
	    }
	};
	
	/**
	 * Launch the application.
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 */
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frmSatelliteTracker.setVisible(true);
					new javax.swing.Timer(500, updateTracks).start();
					new javax.swing.Timer(500, addtrack).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					while(true){
//						System.out.println(new Date());
//						Thread.sleep(500);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSatelliteTracker = new JFrame();
		frmSatelliteTracker.setTitle("Satellite Tracker");
		frmSatelliteTracker.setBounds(100, 100, 1362, 867);
		frmSatelliteTracker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JDesktopPane desktopPane = new JDesktopPane();
		frmSatelliteTracker.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		satelliteTree satTree = new satelliteTree();
		satTree.setClosable(true);
		satTree.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		satTree.setResizable(true);
		satTree.setVisible(false);
		satTree.setBounds(448, 177, 867, 495);
		desktopPane.add(satTree);
		
		satelliteStatus satStatus = new satelliteStatus();
		satStatus.setLocation(32, 86);
		satStatus.setClosable(true);
		satStatus.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		satStatus.setResizable(true);
		satStatus.setVisible(false);
		desktopPane.add(satStatus);
		
		threeDview = new view3D();
		threeDview.setClosable(true);
		threeDview.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		threeDview.setResizable(true);
		threeDview.setVisible(true);
		threeDview.setBounds(300, 20, 500, 500);
		desktopPane.add(threeDview);
		
		JMenuBar menuBar = new JMenuBar();
		frmSatelliteTracker.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setIcon(null);
		menuBar.add(mnFile);
		
		JMenuItem mntmLoadTleXml = new JMenuItem("Load TLE XML");
		mntmLoadTleXml.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				satellites.readXML();
			}
		});
		mntmLoadTleXml.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_MASK));
		mnFile.add(mntmLoadTleXml);
		
		JMenuItem mntmSaveTleXml = new JMenuItem("Save TLE XML");
		mntmSaveTleXml.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				satellites.writeToXML();
			}
		});
		mnFile.add(mntmSaveTleXml);
		
		JMenuItem mntmCustomeSatelliteTrack = new JMenuItem("Custom Satellite Track");
		mnFile.add(mntmCustomeSatelliteTrack);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mnFile.add(mntmSettings);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		JMenuItem mntmUpdateTles = new JMenuItem("Update TLEs");
		mnTools.add(mntmUpdateTles);
		
		JMenuItem mntmRadioInterface = new JMenuItem("Radio Interface");
		mnTools.add(mntmRadioInterface);
		
		JMenuItem mntmAddeditSatelliteData = new JMenuItem("Edit Satellite Data");
		mnTools.add(mntmAddeditSatelliteData);
		
		JMenuItem mntmSimulator = new JMenuItem("Simulator");
		mnTools.add(mntmSimulator);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenuItem mntmSelectSatellite = new JMenuItem("Select Satellite");
		mntmSelectSatellite.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				//(new Thread(new trackerThread())).start();
			}
		});
		mnView.add(mntmSelectSatellite);
		
		JMenu mnWindows = new JMenu("Windows");
		mnView.add(mnWindows);
		
		JMenuItem mntmdGlobe = new JMenuItem("3D Globe");
		mnWindows.add(mntmdGlobe);
		
		JMenuItem mntmdGlobe_1 = new JMenuItem("2D Globe");
		mnWindows.add(mntmdGlobe_1);
		
		JMenuItem mntmPassPlot = new JMenuItem("Pass Plot");
		mnWindows.add(mntmPassPlot);
		
		JMenuItem mntmFuturePasses = new JMenuItem("Future Passes");
		mnWindows.add(mntmFuturePasses);
		
		JMenuItem mntmSatelliteInfo = new JMenuItem("Satellite Info");
		mntmSatelliteInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				satStatus.setVisible(true);
				satStatus.updateTree(satellites);
			}
		});
		mnWindows.add(mntmSatelliteInfo);
		
		JMenuItem mntmSatelliteTree = new JMenuItem("Satellite Tree");
		mntmSatelliteTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				satTree.setVisible(true);
				satTree.updateTree(satellites);
			}
		});
		mnWindows.add(mntmSatelliteTree);
		
		JMenuItem mntmPassScheduler = new JMenuItem("Pass Scheduler");
		mnWindows.add(mntmPassScheduler);
		
		JMenu mnShow = new JMenu("Show");
		mnView.add(mnShow);
		
		JCheckBoxMenuItem chckbxmntmTracks = new JCheckBoxMenuItem("Tracks");
		mnShow.add(chckbxmntmTracks);
		
		JCheckBoxMenuItem chckbxmntmOnScreenSat = new JCheckBoxMenuItem("On Screen Sat Data");
		mnShow.add(chckbxmntmOnScreenSat);
		
		JCheckBoxMenuItem chckbxmntmLosBubble = new JCheckBoxMenuItem("LOS Bubble");
		mnShow.add(chckbxmntmLosBubble);
		
		JCheckBoxMenuItem chckbxmntmTime = new JCheckBoxMenuItem("Time");
		mnShow.add(chckbxmntmTime);
		

		

	}

}
