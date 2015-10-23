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

public class Application {

	private JFrame frmSatelliteTracker;
	private final Action action = new SwingAction();
	SatelliteDB satellites = new SatelliteDB("SatelliteDB");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frmSatelliteTracker.setVisible(true);
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSatelliteTracker = new JFrame();
		frmSatelliteTracker.setTitle("Satellite Tracker");
		frmSatelliteTracker.setBounds(100, 100, 1362, 867);
		frmSatelliteTracker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmSatelliteTracker.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("FIle");
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
		
		JMenuItem mntmCustomeSatelliteTrack = new JMenuItem("Custome Satellite Track");
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
		mnWindows.add(mntmSatelliteInfo);
		
		JMenuItem mntmSatelliteTree = new JMenuItem("Satellite Tree");
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
	

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
