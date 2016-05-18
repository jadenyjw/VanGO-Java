package swing;

import java.awt.EventQueue;
import network.SocketSender;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.awt.event.ActionEvent;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;


import javax.swing.JList;

public class MainWindow {

	private JFrame frmVanGo;
	private JPanel vidPanel;

	
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
		frmVanGo.setResizable(false);
		frmVanGo.setTitle("Van Go");
		frmVanGo.setBounds(100, 100, 1024, 640);
		frmVanGo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVanGo.getContentPane().setLayout(null);
		frmVanGo.setVisible(true);
		KeyListen newListener = new KeyListen();
		frmVanGo.addKeyListener(newListener);
		frmVanGo.setFocusable(true);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setBounds(0, 0, 800, 600);
		frmVanGo.getContentPane().add(panel);

		JLabel lblControls = new JLabel("Controls");
		lblControls.setHorizontalAlignment(SwingConstants.CENTER);
		lblControls.setBounds(810, 11, 188, 14);
		frmVanGo.getContentPane().add(lblControls);

		JLabel lblMovementArrowKeys = new JLabel("Movement: Arrow Keys");
		lblMovementArrowKeys.setBounds(810, 36, 198, 14);
		frmVanGo.getContentPane().add(lblMovementArrowKeys);

		JLabel lblCameraTiltOp = new JLabel("Camera Tilt: PgUp / PgDown");
		lblCameraTiltOp.setBounds(810, 61, 198, 14);
		frmVanGo.getContentPane().add(lblCameraTiltOp);

		JButton btnNewButton = new JButton("Identify Image");

		DefaultListModel listModel;
		listModel = new DefaultListModel();

		JScrollPane scrollPane = new JScrollPane();
		JList list = new JList(listModel);

		scrollPane.setViewportView(list);
		scrollPane.setBounds(810, 86, 198, 480);
		
		frmVanGo.getContentPane().add(scrollPane);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				takeSnapShot(panel);

				ClarifaiClient clarifai = new ClarifaiClient("Wt6QO1me3Idz7ucdO3Dw_We4QTXWbvBzvWre6O_p",
						"N5GD23xeygB98B7Dnpsq2qkXaP0fyZa2ruqU2lV5");
				List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest(new File("clarifai.jpg")));
				listModel.clear();
				for (Tag tag : results.get(0).getTags()) {
					listModel.addElement(tag.getName());
				}
			}
		});
		btnNewButton.setBounds(810, 577, 198, 23);
		frmVanGo.getContentPane().add(btnNewButton);

		String ip;

		boolean connSuccess = false;

		do {
			ip = JOptionPane.showInputDialog(frmVanGo, "What's the IP of the Van Go bot?");
			if (ip == null) {
				System.exit(0);
			}
			try {

				SocketSender.establishConn(ip);
				initVLC(panel, ip);
				connSuccess = true;

			} catch (Exception e) {

				JOptionPane.showMessageDialog(null, "The connection failed. Please try again.");
				e.printStackTrace();
			}
		} while (connSuccess == false);

	}

	public static void initVLC(JPanel panel, String ip) {

		boolean found = new NativeDiscovery().discover();
		EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		panel.add(mediaPlayerComponent);
		mediaPlayerComponent.getMediaPlayer().playMedia("rtsp://" + ip + ":8554/vango");

	}

	public static void takeSnapShot(JPanel panel) {

		BufferedImage bufImage = new BufferedImage(panel.getSize().width, panel.getSize().height,
				BufferedImage.TYPE_INT_RGB);
		panel.paint(bufImage.createGraphics());
		File imageFile = new File("clarifai.jpg");

		try {
			imageFile.createNewFile();
			ImageIO.write(bufImage, "jpeg", imageFile);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
