package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fileSplitter.Controller;

/**
 * The Class ClearListButton is a simple button that deletes all the files in
 * the queue when clicked.
 */
public class ClearListButton extends JButton implements ActionListener {

	/** The controller. */
	private Controller controller;

	/**
	 * Instantiates a new clear list button, and sets its position, bounds and text.
	 * Also adds the action listener.
	 * 
	 * @param controller the parent controller
	 */
	public ClearListButton(Controller controller) {
		this.setBounds(20, 20, 100, 30);
		this.setText("Reset Coda");
		this.controller = controller;
		this.addActionListener(this);

	}

	/**
	 * Action performed function that gets called when the button is clicked: simply
	 * calls the empty function on the file manager instance.
	 *
	 * @param e the action event. Currently unused.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		this.controller.fileManagerButton.empty();
	}

}
