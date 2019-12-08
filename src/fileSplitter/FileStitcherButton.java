package fileSplitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * The FileStitcherButton Class.
 */
public class FileStitcherButton extends JButton implements ActionListener {

	/** The originating controller. */
	private Controller controller;

	/** The file stitcher or any of its descendants (classes that extend it). */
	private FileStitcherCore fileStitcherCore;

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
	 * Event handler called on click of the button. Stitches the files and prints
	 * any exceptions to the user in a message box.
	 *
	 * @param e the event. Currently unused.
	 */
	@Override

	public void actionPerformed(ActionEvent e) {

		try {
			for (File file : this.controller.fileManagerButton.files) {

				this.fileStitcherCore = new FileStitcherCore(this.controller.outputDirChooser.chosenDir.getPath());

				String storedStitchModality = "";

				BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));

				storedStitchModality = reader.readLine();

				SplitModalityEnum splitModality = SplitModalityEnum.valueOf(storedStitchModality);

				this.fileStitcherCore = splitModality.stitcherClass.getDeclaredConstructor(String.class)
						.newInstance(this.controller.outputDirChooser.chosenDir.getPath());

				this.fileStitcherCore.stitchFile(file);
				reader.close();
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			JOptionPane.showMessageDialog(null, exception.getMessage());
		}
	}

}
