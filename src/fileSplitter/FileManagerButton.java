package fileSplitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;

public class FileManagerButton extends JButton implements ActionListener {

	File[] files;
	JFileChooser chooser = new JFileChooser();

	
	private Controller controller;
	
	public FileManagerButton(Controller controller) {
		this.setBounds(20,20,100,30);
		this.setText("Scegli File");
		this.controller = controller;
		this.addActionListener(this);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		chooser.setMultiSelectionEnabled(true);
		chooser.showOpenDialog(null);
		
		files = chooser.getSelectedFiles();
		this.controller.afterChoseFiles();
	}

}
