package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import fileSplitter.Controller;

/**
 * The Class FileManagerButton handles the process of choosing files that will
 * later be split or stitched.
 */
public class FileManagerButton extends JButton implements ActionListener {

	/** The latest chosen files. */
	File[] files;

	/** The standard file chooser. */
	JFileChooser chooser = new JFileChooser();

	/** The originating controller. */
	private Controller controller;

	/**
	 * Instantiates a new file manager button.
	 *
	 * @param controller the originating controller
	 */
	public FileManagerButton(Controller controller) {
		this.setBounds(20, 20, 100, 30);
		this.setText("Aggiungi File");
		this.controller = controller;
		this.addActionListener(this);

		FileNameExtensionFilter partitionInfoFilter = new FileNameExtensionFilter("File Per Ricomposizione",
				"partitioninfo");
		chooser.addChoosableFileFilter(partitionInfoFilter);

	}

	/**
	 * Handles the click on the button by opening up a JFileChooser with multiple
	 * selection enabled.
	 *
	 * @param e The event. Currently unused.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		chooser.setMultiSelectionEnabled(true);
		int result = chooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {

			files = chooser.getSelectedFiles();
			this.controller.fileListDisplayer.updateListing();
		}
	}

	/**
	 * Empties the chosen files array and the related components (through the
	 * controller)
	 */
	public void empty() {
		this.files = new File[0];
		this.controller.fileListDisplayer.refreshModel();
		this.controller.fileListDisplayer.updateListing();
	}

}
