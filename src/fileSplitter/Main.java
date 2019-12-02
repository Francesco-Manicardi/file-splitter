package fileSplitter;

import java.awt.EventQueue;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
