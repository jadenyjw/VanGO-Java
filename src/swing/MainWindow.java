package swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;

public class MainWindow {

	private JFrame frmVanGo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmVanGo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmVanGo = new JFrame();
		frmVanGo.setTitle("Van Go");
		frmVanGo.setBounds(100, 100, 1024, 640);
		frmVanGo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVanGo.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(0, 0, 800, 600);
		frmVanGo.getContentPane().add(panel);
		
		JLabel lblControls = new JLabel("Controls");
		lblControls.setHorizontalAlignment(SwingConstants.CENTER);
		lblControls.setBounds(810, 11, 188, 14);
		frmVanGo.getContentPane().add(lblControls);
		
		JLabel lblMovementArrowKeys = new JLabel("Movement: Arrow Keys");
		lblMovementArrowKeys.setBounds(810, 36, 188, 14);
		frmVanGo.getContentPane().add(lblMovementArrowKeys);
		
		JLabel lblCameraTiltOp = new JLabel("Camera Tilt: O/P");
		lblCameraTiltOp.setBounds(810, 61, 188, 14);
		frmVanGo.getContentPane().add(lblCameraTiltOp);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(810, 86, 188, 470);
		frmVanGo.getContentPane().add(panel_1);
		
		JButton btnNewButton = new JButton("Identify Image");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(810, 567, 188, 23);
		frmVanGo.getContentPane().add(btnNewButton);
	}
}
