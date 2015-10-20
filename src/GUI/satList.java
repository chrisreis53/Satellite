package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class satList extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					satList frame = new satList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public satList() {
		setBounds(100, 100, 450, 300);

	}

}
