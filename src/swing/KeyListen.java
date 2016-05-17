package swing;

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
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keySet = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

}
