package swing;
import network.SocketSender;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class KeyListen implements KeyListener{
    boolean keySet = false;
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(keySet==false){
			int keyCode = e.getKeyCode();
			
			switch (keyCode){
			
			case KeyEvent.VK_DOWN:
				try {
					SocketSender.sendCommand("0");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case KeyEvent.VK_UP:
				try {
					SocketSender.sendCommand("1");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case KeyEvent.VK_RIGHT:
				try {
					SocketSender.sendCommand("2");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case KeyEvent.VK_LEFT:
				try {
					SocketSender.sendCommand("3");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case KeyEvent.VK_PAGE_UP:
				try {
					SocketSender.sendCommand("5");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case KeyEvent.VK_PAGE_DOWN:
				try {
					SocketSender.sendCommand("6");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
				
		
		}
		keySet=true;
		
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
		try {
			SocketSender.sendCommand("4");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		keySet = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

}
