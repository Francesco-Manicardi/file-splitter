package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import fileSplitter.Controller;
import fileSplitter.SplitModalityEnum;
import stitchers.FileStitcher;

/**
 * The button that, when pressed, causes a log file to be read and a file to be
 * stitched up based on the information in the log file. The log file has a
 * .partitioninfo extension.
 */
public class FileStitcherButton extends JButton implements ActionListener {

	/** The originating controller. */
	private Controller controller;

	/**
	 * The core file stitcher or any of its descendants (classes that extend it).
	 */
	private FileStitcher fileStitcherCore;

	/**
	 * Instantiates a new file stitcher button.
	 *
	 * @param controller the originating controller
	 */
	public FileStitcherButton(Controller controller) {
		this.setBounds(20, 20, 100, 30);
		this.setText("Ricomponi File");
		this.controller = controller;
		this.addActionListener(this);

	}

	/**
	 * Event handler called on click of the button. Stitches the files with the
	 * correct file stitcher class and prints any exceptions raised during the
	 * stitching process to the user in a message box.
	 *
	 * @param e the event. Currently unused.
	 */
	@Override

	public void actionPerformed(ActionEvent e) {

		try {
			for (File file : this.controller.fileManagerButton.files) {
				String storedStitchModality = "";

				BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));

				storedStitchModality = reader.readLine();

				SplitModalityEnum splitModality = SplitModalityEnum.valueOf(storedStitchModality);

				this.fileStitcherCore = splitModality.getStitcherClass().getDeclaredConstructor(String.class)
						.newInstance(this.controller.getOutputDirChooser().getChosenDir().getPath());

				this.fileStitcherCore.stitchFile(file);
				reader.close();

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			JOptionPane.showMessageDialog(null, exception.getMessage());
		}

		JOptionPane.showMessageDialog(null, "Files Ricomposti!");
	}

}
