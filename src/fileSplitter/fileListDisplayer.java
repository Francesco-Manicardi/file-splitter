package fileSplitter;

import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The Class FileListDisplayer displays the chosen files in a scrollable list.
 */
public class FileListDisplayer extends JPanel {

	/** The originating controller. */
	private Controller controller;

	/** The scroll pane where the files are listed. */
	public JScrollPane listScrollPane;

	/** The lable/title of the component. */
	public JLabel title;

	/** The chosen files list. */
	JList<File> filesList;

	/** The list models. */
	DefaultListModel<File> models;

	/**
	 * Instantiates a new file list displayer and all of its components.
	 *
	 * @param controller the originating controller
	 */
	public FileListDisplayer(Controller controller) {

		this.controller = controller;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		models = new DefaultListModel<File>();
		filesList = new JList<File>();
		title = new JLabel("Lista Files Scelti");

		listScrollPane = new JScrollPane(filesList);

		add(title);
		add(Box.createVerticalStrut(10));

		add(listScrollPane);

	}

	/**
	 * Update the list with the new chosen files.
	 */
	public void updateListing() {

		for (File file : controller.fileManagerButton.files) {
			models.addElement(file);
		}

		filesList.setModel(models);

	}

	/**
	 * Empty the list.
	 */
	public void refreshModel() {
		this.models = new DefaultListModel<File>();
	}

}
