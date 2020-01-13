package view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * The Main Window.
 */
public class Window {

	/** Just a standard frame. */
	private JFrame frame;

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
		setFrame(new JFrame("File Splitter & Stitcher"));
		getFrame().setBounds(100, 100, 750, 400);
		getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	/**
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * @param frame the frame to set
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

}
