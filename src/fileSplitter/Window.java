package fileSplitter;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * The Main Window.
 */
public class Window {

	/** Just a standard frame. */
	JFrame frame;

	/**
	 * Create the window by initializing it.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the window.
	 */
	private void initialize() {
		frame = new JFrame("File Splitter & Stitcher");
		frame.setBounds(100, 100, 750, 400);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

}
