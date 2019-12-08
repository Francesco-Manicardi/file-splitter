package fileSplitter;

import java.awt.EventQueue;

/**
 * The Main Class. Creates a window and a controller attached to it.
 */
public class Main {

	/**
	 * Launch the application.
	 *
	 * @param args the arguments (unused)
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
					Controller controller = new Controller(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
