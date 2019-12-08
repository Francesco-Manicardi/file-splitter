package fileSplitter;

import javax.swing.JFrame;

public class Window {

	JFrame frame;

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("File Splitter & Stitcher");
		frame.setBounds(100, 100, 750, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
