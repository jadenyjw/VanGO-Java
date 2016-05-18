package swing;
import network.SocketSender;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListen implements KeyListener{
    boolean keySet = false;
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(keySet==false){
		System.out.println(e.getKeyCode());
		keySet=true;
		}
		
		int keyCode = e.getKeyCode();
		
		switch (keyCode){
		
		case KeyEvent.VK_UP:
			
			break;
		case KeyEvent.VK_DOWN:
			
			break;
		case KeyEvent.VK_LEFT:
			
			break;
		case KeyEvent.VK_RIGHT:
			
			break;
		case KeyEvent.VK_PAGE_UP:
			
			break;
			
		case KeyEvent.VK_PAGE_DOWN:
			
			break;
		

			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		keySet = false;
		
		//case 
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

}
