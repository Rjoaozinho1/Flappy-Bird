import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private static Keyboard instance;

	private boolean[] keys;
	
	private Keyboard() {
		keys = new boolean[256]; // Array de booleanos para representar o estado das teclas (256 teclas possíveis)
	}

	public static Keyboard getInstance() {
		if (instance == null) {
			instance = new Keyboard(); // Cria uma instância única da classe Keyboard (padrão Singleton)
		}
		return instance;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = true; // Define a tecla como pressionada no array de keys
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = false; // Define a tecla como não pressionada no array de keys
		}
	}

	public void keyTyped(KeyEvent e) {}

	public boolean isDown(int key) {
		if (key >= 0 && key < keys.length) {
			return keys[key]; // Retorna true se a tecla estiver pressionada, caso contrário, retorna false
		}
		return false;
	}
}
