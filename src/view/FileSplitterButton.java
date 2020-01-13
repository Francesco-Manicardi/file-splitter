package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import fileSplitter.Controller;
import fileSplitter.SplitModalityEnum;
import splitters.FileSplitterCore;

/**
 * The Class FileSplitterButton. It's the interface to the underlying splitting
 * and stitching algorithms.
 * 
 */
public class FileSplitterButton extends JButton implements ActionListener {

	/** The originating controller. */
	private Controller controller;

	/**
	 * The file splitter. Can be any subclass (crypt, compress...) via polymorphism
	 */
	private FileSplitterCore fileSplitter;

	/**
	 * Instantiates a new file splitter button.
	 *
	 * @param controller the originating controller
	 */
	public FileSplitterButton(Controller controller) {
		this.setBounds(20, 20, 100, 30);
		this.setText("Splitta i File");
		this.controller = controller;
		this.addActionListener(this);

	}

	/**
	 * Handles the click on the button. Instantiates the correct file splitter based
	 * on the user selection of mode. Also catches any exceptions during split
	 * process and displays them in a message box to the user.
	 * 
	 *
	 * @param e the event. Currently unused.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			String selectedSplitModality = this.controller.getSplitModalityChooser().getSelected();

			SplitModalityEnum splitModality = SplitModalityEnum.valueOf(selectedSplitModality);

			this.fileSplitter = splitModality.getSplitterClass().getDeclaredConstructor(String.class, int.class).newInstance(
					this.controller.getOutputDirChooser().getChosenDir().getPath(),
					this.controller.getParameterInput().getInputValue());

			this.fileSplitter.splitFiles(this.controller.fileManagerButton.files);
			JOptionPane.showMessageDialog(null, "Files Divisi!");

		} catch (Exception exception) {
			exception.printStackTrace();
			JOptionPane.showMessageDialog(null, exception.getMessage());
		}
	}

}
