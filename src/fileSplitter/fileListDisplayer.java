package fileSplitter;

import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FileListDisplayer extends JPanel {

	private Controller controller;
	public JScrollPane listScrollPane;
	public JLabel title;

	JList<File> filesList;

	DefaultListModel<File> models;

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

	public void updateListing() {

		for (File file : controller.fileManagerButton.files) {
			models.addElement(file);
		}

		filesList.setModel(models);

	}

	public void refreshModel() {
		this.models = new DefaultListModel<File>();
	}

}
