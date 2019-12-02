package fileSplitter;

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;


public class fileListDisplayer{


	private Controller controller;
	public JScrollPane listScrollPane;
	JList<File> filesList;
	
	DefaultListModel<File> models;
	
	public fileListDisplayer(Controller controller) {
		
		this.controller = controller;
		
		models = new DefaultListModel<File>();
		filesList = new JList<File>();
		
		
		this.listScrollPane = new JScrollPane(filesList);
	}
	
	public void updateListing() {

		for(File file: controller.fileManagerButton.files)
		{
			models.addElement(file);
		}

		filesList.setModel(models);
	}

}
