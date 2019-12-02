package fileSplitter;

import java.awt.Dimension;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneLayout;

public class Controller {

	Window window;
	JSplitPane splitPane;
	FileManagerButton fileManagerButton;

	fileListDisplayer fileListDisplayer;

	
	public Controller(Window window) {
		this.window = window;

		splitPane = new JSplitPane();
		
		this.fileManagerButton = new FileManagerButton(this);
		
		JPanel leftView = new JPanel();
		leftView.add(this.fileManagerButton);
		this.splitPane.setLeftComponent(leftView);

		this.fileListDisplayer = new fileSplitter.fileListDisplayer(this);
		
		this.splitPane.setRightComponent(this.fileListDisplayer.listScrollPane);
		
		this.splitPane.setResizeWeight(0.5); 

		this.window.frame.add(splitPane);

	}

	public void afterChoseFiles() {

		this.fileListDisplayer.updateListing();
		
	}
	

}
