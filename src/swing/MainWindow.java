package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;

import network.SocketSender;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class MainWindow {

	private JFrame frmVanGo;
	private JPanel vidPanel;
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private Boolean keySet;
	
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
		
		frmVanGo.setFocusable(true);
		
	    registerBindings();
	    
		vidPanel = new JPanel();
		vidPanel.setBorder(new LineBorder(Color.GRAY));
		vidPanel.setBounds(0, 0, 800, 600);
		frmVanGo.getContentPane().add(vidPanel);
		vidPanel.setLayout(new BorderLayout(0, 0));

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
		btnNewButton.setFocusable(false);

		DefaultListModel<String> listModel;
		listModel = new DefaultListModel<String>();

		JScrollPane scrollPane = new JScrollPane();
		JList<String> list = new JList<String>(listModel);

		scrollPane.setViewportView(list);
		scrollPane.setBounds(810, 86, 198, 480);
		
		frmVanGo.getContentPane().add(scrollPane);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				takeSnapShot(mediaPlayerComponent);
				File file = new File("clarifai.jpg");
				ClarifaiClient clarifai = new ClarifaiClient("Wt6QO1me3Idz7ucdO3Dw_We4QTXWbvBzvWre6O_p",
						"N5GD23xeygB98B7Dnpsq2qkXaP0fyZa2ruqU2lV5");
				//Free @p1 Key, doesn't need to be obfuscated as of now.
				
				List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest(file));
				listModel.clear();
				for (Tag tag : results.get(0).getTags()) {
					listModel.addElement(tag.getName());
				}
				
				file.delete();
				
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
				connSuccess = true;

			} catch (Exception e) {

				JOptionPane.showMessageDialog(null, "The connection failed. Please try again.");
				e.printStackTrace();
			}
		} while (connSuccess == false);
		
		boolean found = new NativeDiscovery().discover();
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		
		vidPanel.add(mediaPlayerComponent);
		mediaPlayerComponent.getMediaPlayer().playMedia("rtsp://" + ip + ":8554/vango.mp4");
	    
	}


	public static void takeSnapShot(EmbeddedMediaPlayerComponent panel) {

		try {
			File file = new File("clarifai.jpg");
			panel.getMediaPlayer().saveSnapshot(file);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

		
	}
	
	private void registerBindings(){
		
		keySet = false;
		Action right = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
		    	if (keySet == false){
		    		try {
						SocketSender.sendCommand("2");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		    		keySet = true;
		    	}
		        
		    }
		};
		frmVanGo.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "right");
		frmVanGo.getRootPane().getActionMap().put("right", right);
		
		Action left = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
		    	if (keySet == false){
		    		try {
						SocketSender.sendCommand("3");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		    		keySet = true;
		    	}
		    }
		};
		frmVanGo.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "left");
		frmVanGo.getRootPane().getActionMap().put("left", left);
		
		Action down = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
		    	if (keySet == false){
		    		try {
						SocketSender.sendCommand("0");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		    		keySet = true;
		    	}
		    }
		};
		frmVanGo.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "down");
		frmVanGo.getRootPane().getActionMap().put("down", down);
		
		Action up = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
		    	if (keySet == false){
		    		try {
						SocketSender.sendCommand("1");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		    		keySet = true;
		    	}
		    }
		};
		frmVanGo.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "up");
		frmVanGo.getRootPane().getActionMap().put("up", up);
		
		Action camUp = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
		    	if (keySet == false){
		    		try {
						SocketSender.sendCommand("5");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		    		keySet = true;
		    	}
		    }
		};
		frmVanGo.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("PAGE_UP"), "camUp");
		frmVanGo.getRootPane().getActionMap().put("camUp", camUp);
		
		Action camDown = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
		    	if (keySet == false){
		    		try {
						SocketSender.sendCommand("6");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		    		keySet = true;
		    	}
		    }
		};
		frmVanGo.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("PAGE_DOWN"), "camDown");
		frmVanGo.getRootPane().getActionMap().put("camDown", camDown);
		
		Action stop = new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
		    	try {
					SocketSender.sendCommand("4");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        keySet = false;
		    }
		};
		frmVanGo.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"), "stop");
		frmVanGo.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "stop");
		frmVanGo.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released UP"), "stop");
		frmVanGo.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released DOWN"), "stop");
		
		
		
		frmVanGo.getRootPane().getActionMap().put("stop", stop);
		
		
	}
	                                         
	
}
